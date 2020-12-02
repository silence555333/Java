package kw.kd.dss.entity.qualitis;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-02 18:27
 */
public class DWSQualitisProjectUser {
    private Long id;
    private Integer permission;
    private String userFullName;
    private String userName;
    private Long projectID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }
}
