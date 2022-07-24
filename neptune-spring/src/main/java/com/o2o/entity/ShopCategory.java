package com.o2o.entity;

import java.util.Date;

/**
 * 店铺类型
 */
public class ShopCategory
{
    private Integer shopCategoryId;
    // 父类标识符: 顶级类型的父类标识符为空, 子类型才有父类标识符
    private Integer parentCategoryId;
    private String categoryName;
    private Integer priority;
    private String description;
    private String imageURL;
    private Date createTime;
    private Date updateTime;

    public ShopCategory()
    {
    }

    public Integer getShopCategoryId()
    {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId)
    {
        this.shopCategoryId = shopCategoryId;
    }

    public Integer getParentCategoryId()
    {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId)
    {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
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
        return "ShopCategory{" +
                       "shopCategoryId=" + shopCategoryId +
                       ", parentCategoryId=" + parentCategoryId +
                       ", categoryName='" + categoryName + '\'' +
                       ", priority=" + priority +
                       ", description='" + description + '\'' +
                       ", imageURL='" + imageURL + '\'' +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       '}';
    }
}
