package kw.kd.dss.service.impl;


import kw.kd.dss.config.DSSServerConstant;
import kw.kd.dss.dao.*;
import kw.kd.dss.dao.bml.ResourceDao;
import kw.kd.dss.dao.bml.VersionDao;
import kw.kd.dss.entity.Application;
import kw.kd.dss.entity.bml.Resource;
import kw.kd.dss.entity.bml.ResourceVersion;
import kw.kd.dss.entity.flow.DWSFlow;
import kw.kd.dss.entity.flow.DWSFlowTaxonomy;
import kw.kd.dss.entity.flow.DWSFlowVersion;
import kw.kd.dss.entity.project.DWSProject;
import kw.kd.dss.entity.project.DWSProjectTaxonomyRelation;
import kw.kd.dss.entity.project.DWSProjectVersion;
import kw.kd.dss.exception.AppJointErrorException;
import kw.kd.dss.exception.DSSErrorException;
import kw.kd.dss.service.MoveService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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

    @Override
    public Long copyEnvProject(Long projectVersionID, Long projectID, String projectName, Long userId) throws DSSErrorException, AppJointErrorException {
        //根据userid查询userName
        String userName = dwsUserMapper.getuserName(userId);
        System.out.println("获取username---" + userName);
        DWSProject project = projectMapper.selectProjectByID(projectID);
        if (StringUtils.isNotEmpty(projectName)) {
            project.setName(projectName);
        }
        DWSProjectTaxonomyRelation projectTaxonomyRelation = projectTaxonomyMapper.selectProjectTaxonomyRelationByTaxonomyIdOrProjectId(projectID);
        //添加至wtss的project数据库，获取projectID
        project.setUserName(userName);
        //这一块为判断是否存在调度
//        if(existSchesulis()){
//            createSchedulerProject(project);
//        }
//        Map<Long,Long> appjointProjectIDAndAppID = createAppjointProject(project);
        Long userID = dwsUserMapper.getUserID(userName);
        System.out.println("获取userid---" + userID);
        //添加至dws的project数据库，这里的projectID应该不需要自增
        //目前是相同数据库，需要自增id保持一致
        project.setUserID(userID);
        project.setCreateTime(new Date());
        //这里因为跨集群复制，不能使用自增，与测试的id
        project.setId(projectID);
        System.out.println("---project" + project.toString());
        projectMapper.addProject(project);
//        if(!appjointProjectIDAndAppID.isEmpty())projectMapper.addAccessProjectRelation(appjointProjectIDAndAppID,project.getId());
        projectTaxonomyMapper.addProjectTaxonomyRelation(project.getId(), projectTaxonomyRelation.getTaxonomyId(), userID);
        DWSProjectVersion maxVersion = projectMapper.selectLatestVersionByProjectID(projectID);
        copyPublishProjectVersionMax(maxVersion.getId(), maxVersion, maxVersion, userName, project.getId());
        return project.getId();
    }

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
        System.out.println("------------userId2" + userID);
        targetVersion.setUpdatorID(userID);
        targetVersion.setUpdateTime(new Date());
        List<DWSFlowVersion> flowVersions = flowMapper.listLastFlowVersionsByProjectVersionID(targetVersion.getId())
                .stream().sorted((o1, o2) -> Integer.valueOf(o1.getFlowID().toString()) - Integer.valueOf(o2.getFlowID().toString()))
                .collect(Collectors.toList());
        System.out.println("-----targetVersion  1 " + targetVersion.toString());
        Long oldProjectVersionID = targetVersion.getId();
        targetVersion.setId(srcVersion.getId());
        System.out.println("-----targetVersion  2 " + targetVersion.toString());
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

        for (DWSFlowVersion fv : flowVersions) {
            System.out.println("----fv--------------------"+fv.toString());
            String jsonPath = fv.getJsonPath();
            String version = fv.getVersion();
            //获取来源集群的resource
//            int isFileExists = resourceMapper.checkExists(jsonPath);
//            if (isFileExists == 0) return;

            Resource resource = resourceMapper.getResource(jsonPath);
            //插入一条记录到resource表
            System.out.println("-----linkis——resource----"+resource.toString());
            long id = resourceMapper.uploadResource(resource);
            logger.info("{} uploaded a resource and resourceId is {}", resource.getResourceId());
            //插入一条记录到resource version表 暂时写定服务器为生产的ip
            String clientIp = "192.168.200.206";
            String username = dwsUserMapper.getuserName(fv.getUpdatorID());
            String resourceId = resource.getResourceId();
            //读取linkis_resource_version 对应的版本信息
            String maxversion = versionMapper.getNewestVersion(resourceId);

            ResourceVersion resourceVersion_src = versionMapper.getResourceVersion(resourceId, maxversion);

            ResourceVersion resourceVersion_target = ResourceVersion.createNewResourceVersion(resourceId, resourceVersion_src.getResource(), resourceVersion_src.getFileMd5(),
                    clientIp, resourceVersion_src.getSize(), "v000001", 1);
            System.out.println("--------目标集群需要传送的资源11--------------"+resourceVersion_target.toString());
            versionMapper.insertNewVersion(resourceVersion_target);
            System.out.println("--------目标集群需要传送的资源--------------"+resourceVersion_target.getResource());

        }

        if (flowVersions != null && flowVersions.size() > 0) {
            flowVersions.stream().forEach(f -> {
                System.out.println(String.format("jsonPaht:%s,oldeFlowID:%s,newFlowID:%s", f.getJsonPath(), f.getOldFlowID(), f.getFlowID()));
                logger.info("jsonPaht:{},oldeFlowID:{},newFlowID", f.getJsonPath(), f.getOldFlowID(), f.getFlowID());
            });
            flowMapper.batchInsertFlowVersion(flowVersions);
        }
        System.out.println(projectVersionID + "-----" + srcVersion.getId() + "----" + userName + "----" + srcProjectID);
//        throw new RuntimeException();
    }

//    private Map<Long,Long> createAppjointProject(DWSProject project) throws DSSErrorException, AppJointErrorException {
//        Map applicationProjectIDs = new HashMap<Long,Long>();
//        List<Pair<Project, String>> pairs = projectServiceAddFunction(project, ProjectService::createProject, applicationService.listAppjoint());
//        for (Pair<Project, String> pair : pairs) {
//            if(pair.getFirst().getId() != null){
//                applicationProjectIDs.put(applicationService.getApplication(pair.getSecond()).getId(),pair.getFirst().getId());
//            }
//        }
//        return applicationProjectIDs;
//    }

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

//    private void createSchedulerProject(DWSProject dwsProject) throws DSSErrorException {
//        try {
//            if(getSchedulerAppJoint() != null) {
//                projectServiceAddFunction(dwsProject, ProjectService::createProject, Arrays.asList(getSchedulerAppJoint()));
//            }else{
//                logger.error("Add scheduler project failed for scheduler appjoint is null");
//            }
//        } catch (Exception e) {
//            logger.error("add scheduler project failed,", e);
//            throw new DSSErrorException(90002, "add scheduler project failed" + e.getMessage());
//        }
//    }
//    public List<Pair<Project,String>> projectServiceAddFunction(DWSProject project, ProjectServiceAddFunction projectServiceAddFunction, List<AppJoint> appJoints) throws AppJointErrorException {
//        ArrayList<Pair<Project,String>> projects = new ArrayList<>();
//        for (AppJoint appJoint : appJoints) {
//            Project appJointProject = null;
//            Session session = null;
//            if (appJoint.getSecurityService() != null) {
//                logger.info("[addProject]securityService is exist,{}login...", project.getUserName());
//                session = appJoint.getSecurityService().login(project.getUserName());
//            }
//            if (appJoint.getProjectService() != null) {
//                logger.info("[addProject]projectService is exist");
//                appJointProject = projectServiceAddFunction.accept(appJoint.getProjectService(), project, session);
//                if(appJointProject != null) projects.add(new Pair<Project, String>(appJointProject,appJoint.getAppJointName()));
//            }
//        }
//        return projects;
//    }
//    private SchedulerAppJoint getSchedulerAppJoint(){
//        if(schedulerAppJoint == null){
//            try {
//                schedulerAppJoint = (SchedulerAppJoint)applicationService.getAppjoint("schedulis");
//            } catch (AppJointErrorException e) {
//                logger.error("Schedule system init failed!", e);
//            }
//        }
//        return schedulerAppJoint;
//    }

}
