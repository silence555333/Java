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
import kw.kd.dss.entity.flow.DWSFlowTaxonomy;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@DataSource
public interface FlowTaxonomyMapper {
    @DataSource
    DWSFlowTaxonomy selectFlowTaxonomyByID(Long id);

    @DataSource("slave1")
    void insertFlowTaxonomy(DWSFlowTaxonomy dwsFlowTaxonomy) throws DuplicateKeyException;

    void updateFlowTaxonomy(DWSFlowTaxonomy dwsFlowTaxonomy) throws DuplicateKeyException;

    Long hasFlows(Long flowTaxonomyID);

    void deleteFlowTaxonomy(Long flowTaxonomyID);


    @DataSource("slave1")
    void insertFlowTaxonomyRelation(@Param("taxonomyID") Long taxonomyID, @Param("flowID") Long flowID);

    @DataSource
    Long selectTaxonomyIDByFlowID(Long id);

    void updateFlowTaxonomyRelation(@Param("flowIDList") List<Long> flowIDList, @Param("taxonomyID") Long taxonomyID);

    void deleteFlowTaxonomyRelation(Long flowID);

    void deleteFlowTaxonomyByProjectID(Long projectID);

    List<DWSFlowTaxonomy> listFlowTaxonomyByProjectID(Long projectID);
}
