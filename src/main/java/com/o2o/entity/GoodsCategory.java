package com.o2o.entity;

import java.util.Date;

/**
 * 商品类型
 */
public class GoodsCategory
{
    private Integer goodsCategoryId;
    private String goodsCategoryName;
    private Integer priority;
    // 商品归属的店铺
    private ShopInfo shop;
    // TODO 每种商品只能顾存在单个商店里吗?
    private Date createTime;

    public GoodsCategory()
    {
    }

    public GoodsCategory(Integer goodsCategoryId, String goodsCategoryName, Integer priority, ShopInfo shop, Date createTime)
    {
        this.goodsCategoryId = goodsCategoryId;
        this.goodsCategoryName = goodsCategoryName;
        this.priority = priority;
        this.shop = shop;
        this.createTime = createTime;
    }

    public Integer getGoodsCategoryId()
    {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId)
    {
        this.goodsCategoryId = goodsCategoryId;
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

    public ShopInfo getShop()
    {
        return shop;
    }

    public void setShop(ShopInfo shop)
    {
        this.shop = shop;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
