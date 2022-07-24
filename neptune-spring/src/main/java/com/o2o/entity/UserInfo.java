package com.o2o.entity;

import java.util.Date;

/**
 * 用户信息实体类
 */
public class UserInfo
{
    private Integer userId;
    private String username;
    private String avatarURL;
    private String email;
    private String gender;
    // 状态: 0 禁用 1 表示可用
    private Integer status;
    // 角色: 1 顾客 2 店铺管理员 3 超级管理员
    private Integer role;
    private Date createTime;
    private Date updateTime;

    public UserInfo()
    {
    }

    public UserInfo(Integer userId, String username, String avatarURL, String email, String gender, Integer status, Integer role, Date createTime, Date updateTime)
    {
        this.userId = userId;
        this.username = username;
        this.avatarURL = avatarURL;
        this.email = email;
        this.gender = gender;
        this.status = status;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getAvatarURL()
    {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL)
    {
        this.avatarURL = avatarURL;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getRole()
    {
        return role;
    }

    public void setRole(Integer role)
    {
        this.role = role;
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
        return "UserInfo{" +
                       "userId=" + userId +
                       ", username='" + username + '\'' +
                       ", avatarURL='" + avatarURL + '\'' +
                       ", email='" + email + '\'' +
                       ", gender='" + gender + '\'' +
                       ", status=" + status +
                       ", role=" + role +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       '}';
    }
}
