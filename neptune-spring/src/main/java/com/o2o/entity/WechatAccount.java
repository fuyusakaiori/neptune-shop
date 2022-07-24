package com.o2o.entity;

import java.util.Date;

/**
 * 微信账号
 */
public class WechatAccount
{
    private Integer wechatId;
    // 关联微信账号的标识符
    private Integer openId;
    private Date createTime;
    // 关联的用户信息
    private UserInfo userInfo;

    public WechatAccount()
    {
    }

    public WechatAccount(Integer wechatId, Integer openId, Date createTime, UserInfo userInfo)
    {
        this.wechatId = wechatId;
        this.openId = openId;
        this.createTime = createTime;
        this.userInfo = userInfo;
    }

    public Integer getWechatId()
    {
        return wechatId;
    }

    public void setWechatId(Integer wechatId)
    {
        this.wechatId = wechatId;
    }

    public Integer getOpenId()
    {
        return openId;
    }

    public void setOpenId(Integer openId)
    {
        this.openId = openId;
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

    @Override
    public String toString()
    {
        return "WechatAccount{" +
                       "wechatId=" + wechatId +
                       ", openId=" + openId +
                       ", createTime=" + createTime +
                       ", userInfo=" + userInfo +
                       '}';
    }
}
