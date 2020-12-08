package kw.kd.dss.entity;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-26 19:41
 */
public class BackMessage {
    private Long projectID;
    private String projectVersion;
    private Long  userID;
    private int status;
    private String message;

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
