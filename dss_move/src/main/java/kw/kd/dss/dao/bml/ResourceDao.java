/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kw.kd.dss.dao.bml;



import kw.kd.dss.annotation.DataSource;
import kw.kd.dss.entity.bml.Resource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * created by cooperyang on 2019/5/14
 * Description:
 */
@DataSource
public interface ResourceDao {

    @DataSource
    List<Resource> getResources(Map paramMap);

    void deleteResource(@Param("resourceId") String resourceId);

    void batchDeleteResources(@Param("resourceIds") List<String> resourceIds);

    @DataSource("slave1")
    long uploadResource(Resource resource);


    @Select("select exists(select * from `linkis_resources` where resource_id = #{resourceId}  and enable_flag = 1 )")
    int checkExists(@Param("resourceId") String resourceId);

    @DataSource
    Resource getResource(@Param("resourceId") String resourceId);

    @Select("select owner from `linkis_resources` where resource_id = #{resourceId} ")
    String getUserByResourceId(@Param("resourceId") String resourceId);

    List<Resource> getResourceListByProjectID(Long projectID);
}
