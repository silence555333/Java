package kw.kd.dss.dao.qualitis;

import kw.kd.dss.annotation.DataSource;
import kw.kd.dss.entity.qualitis.DWSQualitisProject;
import kw.kd.dss.entity.qualitis.DWSQualitisProjectUser;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-02 18:43
 */
@DataSource
public interface QualitisMapper {

    DWSQualitisProject queryQualitiesProject(Long id);
    DWSQualitisProjectUser queryQualitiesProjectUser(Long projectID);

    @DataSource("slave1")
    void insertQualitiesProject(DWSQualitisProject dwsQualitisProject);
    @DataSource("slave1")
    void insertQualitiesProjectUser(DWSQualitisProjectUser dwsQualitisProjectUser);

}
