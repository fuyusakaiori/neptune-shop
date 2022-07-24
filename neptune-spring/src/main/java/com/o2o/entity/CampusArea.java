package com.o2o.entity;

import java.util.Date;

/**
 * 校园区域实体类
 */
public class CampusArea
{
    // 标识符
    private Integer campusAreaId;
    // 名称
    private String campusAreaName;
    // 权重值
    private Integer priority;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date updateTime;

    public CampusArea()
    {
    }

    public CampusArea(Integer campusAreaId, String campusAreaName, Integer priority, Date createTime, Date updateTime)
    {
        this.campusAreaId = campusAreaId;
        this.campusAreaName = campusAreaName;
        this.priority = priority;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getCampusAreaId()
    {
        return campusAreaId;
    }

    public void setCampusAreaId(Integer campusAreaId)
    {
        this.campusAreaId = campusAreaId;
    }

    public String getCampusAreaName()
    {
        return campusAreaName;
    }

    public void setCampusAreaName(String campusAreaName)
    {
        this.campusAreaName = campusAreaName;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public String toString()
    {
        return "CampusArea{" +
                       "campusAreaId=" + campusAreaId +
                       ", campusAreaName='" + campusAreaName + '\'' +
                       ", priority=" + priority +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       '}';
    }
}
