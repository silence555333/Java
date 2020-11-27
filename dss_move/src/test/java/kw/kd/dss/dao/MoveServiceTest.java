package kw.kd.dss.dao;

import kw.kd.dss.entity.project.DWSProjectVersion;
import kw.kd.dss.exception.AppJointErrorException;
import kw.kd.dss.exception.DSSErrorException;
import kw.kd.dss.service.MoveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 18:28
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MoveServiceTest {

    @Autowired
    private MoveService moveService;

    @Test
    public void copyPublishProjectVersionMax() throws DSSErrorException, InterruptedException, AppJointErrorException {
        DWSProjectVersion srcproject=new DWSProjectVersion();
        srcproject.setId(11l);
        DWSProjectVersion targetproject=new DWSProjectVersion();
        moveService.copyEnvProject(1l,22l,"kd_test",1l);
//        moveService.copyPublishProjectVersionMax(1l,srcproject,targetproject,"hdfs",19l);
    }


}
