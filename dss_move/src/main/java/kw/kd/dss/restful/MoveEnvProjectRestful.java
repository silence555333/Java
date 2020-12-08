package kw.kd.dss.restful;


import com.alibaba.fastjson.JSONArray;
import kw.kd.dss.config.CommonConfigInfo;
import kw.kd.dss.config.DSSServerConstant;
import kw.kd.dss.config.EnvDSSServerConstant;
import kw.kd.dss.dao.DWSUserMapper;
import kw.kd.dss.dao.ProjectMapper;
import kw.kd.dss.entity.BackMessage;
import kw.kd.dss.entity.project.DWSProject;
import kw.kd.dss.entity.project.DWSProjectVersion;
import kw.kd.dss.exception.AppJointErrorException;
import kw.kd.dss.exception.DSSErrorException;
import kw.kd.dss.service.MoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //为空为进行发布，为0正在发布中，1为上一次发布完成
    private static Map<Long, Integer> status = new HashMap<>();
    @Autowired
    private MoveService moveService;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private DWSUserMapper dwsUserMapper;


    @RequestMapping(value = "/publishEnvProject", method = RequestMethod.GET)
    public String PublishProject(@RequestParam Map<String, Object> params) throws DSSErrorException, AppJointErrorException, InterruptedException {

        Long projectId = Long.valueOf(params.get("projectId").toString());
        String projectVersion = params.get("projectVersion").toString();
        Long userId = Long.valueOf(params.get("userId").toString());
        BackMessage bm = new BackMessage();
        bm.setProjectID(projectId);
        bm.setProjectVersion("v000001");
        bm.setUserID(userId);
        try {
            if (status.get(projectId) == null) {
                status.put(projectId, 1);
                DWSProject dwsProject = projectMapper.selectProjectByID(projectId);
                DWSProjectVersion dwsProjectVersion = projectMapper.selectLatestVersionByProjectID(projectId);

                String name = dwsProject.getName();
                String version = dwsProjectVersion.getVersion();
                String username = dwsUserMapper.getuserName(userId);
                DWSProjectVersion srcproject = new DWSProjectVersion();
                srcproject.setId(11l);
                DWSProjectVersion targetproject = new DWSProjectVersion();
                //进行版本的转移
//                moveService.copyEnvProject(1l, projectId, name, 1l);
                bm.setStatus(1);
                bm.setMessage(EnvDSSServerConstant.MOVEING_FINISH);
                status.remove(projectId);
            } else {
                bm.setStatus(0);
                bm.setMessage(EnvDSSServerConstant.MOVEING_MESSAGE);
            }
        } catch (Exception e) {
            bm.setMessage(EnvDSSServerConstant.MOVEING_FAIL);
            bm.setStatus(-1);
            status.remove(projectId);

        }
        Object obj = JSONArray.toJSON(bm);
        String json = obj.toString();
        return json;
    }

    @RequestMapping(value = "/publishEnvProject/status", method = RequestMethod.GET)
    public String PublishProjectStatus(@RequestParam Map<String, Object> params) {

        Long projectId = Long.valueOf(params.get("projectId").toString());

        Long userId = Long.valueOf(params.get("userId").toString());
        BackMessage bm = new BackMessage();
        bm.setProjectID(projectId);
        bm.setProjectVersion("v000001");
        bm.setUserID(userId);
        if (status.get(projectId) == null) {
            bm.setStatus(1);
            bm.setMessage(EnvDSSServerConstant.MOVEING_FINISH);
        } else {
            bm.setStatus(0);
            bm.setMessage( EnvDSSServerConstant.MOVEING_MESSAGE);
        }
        Object obj = JSONArray.toJSON(bm);
        String json = obj.toString();
        return json;
    }

}
