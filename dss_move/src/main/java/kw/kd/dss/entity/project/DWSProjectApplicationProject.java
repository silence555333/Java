package kw.kd.dss.entity.project;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-02 17:58
 */
public class DWSProjectApplicationProject {

    private Long projectID;
    private Integer applicationID;
    private Long applicationProjectID;

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public Integer getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Integer applicationID) {
        this.applicationID = applicationID;
    }

    public Long getApplicationProjectID() {
        return applicationProjectID;
    }

    public void setApplicationProjectID(Long applicationProjectID) {
        this.applicationProjectID = applicationProjectID;
    }
}
