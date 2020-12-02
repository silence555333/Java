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
import kw.kd.dss.entity.flow.DWSFlow;
import kw.kd.dss.entity.flow.DWSFlowVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;


public interface FlowMapper {
    @DataSource
    DWSFlow selectFlowByID(Long id);

    List<DWSFlow> listFlowByTaxonomyID(@Param("projectID") Long projectID, @Param("taxonomyID") Long taxonomyID, @Param("isRootFlow") Boolean isRootFlow);

    @DataSource
    List<DWSFlowVersion> listFlowVersionsByFlowID(@Param("flowID") Long flowID, @Param("projectVersionID") Long projectVersionID);

    @DataSource("slave1")
    void insertFlow(DWSFlow dwsFlow) throws DuplicateKeyException;

    void insertFlowVersion(DWSFlowVersion version);

    @DataSource("slave1")
    void batchInsertFlowVersion(@Param("flowVersions") List<DWSFlowVersion> flowVersions);

    @DataSource("slave1")
    void insertFlowRelation(@Param("flowID") Long flowID, @Param("parentFlowID") Long parentFlowID);

    DWSFlowVersion selectVersionByFlowID(@Param("flowID") Long flowID, @Param("version") String version, @Param("projectVersionID") Long projectVersionID);

    void updateFlowBaseInfo(DWSFlow dwsFlow) throws DuplicateKeyException;

    @DataSource("slave1")
    List<Long> selectSubFlowIDByParentFlowID(Long parentFlowID);

    void deleteFlowVersions(@Param("flowID") Long flowID, @Param("projectVersionID") Long projectVersionID);

    void deleteFlowBaseInfo(Long flowID);

    void deleteFlowRelation(Long flowID);

    @DataSource
    Long selectParentFlowIDByFlowID(Long flowID);

    @DataSource("slave1")
    List<DWSFlow> listFlowByProjectID(Long projectID);

    List<DWSFlowVersion> listVersionByFlowIDAndProjectVersionID(@Param("flowID") Long flowID, @Param("projectVersionID") Long projectVersionID);

    Boolean noVersions(Long flowID);

    @DataSource
    List<DWSFlowVersion> listLastFlowVersionsByProjectVersionID(@Param("projectVersionID") Long projectVersionId);


    List<DWSFlowVersion> listLatestRootFlowVersionByProjectVersionID(Long projectVersionID);

    void batchUpdateFlowVersion(List<DWSFlowVersion> flowVersions);

    Long getParentFlowID(Long flowID);
}
