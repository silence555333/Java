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
 *
 */

package kw.kd.dss.dao;



import kw.kd.dss.annotation.DataSource;
import kw.kd.dss.entity.project.DWSProject;
import kw.kd.dss.entity.project.DWSProjectApplicationProject;
import kw.kd.dss.entity.project.DWSProjectPublishHistory;
import kw.kd.dss.entity.project.DWSProjectVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@DataSource
public interface ProjectMapper {
    DWSProject selectProjectByID(Long id);

    @DataSource
    DWSProjectVersion selectLatestVersionByProjectID(Long projectID);

    DWSProject selectProjectByVersionID(Long projectVersionID);

    @DataSource("slave1")
    void addProject(DWSProject dwsProject);

    @DataSource("slave1")
    void addProjectVersion(DWSProjectVersion dwsProjectVersion);

    void updateDescription(@Param("projectID") Long projectID, @Param("description") String description, @Param("product") String product, @Param("applicationArea") Integer applicationArea, @Param("business") String business);

    List<DWSProjectVersion> listProjectVersionsByProjectID(Long projectID);

    Boolean noPublished(Long projectID);

    void deleteProjectVersions(long projectID);

    void deleteProjectBaseInfo(long projectID);

    DWSProjectVersion selectProjectVersionByID(Long id);

    DWSProjectVersion selectProjectVersionByProjectIDAndVersionID(@Param("projectID") Long projectId, @Param("version") String version);

    Integer updateLock(@Param("lock") Integer lock, @Param("projectVersionID") Long projectVersionID);

    DWSProjectPublishHistory selectProjectPublishHistoryByProjectVersionID(Long projectVersionID);

    void insertPublishHistory(DWSProjectPublishHistory dwsProjectPublishHistory);

    void updatePublishHistoryState(@Param("projectVersionID") Long projectVersionID, @Param("status") Integer status);

    @DataSource("slave1")
    void addAccessProjectRelation(@Param("appjointProjectIDAndAppID") Map<Long, Long> appjointProjectIDAndAppID, @Param("projectID") Long projectID);


    Long getAppjointProjectID(@Param("projectID") Long projectID, @Param("applicationID") Integer applicationID);

    @DataSource
    DWSProjectApplicationProject selectAccessByProjectId(Long projectID);

    @DataSource("slave1")
    void insertAccessProject(DWSProjectApplicationProject dwsProjectApplicationProject);
}
