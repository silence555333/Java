package kw.kd.dss.service.impl;


import kw.kd.dss.config.DSSServerConstant;
import kw.kd.dss.config.EnvDSSServerConstant;
import kw.kd.dss.dao.*;
import kw.kd.dss.dao.bml.ResourceDao;
import kw.kd.dss.dao.bml.VersionDao;
import kw.kd.dss.dao.qualitis.QualitisMapper;
import kw.kd.dss.entity.Application;
import kw.kd.dss.entity.bml.Resource;
import kw.kd.dss.entity.bml.ResourceVersion;
import kw.kd.dss.entity.flow.DWSFlow;
import kw.kd.dss.entity.flow.DWSFlowTaxonomy;
import kw.kd.dss.entity.flow.DWSFlowVersion;
import kw.kd.dss.entity.project.DWSProject;
import kw.kd.dss.entity.project.DWSProjectApplicationProject;
import kw.kd.dss.entity.project.DWSProjectTaxonomyRelation;
import kw.kd.dss.entity.project.DWSProjectVersion;
import kw.kd.dss.entity.qualitis.DWSQualitisProject;
import kw.kd.dss.entity.qualitis.DWSQualitisProjectUser;
import kw.kd.dss.exception.AppJointErrorException;
import kw.kd.dss.exception.DSSErrorException;
import kw.kd.dss.service.MoveService;
import kw.kd.dss.util.DistcpHdfsUtil;
import kw.kd.dss.util.FlowParserUtil;
import kw.kd.dss.util.ZookeeperClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 14:23
 */
@Service
public class MoveServiceImpl implements MoveService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectTaxonomyMapper projectTaxonomyMapper;

    @Autowired
    private DWSUserMapper dwsUserMapper;
    @Autowired
    private FlowMapper flowMapper;
    @Autowired
    private FlowTaxonomyMapper flowTaxonomyMapper;

    @Autowired
    private ProjectMapper projectMapper;


    @Autowired
    private ResourceDao resourceMapper;
    @Autowired
    private VersionDao versionMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private QualitisMapper qualitisMapper;


    @Override
    public String checkDssEnv() {
        return null;
    }

    @Override
    public String synEnv() {
        return null;
    }

    @Override
    public List<DWSFlowVersion> listAllFlowVersions(Long flowID, Long projectVersionID) {
        List<DWSFlowVersion> versions = flowMapper.listFlowVersionsByFlowID(flowID, projectVersionID).stream().sorted((v1, v2) -> {
            return v1.compareTo(v2);
        }).collect(Collectors.toList());
        return versions;
    }

    @Transactional(rollbackFor = {DSSErrorException.class, InterruptedException.class})
    @Override
    public Long copyEnvProject(Long projectVersionID, Long projectID, String projectName, Long userId) throws DSSErrorException, AppJointErrorException {


        //根据userid查询userName
        String userName = dwsUserMapper.getuserName(userId);

        DWSProject project = projectMapper.selectProjectByID(projectID);
        if (StringUtils.isNotEmpty(projectName)) {
            project.setName(projectName);
        }
        DWSProjectTaxonomyRelation projectTaxonomyRelation = projectTaxonomyMapper.selectProjectTaxonomyRelationByTaxonomyIdOrProjectId(projectID);
        //添加至wtss的project数据库，获取projectID
        project.setUserName(userName);
        /**
         * 这一块增加质量关联的校验
         * 1 dss_project_applications_project
         * 2 qualitis_project
         * 3 qualitis_project_user
         */
        copyQualitis(projectID);
        Long userID = dwsUserMapper.getUserID(userName);

        //添加至dws的project数据库，这里的projectID应该不需要自增
        //目前是相同数据库，需要自增id保持一致
        project.setUserID(userID);
        project.setCreateTime(new Date());
        //这里因为跨集群复制，不能使用自增，与测试的id
        project.setId(projectID);
        projectMapper.addProject(project);

        projectTaxonomyMapper.addProjectTaxonomyRelation(project.getId(), projectTaxonomyRelation.getTaxonomyId(), userID);
        DWSProjectVersion maxVersion = projectMapper.selectLatestVersionByProjectID(projectID);
        copyPublishProjectVersionMax(maxVersion.getId(), maxVersion, maxVersion, userName, project.getId());

        return project.getId();
    }

    @Transactional(rollbackFor = {DSSErrorException.class, InterruptedException.class})
    @Override
    public void copyPublishProjectVersionMax(Long projectVersionID, DWSProjectVersion srcVersion, DWSProjectVersion targetVersion, String userName, Long srcProjectID) throws DSSErrorException {
        String maxVersionNum = generateInitVersion(srcVersion.getVersion());
        if (null != srcProjectID) {
            targetVersion.setVersion(DSSServerConstant.DWS_PROJECT_FIRST_VERSION);
            targetVersion.setProjectID(srcProjectID);
        } else {
//            targetVersion.setVersion(maxVersionNum);
            targetVersion.setVersion(DSSServerConstant.DWS_PROJECT_FIRST_VERSION);
        }
        Long userID = dwsUserMapper.getUserID(userName);
        targetVersion.setUpdatorID(userID);
        targetVersion.setUpdateTime(new Date());
        List<DWSFlowVersion> flowVersions = flowMapper.listLastFlowVersionsByProjectVersionID(targetVersion.getId())
                .stream().sorted((o1, o2) -> Integer.valueOf(o1.getFlowID().toString()) - Integer.valueOf(o2.getFlowID().toString()))
                .collect(Collectors.toList());

        Long oldProjectVersionID = targetVersion.getId();
        targetVersion.setId(srcVersion.getId());

        projectMapper.addProjectVersion(targetVersion);
        if (targetVersion.getId() == null) {
            throw new DSSErrorException(90015, "复制工程版本失败");
        }
        Map<Long, Long> subAndParentFlowIDMap = new ConcurrentHashMap<>();
        if (null != srcProjectID) {
            //这一块需要修改为只复制指定的版本
            flowVersions.stream().forEach(f -> {
                DWSFlow flow = flowMapper.selectFlowByID(f.getFlowID());
                Long parentFlowID = flowMapper.selectParentFlowIDByFlowID(flow.getId());
                if (parentFlowID != null) {
                    subAndParentFlowIDMap.put(flow.getId(), parentFlowID);
                }
            });
            for (DWSFlowVersion fv : flowVersions) {
                // 添加所有父子到map中
                DWSFlow flow = flowMapper.selectFlowByID(fv.getFlowID());
                flow.setCreatorID(userID);
                flow.setName(flow.getName());
                flow.setProjectID(targetVersion.getProjectID());
                flow.setCreateTime(new Date());

                Long taxonomyID = flowTaxonomyMapper.selectTaxonomyIDByFlowID(flow.getId());
                DWSFlowTaxonomy flowTaxonomy = flowTaxonomyMapper.selectFlowTaxonomyByID(taxonomyID);
                //新增flow相关数据
                fv.setOldFlowID(flow.getId());
                flow.setId(null);
                flowMapper.insertFlow(flow);
                if (null == flow.getId()) {
                    throw new DSSErrorException(90016, "复制工作流失败");
                }
                for (Map.Entry<Long, Long> entry : subAndParentFlowIDMap.entrySet()) {
                    if (entry.getValue().equals(fv.getFlowID())) {
                        subAndParentFlowIDMap.put(entry.getKey(), flow.getId());
                    }
                    if (entry.getKey().equals(fv.getFlowID())) {
                        subAndParentFlowIDMap.put(flow.getId(), entry.getValue());
                    }
                }
                if (flowTaxonomy.getProjectID() != -1 && (!flowTaxonomy.getProjectID().equals(targetVersion.getProjectID()))) {
                    flowTaxonomy.setProjectID(targetVersion.getProjectID());
                    flowTaxonomy.setCreateTime(new Date());
                    flowTaxonomy.setUpdateTime(new Date());
                    flowTaxonomy.setProjectID(targetVersion.getUpdatorID());
                    flowTaxonomyMapper.insertFlowTaxonomy(flowTaxonomy);
                }
                if (null != taxonomyID) {
                    flowTaxonomyMapper.insertFlowTaxonomyRelation(flowTaxonomy.getId(), flow.getId());
                }
                fv.setFlowID(flow.getId());
            }
            for (DWSFlowVersion fv : flowVersions) {
                if (subAndParentFlowIDMap.get(fv.getFlowID()) != null) {
                    flowMapper.insertFlowRelation(fv.getFlowID(), subAndParentFlowIDMap.get(fv.getFlowID()));
                }
            }
        }
        //进行资源的上传和同步
        //判断是否存在

        FlowParserUtil flowParserUtil = new FlowParserUtil();

        for (DWSFlowVersion fv : flowVersions) {

            String jsonPath = fv.getJsonPath();
            String version = fv.getVersion();
            /**
             * 补充： 解析json文件，并将解析到的resourceid 进行写入
             * 1,获取最新的flowid 对应的resource_json
             * 2,根据resource_json解析出相关的所有resourceid
             * 3,写入resourceid对应的linkis_resource 和 linkis_resource_version
             */
            //获取来源集群的resource
//            int isFileExists = resourceMapper.checkExists(jsonPath);
//            if (isFileExists == 0) return;

            Resource resource = resourceMapper.getResource(jsonPath);
            //插入一条记录到resource表
            //一个flow 对应一个json
            long id = resourceMapper.uploadResource(resource);
            logger.info("{} uploaded a resource and resourceId is {}", resource.getResourceId());
            //插入一条记录到resource version表 暂时写定服务器为生产的ip
            String clientIp = "192.168.200.206";
            String username = dwsUserMapper.getuserName(fv.getUpdatorID());
            String resourceId = resource.getResourceId();
            //读取linkis_resource_version 对应的版本信息
            String maxversion = versionMapper.getNewestVersion(resourceId);

            // 1,获取最新的flowid 对应的resource_json
            ResourceVersion resourceVersion_src = versionMapper.getResourceVersion(resourceId, maxversion);
            ResourceVersion resourceVersion_target = ResourceVersion.createNewResourceVersion(resourceId, resourceVersion_src.getResource(), resourceVersion_src.getFileMd5(),
                    clientIp, resourceVersion_src.getSize(), "v000001", 1);
            versionMapper.insertNewVersion(resourceVersion_target);

            // 2,解析json文件，获取依赖的resourceid
            String json_path = resourceVersion_target.getResource();
            String file_md5 = resourceVersion_src.getFileMd5();
            String srcPath = json_path.replace("hdfs://", EnvDSSServerConstant.DSS_DEV_HDFS_URL);
            distcpEnvDSSFile(json_path);
            String local_tmp_file = EnvDSSServerConstant.DWS_HDFS_LOCAL_TMP_DIR + file_md5;
            List<String> resourceid_list = flowParserUtil.getFlowResouceIdList(srcPath, local_tmp_file);
            for (String resource_id : resourceid_list) {
                getResouceAndCopy(resource_id);
            }
        }

        if (flowVersions != null && flowVersions.size() > 0) {
            flowVersions.stream().forEach(f -> {
                System.out.println(String.format("jsonPaht:%s,oldeFlowID:%s,newFlowID:%s", f.getJsonPath(), f.getOldFlowID(), f.getFlowID()));
                logger.info("jsonPaht:{},oldeFlowID:{},newFlowID", f.getJsonPath(), f.getOldFlowID(), f.getFlowID());
            });
            flowMapper.batchInsertFlowVersion(flowVersions);
        }
    }

    @Override
    public void delResourceProject(Long projectID) {
        // 从测试中读取所有的项目，删除多余文件
        List<Resource> resourceslist=resourceMapper.selectLinkisResourcesList(projectID);
        Integer size=resourceslist.size();
        String[] idList=new String[size];
        for (int i=0;i<size;i++){
            idList[i]=resourceslist.get(i).getResourceId();
            System.out.println("----输出数据"+resourceslist.get(i).getResourceId());
        }
        //删除已经存在的
        batchDeleteLinkisResourcesAndVersion(idList);
    }

    public void batchDeleteLinkisResourcesAndVersion(String[] resourceIdList){
        resourceMapper.batchDeleteLinkisRsources(resourceIdList);
        resourceMapper.batchDeleteLinkisRsourcesVersion(resourceIdList);
    }
    @Override
    public void copyQualitis(Long projectID) {
        /**
         * 这一块增加质量关联的校验
         * 1 dss_project_applications_project
         * 2 qualitis_project
         * 3 qualitis_project_user
         */
        DWSProjectApplicationProject dwsProjectApplicationProject = projectMapper.selectAccessByProjectId(projectID);
        if (dwsProjectApplicationProject == null || dwsProjectApplicationProject.equals("")) {
            logger.info("获取到的相关数据为空");
        } else {
            projectMapper.insertAccessProject(dwsProjectApplicationProject);
            Long relateId = dwsProjectApplicationProject.getApplicationProjectID();
            DWSQualitisProject dwsQualitisProject = qualitisMapper.queryQualitiesProject(relateId);
            if (dwsProjectApplicationProject.equals("") || dwsProjectApplicationProject == null) {
                logger.warn("质量检查中没有创建该项目");
            } else {
                qualitisMapper.insertQualitiesProject(dwsQualitisProject);
                DWSQualitisProjectUser dwsQualitisProjectUser = qualitisMapper.queryQualitiesProjectUser(relateId);
                if (dwsProjectApplicationProject.equals("") || dwsProjectApplicationProject == null) {
                    logger.warn("质量权限检查中没有创建该项目");
                } else {
                    qualitisMapper.insertQualitiesProjectUser(dwsQualitisProjectUser);
                }
            }
        }
    }


    public void distcpEnvDSSFile(String json_path) {
        DistcpHdfsUtil distcpHdfsUtil = new DistcpHdfsUtil();
        String activenode = ZookeeperClient.getActiveNode();
        String srcPath = json_path.replace("hdfs://", EnvDSSServerConstant.DSS_DEV_HDFS_URL);
        String dstPath = json_path.replace("hdfs://", String.format(EnvDSSServerConstant.DWS_PRO_HDFS_URL_FORMAT, activenode));
        logger.info(String.format("将测试环境的%s复制到生产集群%s", srcPath, dstPath));
        distcpHdfsUtil.distCopy(new Path(srcPath), new Path(dstPath));

    }

    /**
     * 根据resourceid 获取linkis_resource 和linkis_resource_version的最新版本并且进行同步
     *
     * @param resourceId
     */
    public void getResouceAndCopy(String resourceId) {

        Resource resource = resourceMapper.getResource(resourceId);
        //插入一条记录到resource表
        //一个flow 对应一个json
        long id = resourceMapper.uploadResource(resource);
        logger.info("{} uploaded a resource and resourceId is {}", resource.getResourceId());
        //获取version
        String maxversion = versionMapper.getNewestVersion(resourceId);
        String clientIp = "192.168.200.206";
        // 1,获取最新的flowid 对应的resource_json
        ResourceVersion resourceVersion_src = versionMapper.getResourceVersion(resourceId, maxversion);
        ResourceVersion resourceVersion_target = ResourceVersion.createNewResourceVersion(resourceId, resourceVersion_src.getResource(), resourceVersion_src.getFileMd5(),
                clientIp, resourceVersion_src.getSize(), "v000001", 1);
        versionMapper.insertNewVersion(resourceVersion_target);
        //复制文件
        String json_path = resourceVersion_src.getResource();
        distcpEnvDSSFile(json_path);

    }

    /**
     * 统一初始化版本
     *
     * @param version
     * @return
     */
    private String generateInitVersion(String version) {
        return DSSServerConstant.VERSION_PREFIX + String.format(DSSServerConstant.VERSION_FORMAT, 0);

    }

    private boolean existSchesulis() {
        return getApplication("schedulis") != null;
    }

    private Application getApplication(String appName) {
        return applicationMapper.getApplication(appName);
    }

}
