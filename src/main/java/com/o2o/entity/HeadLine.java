package com.o2o.entity;

import java.util.Date;

/**
 * 头条信息
 */
public class HeadLine
{
    private Integer headlineId;
    private String headlineName;
    private Integer priority;
    // 头条关联的页面地址
    private String link;
    // 头条图片
    private String imageURL;
    // 状态: 0 表示禁用 1 表示启用
    private Integer status;
    private Date createTime;
    private Date updateTime;


    public Integer getHeadlineId()
    {
        return headlineId;
    }

    public void setHeadlineId(Integer headlineId)
    {
        this.headlineId = headlineId;
    }

    public String getHeadlineName()
    {
        return headlineName;
    }

    public void setHeadlineName(String headlineName)
    {
        this.headlineName = headlineName;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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
        return "HeadLine{" +
                       "headlineId=" + headlineId +
                       ", headlineName='" + headlineName + '\'' +
                       ", priority=" + priority +
                       ", link='" + link + '\'' +
                       ", imageURL='" + imageURL + '\'' +
                       ", status=" + status +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       '}';
    }
}
