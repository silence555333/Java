## hive 在mysql 中存储的元数据

### 查找数据库id+数据库名称+表格id+表格名称+字段id+字段名称

```
select b.DB_ID,b.`NAME`,a.tbl_id,a.TBL_NAME,d.CD_ID,d.COLUMN_NAME 
from tbls a join dbs b
on a.DB_ID =b.DB_ID
join sds c
on a.SD_ID = c.SD_ID
join columns_v2 d
on c.CD_ID=d.CD_ID
```