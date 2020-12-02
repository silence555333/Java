package kw.kd.dss.restful;


import com.alibaba.fastjson.JSONArray;
import kw.kd.dss.dao.DWSUserMapper;
import kw.kd.dss.dao.ProjectMapper;
import kw.kd.dss.entity.BackMessage;

import kw.kd.dss.entity.project.DWSProject;
import kw.kd.dss.entity.project.DWSProjectVersion;
import kw.kd.dss.exception.AppJointErrorException;
import kw.kd.dss.exception.DSSErrorException;
import kw.kd.dss.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-26 18:28
 */
@RestController//控制器类
@RequestMapping("/move")//映射路径
public class MoveEnvProjectRestful {
    @Autowired
    private MoveService moveService;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private DWSUserMapper dwsUserMapper;
    @RequestMapping(value="/testid")
    public String listAllVersions(Long projectVersionID) {
        return "this is a test ";
    }
    @RequestMapping(value="/publishEnvProject",method = RequestMethod.GET)
    public String PublishProject(@RequestParam Map<String, Object> params) throws DSSErrorException, AppJointErrorException {

        Long projectId= Long.valueOf(params.get("projectId").toString());
        String projectVersion=params.get("projectVersion").toString();
        Long userId=Long.valueOf(params.get("userId").toString());
        DWSProject dwsProject=projectMapper.selectProjectByID(projectId);
        DWSProjectVersion dwsProjectVersion=projectMapper.selectLatestVersionByProjectID(projectId);

        String name=dwsProject.getName();
        String version=dwsProjectVersion.getVersion();
        String username=dwsUserMapper.getuserName(userId);
        BackMessage bm = new BackMessage();
        bm.setProjectID(projectId);
        bm.setProjectVersion(projectVersion);
        bm.setUserID(userId);
        DWSProjectVersion srcproject=new DWSProjectVersion();
        srcproject.setId(11l);
        DWSProjectVersion targetproject=new DWSProjectVersion();
        moveService.copyEnvProject(1l,projectId,name,1l);
        return String.format("完成用户%s下的项目 为 %s,版本为 %s的部署",username,name,version);
    }

    @RequestMapping(value="/publishEnvProject/status",method = RequestMethod.GET)
    public String PublishProjectStatus(@RequestParam Map<String, Object> params) {
        Long projectId= Long.valueOf(params.get("projectId").toString());
        Long userId=Long.valueOf(params.get("userId").toString());
        BackMessage bm = new BackMessage();
        bm.setProjectID(projectId);
        bm.setProjectVersion("v000001");
        bm.setUserID(userId);
        bm.setStatus(0);
        Object obj = JSONArray.toJSON(bm);
        String json = obj.toString();
        return json;
    }

}
