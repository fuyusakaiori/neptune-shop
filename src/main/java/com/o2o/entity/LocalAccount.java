package com.o2o.entity;

import java.util.Date;

/**
 * 本地账号
 */
public class LocalAccount
{
    private Integer localId;
    private String username;
    private String password;
    private Date createTime;
    private Date updateTime;
    // 关联的用户
    private UserInfo userInfo;

    public LocalAccount()
    {
    }


    public Integer getLocalId()
    {
        return localId;
    }

    public void setLocalId(Integer localId)
    {
        this.localId = localId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
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
        return "LocalAccount{" +
                       "localId=" + localId +
                       ", username='" + username + '\'' +
                       ", password='" + password + '\'' +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       ", userInfo=" + userInfo +
                       '}';
    }
}
