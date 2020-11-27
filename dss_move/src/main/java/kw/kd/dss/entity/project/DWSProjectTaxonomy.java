package kw.kd.dss.entity.project;

import com.webank.wedatasphere.dss.common.entity.project.DWSProject;

import java.util.Date;
import java.util.List;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 17:18
 */
public class DWSProjectTaxonomy {
    private Long id;
    private String name;
    private String description;
    private Long creatorID;
    private Date createTime;
    private Date updateTime;
    private List<DWSProject> dwsProjectList;

    public List<DWSProject> getDwsProjectList() {
        return dwsProjectList;
    }

    public void setDwsProjectList(List<DWSProject> dwsProjectList) {
        this.dwsProjectList = dwsProjectList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(Long creatorID) {
        this.creatorID = creatorID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
