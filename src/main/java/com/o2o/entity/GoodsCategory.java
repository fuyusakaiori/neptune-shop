package com.o2o.entity;

import java.util.Date;

/**
 * 商品类型
 */
public class GoodsCategory
{
    private Integer goodsCategoryId;
    private GoodsCategory goodsCategoryParent;
    private String goodsCategoryName;
    private Integer priority;
    // 商品归属的店铺: 商品类别中只需要记录店铺编号就可以了, 没有必要记录店铺的所有信息
    private Integer shopId;
    // TODO 每种商品只能顾存在单个商店里吗?
    private Date createTime;
    // 每个商品类型是不可以更新的

    public GoodsCategory()
    {
    }

    public Integer getGoodsCategoryId()
    {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId)
    {
        this.goodsCategoryId = goodsCategoryId;
    }

    public GoodsCategory getGoodsCategoryParent()
    {
        return goodsCategoryParent;
    }

    public void setGoodsCategoryParent(GoodsCategory goodsCategoryParent) {
        this.goodsCategoryParent = goodsCategoryParent;
    }

    public String getGoodsCategoryName()
    {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName)
    {
        this.goodsCategoryName = goodsCategoryName;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public Integer getShopId()
    {
        return shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString()
    {
        return "GoodsCategory{" +
                       "goodsCategoryId=" + goodsCategoryId +
                       ", goodsCategoryParent=" + goodsCategoryParent +
                       ", goodsCategoryName='" + goodsCategoryName + '\'' +
                       ", priority=" + priority +
                       ", shopId=" + shopId +
                       ", createTime=" + createTime +
                       '}';
    }

    @Override
    public int hashCode() {
        return this.goodsCategoryId;
    }

    @Override
    public boolean equals(Object obj) {
        GoodsCategory other = (GoodsCategory) obj;
        return this.goodsCategoryId.equals(other.goodsCategoryId);
    }
}
