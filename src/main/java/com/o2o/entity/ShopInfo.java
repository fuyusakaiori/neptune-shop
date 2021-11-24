package com.o2o.entity;

import java.util.Date;

/**
 * 店铺信息
 */
public class ShopInfo
{
    private Integer shopId;
    private String imageURL;
    private String shopName;
    private String description;
    private String phone;
    private String address;
    private Integer priority;
    // 店铺类型
    private ShopCategory category;
    // 店铺所属区域
    private CampusArea campusArea;
    // 店铺老板
    private UserInfo master;
    // 状态: 0 禁用 1 审核 2 通过
    private Integer status;
    // 系统管理员提供的建议
    private String advice;
    private Date createTime;
    private Date updateTime;

    public ShopInfo()
    {
    }

    public ShopInfo(Integer shopId, String imageURL, String shopName, String description, String phone, String address, Integer priority, ShopCategory category, CampusArea campusArea, UserInfo master, Integer status, String advice, Date createTime, Date updateTime)
    {
        this.shopId = shopId;
        this.imageURL = imageURL;
        this.shopName = shopName;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.priority = priority;
        this.category = category;
        this.campusArea = campusArea;
        this.master = master;
        this.status = status;
        this.advice = advice;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public Integer getShopId()
    {
        return shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public ShopCategory getCategory()
    {
        return category;
    }

    public void setCategory(ShopCategory category)
    {
        this.category = category;
    }

    public CampusArea getCampusArea()
    {
        return campusArea;
    }

    public void setCampusArea(CampusArea campusArea)
    {
        this.campusArea = campusArea;
    }

    public UserInfo getMaster()
    {
        return master;
    }

    public void setMaster(UserInfo master)
    {
        this.master = master;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getAdvice()
    {
        return advice;
    }

    public void setAdvice(String advice)
    {
        this.advice = advice;
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
        return "ShopInfo{" +
                       "shopId=" + shopId +
                       ", imageURL='" + imageURL + '\'' +
                       ", shopName='" + shopName + '\'' +
                       ", description='" + description + '\'' +
                       ", phone='" + phone + '\'' +
                       ", address='" + address + '\'' +
                       ", priority=" + priority +
                       ", category=" + category +
                       ", campusArea=" + campusArea +
                       ", master=" + master +
                       ", status=" + status +
                       ", advice='" + advice + '\'' +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       '}';
    }
}
