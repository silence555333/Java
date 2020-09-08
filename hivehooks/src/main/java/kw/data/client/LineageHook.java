package kw.data.client;

import kw.data.hooks.model.*;
import kw.data.hooks.model.event.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.ql.hooks.*;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.BaseColumnInfo;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.Dependency;
import org.apache.hadoop.hive.ql.hooks.LineageInfo.DependencyKey;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.HiveOperation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.hadoop.hive.ql.hooks.Entity.Type.TABLE;
import static org.apache.hadoop.hive.ql.plan.HiveOperation.*;


/**
 * @author yangfei
 * @create 2020-06-01 17:26
 */
public class LineageHook implements ExecuteWithHookContext {
    static final private Log LOG = LogFactory.getLog(LineageHook.class);
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Map<String, HiveOperation> OPERATION_MAP = new HashMap<>();

    static {
        //写入org.apache.hadoop.hive.ql.plan hiveOperation
        OPERATION_MAP.put(CREATEDATABASE.getOperationName(), CREATEDATABASE);
        OPERATION_MAP.put(DROPDATABASE.getOperationName(), DROPDATABASE);

        OPERATION_MAP.put(CREATETABLE.getOperationName(), CREATETABLE);
        OPERATION_MAP.put(CREATETABLE_AS_SELECT.getOperationName(), CREATETABLE_AS_SELECT);
        OPERATION_MAP.put(DROPTABLE.getOperationName(), DROPTABLE);
        OPERATION_MAP.put(ALTERTABLE_ADDCOLS.getOperationName(), ALTERTABLE_ADDCOLS);
        OPERATION_MAP.put(ALTERTABLE_REPLACECOLS.getOperationName(), ALTERTABLE_REPLACECOLS);
        OPERATION_MAP.put(ALTERTABLE_RENAMECOL.getOperationName(), ALTERTABLE_RENAMECOL);
        OPERATION_MAP.put(ALTERTABLE_RENAME.getOperationName(), ALTERTABLE_RENAME);
        OPERATION_MAP.put(ALTERTABLE_FILEFORMAT.getOperationName(), ALTERTABLE_FILEFORMAT);
        OPERATION_MAP.put(ALTERTABLE_LOCATION.getOperationName(), ALTERTABLE_LOCATION);

        OPERATION_MAP.put(CREATEVIEW.getOperationName(), CREATEVIEW);
        OPERATION_MAP.put(ALTERVIEW_AS.getOperationName(), ALTERVIEW_AS);
        OPERATION_MAP.put(LOAD.getOperationName(), LOAD);
        OPERATION_MAP.put(EXPORT.getOperationName(), EXPORT);
        OPERATION_MAP.put(IMPORT.getOperationName(), IMPORT);
        OPERATION_MAP.put(QUERY.getOperationName(), QUERY);

    }


    @Override
    public void run(HookContext hookContext) throws Exception {


        try {
            final HookContext.HookType hookType = hookContext.getHookType();
            final String operationName = hookContext.getOperationName();

            if (HookContext.HookType.POST_EXEC_HOOK != hookContext.getHookType()
                    || !OPERATION_MAP.containsKey(operationName)
            ) {
                LOG.info("Hive hook " + hookType + " , operation name " + operationName);
                return;
            }
            final HiveOperation hiveOperation = OPERATION_MAP.get(operationName);
            LineageMessage key = new LineageMessage();

            Object value = null;

            switch (hiveOperation) {
                case CREATEDATABASE:
                    key.setValueClass(CreateDatabase.class.getName());
                    Optional<String> cdbo = extractOneDbName(hookContext.getOutputs());
                    if (cdbo.isPresent()) {
                        value = new CreateDatabase(cdbo.get());
                    }
                    break;
                case DROPDATABASE:
                    key.setValueClass(DropDatabase.class.getName());
                    Optional<String> ddbo = extractOneDbName(hookContext.getOutputs());
                    if (ddbo.isPresent()) {
                        value = new DropDatabase(ddbo.get());
                    }
                    break;

                case CREATETABLE:
                case ALTERTABLE_ADDCOLS:
                case ALTERTABLE_REPLACECOLS:
                case ALTERTABLE_FILEFORMAT:
                case ALTERTABLE_LOCATION:
                    key.setValueClass(CreateOrAlterTable.class.getName());
                    Optional<org.apache.hadoop.hive.ql.metadata.Table> ctOpt = extractOneTable(hookContext.getOutputs());
                    if (ctOpt.isPresent()) {
                        value = new CreateOrAlterTable(ctOpt.get().getDbName(), ctOpt.get().getTableName());
                    } else {
                        LOG.info("Cannot extract table from hook context");
                    }
                    break;
                case DROPTABLE:
                    key.setValueClass(DropTable.class.getName());
                    Optional<org.apache.hadoop.hive.ql.metadata.Table> dtOpt = extractOneTable(hookContext.getOutputs());
                    if (dtOpt.isPresent()) {
                        value = new DropTable(dtOpt.get().getDbName(), dtOpt.get().getTableName());
                    }
                    break;
                case ALTERTABLE_RENAMECOL:
                    key.setValueClass(AlterTableRenameColumn.class.getName());
                    Optional<AlterTableRenameColumn> opt = buildAlterTableRenameColumn(hookContext);
                    if (opt.isPresent()) {
                        value = opt.get();
                    }
                    break;
                case ALTERTABLE_RENAME:
                    key.setValueClass(RenameTable.class.getName());
                    List<Table> oldTables = fromEntities(hookContext.getInputs());
                    List<Table> newTables = fromEntities(hookContext.getOutputs());
                    //Old table name is in outputs, has to be ignored.
                    List<Table> newTables2 = newTables.stream()
                            .filter(t -> !oldTables.contains(t))
                            .collect(Collectors.toList());
                    if (!oldTables.isEmpty() && !newTables2.isEmpty()) {
                        value = new RenameTable(oldTables.get(0), newTables2.get(0));
                    }
                    break;
                case CREATETABLE_AS_SELECT:
                    key.setValueClass(CreateTableAsSelect.class.getName());
                    List<Table> createdTables = fromEntities(hookContext.getOutputs());
                    Optional<Lineage> lineage = buildLineage(hookContext);
                    if (!createdTables.isEmpty() && lineage.isPresent()) {
                        value = new CreateTableAsSelect(createdTables.get(0), lineage.get());
                    }
                    break;
                case CREATEVIEW:
                case ALTERVIEW_AS:
                case LOAD:
                case EXPORT:
                case IMPORT:
                case QUERY:
                    key.setValueClass(Lineage.class.getName());
                    System.out.println("---------  START Query-------");
                    Optional<Lineage> dataLineageOpt = buildLineage(hookContext);

                    if (dataLineageOpt.isPresent()) {
                        value = dataLineageOpt.get();
                    }
                    break;
                default:
                    LOG.warn("Unsupported operation " + operationName);
                    break;
            }
            if (value != null) {
                System.out.println(value.toString());
            }
//            if (value != null ){
//                //解析为所依赖的数据，暂时不加字段解析，记录查询的sql
//                //多加一层到HiveLineage
//                Lineage lineage= (Lineage) value;
//                //拼接列表中数据
//                String sourcedb=null;
//                String sourceTable =null;
//                String sourceCol=null;
//                if(lineage.getSourceTables().size() >0 ){
//
//                    for (Table table :lineage.getSourceTables()){
//                        sourcedb=AppendString(sourcedb,table.getDbName());
//                        sourceTable=AppendString(sourceTable,table.getDbName()+"."+table.getTableName());
//
//                        if (table.getColumns().size()>0) {
//                            if(Check.notEmpty(table.getColumns().toString())) {
//                                sourceCol = AppendString(sourceCol, StringUtils.strip(table.getColumns().toString(), "[]"));
//                            }
//                            }
//
//                    }
//
//                }
//                HiveLineage hl =new HiveLineage();
//                hl.setSource_db(sourcedb);
//                hl.setSource_tname(sourceTable);
//                hl.setSource_col(sourceCol);
//                  if(lineage.getTargetTables().size() >0) {
//
//                    Table targetTable = lineage.getTargetTables().get(0);
//
//                    hl.setTarget_db(targetTable.getDbName());
//                    hl.setTarget_tname(targetTable.getDbName()+"."+targetTable.getTableName());
//                    if (Check.notEmpty(targetTable.getColumns())) {
//                        if (Check.notEmpty(targetTable.getColumns().toString()) && targetTable.getColumns().toString().contains("[")){
//
//                            hl.setTarget_col(StringUtils.strip(targetTable.getColumns().toString(), "[]"));
//                        }
//
//                    }
//                }
//
//                if (Check.notEmpty(lineage.getDependencies())) {
//
//                    if(Check.notEmpty(lineage.getDependencies().toString()) && lineage.getDependencies().toString().contains("[")){
//                        hl.setExpr(StringUtils.strip(lineage.getDependencies().toString(), "[]"));
//                    }
//                }
//
//                hl.setPtype(hiveOperation.getOperationName());
//
//                hl.setQuery_id(lineage.getQueryId());
//                hl.setQuery_sql(lineage.getQueryString());
//                hl.setCreate_time(df.format(new Date()));
//                hl.setUpdate_time(df.format(new Date()));
//                Object obj = JSONArray.toJSON(hl);
//                String json = obj.toString();
////                FileUtil.writeFile(json);
//                HDFSUtil.writeFile(json);
//
//                LOG.info(" hive hook explain sql : " + json);
//            }
        } catch (Exception e) {
            LOG.error("Error in KWHiveHook ", e);
        }
    }

    private Optional<String> extractOneDbName(Set<? extends Entity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Optional.empty();
        }
        Entity entity = entities.iterator().next();

        if (entity.getType() != Entity.Type.DATABASE) {
            return Optional.empty();
        }

        return Optional.ofNullable(entity.getDatabase().getName());
    }

    private Optional<org.apache.hadoop.hive.ql.metadata.Table> extractOneTable(Set<? extends Entity> entities) {
        if (entities == null || entities.isEmpty()) {
            LOG.info("Entities are NULL or empty");
            return Optional.empty();
        }
        Iterator<? extends Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity et = iterator.next();
            if (et.getType() == Entity.Type.TABLE) {
                return Optional.ofNullable(et.getTable());
            }
        }
        return Optional.empty();
    }

    private Optional<AlterTableRenameColumn> buildAlterTableRenameColumn(HookContext hookContext) {
        try {
            org.apache.hadoop.hive.ql.metadata.Table oldTable = hookContext.getInputs().iterator().next().getTable();
            org.apache.hadoop.hive.ql.metadata.Table newTable = hookContext.getOutputs().iterator().next().getTable();

            Hive hive = Hive.get(hookContext.getConf());
            newTable = hive.getTable(newTable.getDbName(), newTable.getTableName());

            List<FieldSchema> oldColumns = oldTable.getCols();
            List<FieldSchema> newColumns = newTable.getCols();
            FieldSchema changedColumnOld = null;
            FieldSchema changedColumnNew = null;

            for (FieldSchema oldColumn : oldColumns) {
                if (!newColumns.contains(oldColumn)) {
                    changedColumnOld = oldColumn;
                    break;
                }
            }

            for (FieldSchema newColumn : newColumns) {
                if (!oldColumns.contains(newColumn)) {
                    changedColumnNew = newColumn;
                    break;
                }
            }

            if (changedColumnOld != null && changedColumnNew != null) {

                return Optional.of(new AlterTableRenameColumn(new Table(newTable.getDbName(), newTable.getTableName())
                        , changedColumnOld.getName()
                        , changedColumnNew.getName())
                );
            }
        } catch (HiveException e) {
            LOG.error(e);

        }
        return Optional.empty();
    }

    private Optional<Table> fromEntity(Entity entity) {
        if (entity.getType() != TABLE && entity.getType() != Entity.Type.PARTITION) {
            return Optional.empty();
        }
        Table tbl = new Table(entity.getTable().getDbName(), entity.getTable().getTableName());
        LOG.info("Entity type " + entity.getType() + " table " + tbl.toString());
        if (entity instanceof ReadEntity && ((ReadEntity) entity).getAccessedColumns() != null) {
            tbl.setColumns(((ReadEntity) entity).getAccessedColumns());
        }
        return Optional.of(tbl);
    }

    private List<Table> fromEntities(Set<? extends Entity> entities) {
        List<Table> tables = new ArrayList<>();
        if (entities != null) {
            for (Entity entity : entities) {
                if (entity != null) {
                    Optional<Table> opt = fromEntity(entity);
                    opt.ifPresent(tables::add);
                }
            }
        }
        return tables;
    }

    /**
     * 解析血缘关系
     *
     * @param hookContext
     * @return
     */
    private Optional<Lineage> buildLineage(HookContext hookContext) throws SQLException {

        //写入mysql要存储的对象
        Lineage lineage = new Lineage();
        if (hookContext.getQueryPlan() != null) {

            lineage.setQueryId(hookContext.getQueryPlan().getQueryId());
            lineage.setQueryString(hookContext.getQueryPlan().getQueryString());

        }

        //添加判断，只是记录写入表格的sql
        String quesy_sql = hookContext.getQueryPlan().getQueryString();

        lineage.setSourceTables(fromEntities(hookContext.getInputs()));
        lineage.setTargetTables(fromEntities(hookContext.getOutputs()));

        LineageInfo lineageInfo = hookContext.getLinfo();

        if (lineageInfo != null) {
            System.out.println(" ----------------------------");
            LinkedList<Map.Entry<DependencyKey, Dependency>> entry_list =
                    new LinkedList<Map.Entry<DependencyKey, Dependency>>(lineageInfo.entrySet());

            Collections.sort(entry_list, new DependencyKeyComp());
            Iterator<Map.Entry<DependencyKey, Dependency>> iter = entry_list.iterator();
            System.out.println( "ITER   " + entry_list.size());
            List<UpstreamsDownstream> deps = new ArrayList<>();
            while (iter.hasNext()) {
                System.out.println("POSTHOOK: Lineage: -------------- ");
                Map.Entry<DependencyKey, Dependency> entry = iter.next();
                //上游
                Dependency dep = entry.getValue();
                //下游
                DependencyKey depK = entry.getKey();
                System.out.println("iter.next()  Upstream " + dep + " downstream " + depK);
                LOG.info("Upstream " + dep + " downstream " + depK);
                if (dep == null) {
                    continue;
                }
                DbTblCol downstream = new DbTblCol();
                downstream.setDbName(depK.getDataContainer().getTable().getDbName());
                downstream.setTblName(depK.getDataContainer().getTable().getTableName());
                downstream.setColumn(new Column(depK.getFieldSchema()));

                if (depK.getDataContainer().isPartition()) {

                    List<FieldSchema> partitionKeys = depK.getDataContainer().getTable().getPartitionKeys();
                    Partition part = depK.getDataContainer().getPartition();
                    System.out.println(" PARTITION( "+part.getTableName());
                    int i = 0;
                    List<PartitionKeyValue> partitionKeyValues = new ArrayList<>();
                    for (FieldSchema partitionKey : partitionKeys) {
                        PartitionKeyValue pkv = new PartitionKeyValue();
                        pkv.setPartitionKey(partitionKey.getName());
                        pkv.setPartitionValue(part.getValues().get(i++));
                        partitionKeyValues.add(pkv);
                        System.out.println(partitionKey.getName() + "=" + part.getValues().get(i++));
                    }
                    downstream.setPartition(partitionKeyValues);
                }

                System.out.println(" start split string  ");
                System.out.println(" depK   dep   :"+depK.getDataContainer().getTable().getTableName()+"." + depK.getFieldSchema().getName() + " " +
                        dep.getType() + " ");

                List<DbTblCol> upstreams = new ArrayList<>();


                for (BaseColumnInfo baseCol : dep.getBaseCols()) {
                    System.out.println("BaseColumnInfo :  (" + baseCol.getTabAlias().getTable().getTableName() + ")"
                            + baseCol.getTabAlias().getAlias() + "."
                            + baseCol.getColumn() + ", ");
                    if (baseCol.getColumn() == null) {

                        LOG.warn(baseCol.toString() + " has NULL FieldSchema");
                        continue;
                    }
                    DbTblCol dbTblCol = new DbTblCol();
                    dbTblCol.setDbName(baseCol.getTabAlias().getTable().getDbName());
                    dbTblCol.setTblName(baseCol.getTabAlias().getTable().getTableName());
                    dbTblCol.setColumn(new Column(baseCol.getColumn()));
                    upstreams.add(dbTblCol);
                }
                deps.add(new UpstreamsDownstream(upstreams, downstream));
            }

            lineage.setDependencies(deps);
        }
        LOG.info(lineage.toString());
        return Optional.of(lineage);
    }

    public String AppendString(String str1, String str2) {
        String res = null;
        if (str1 == null || str1.equals("")) {
            res = str2;
        } else {
            res = str1 + "," + str2;
        }
        return res;
    }


}
