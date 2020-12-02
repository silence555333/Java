package kw.kd.dss.service;

import kw.kd.dss.entity.flow.DWSFlowVersion;
import kw.kd.dss.entity.project.DWSProjectVersion;
import kw.kd.dss.exception.AppJointErrorException;
import kw.kd.dss.exception.DSSErrorException;


import java.util.List;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 11:45
 */

public interface MoveService {


    String checkDssEnv();

    String synEnv();

    List<DWSFlowVersion> listAllFlowVersions(Long flowID, Long projectVersionID);

    Long copyEnvProject(Long projectVersionID, Long projectID, String projectName, Long userId) throws DSSErrorException, AppJointErrorException;

    void copyPublishProjectVersionMax(Long projectVersionID, DWSProjectVersion srcVersion, DWSProjectVersion targetVersion, String userName, Long srcProjectID) throws DSSErrorException;

    void delResourceProject(Long projectID);

    void copyQualitis(Long projectID);

}
