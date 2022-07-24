package com.o2o.entity;

import java.util.Date;

/**
 * 商品图片
 */
public class GoodsImage
{
    private Integer goodsImageId;
    private String url;
    private String description;
    private Integer priority;
    private Date createTime;
    // 图片归属的商品
    private GoodsInfo goods;
    // TODO 为什么要直接使用标识符而不是整个实体类?


    public GoodsImage()
    {
    }

    public GoodsImage(Integer goodsImageId, String url, String description, Integer priority, Date createTime, GoodsInfo goods)
    {
        this.goodsImageId = goodsImageId;
        this.url = url;
        this.description = description;
        this.priority = priority;
        this.createTime = createTime;
        this.goods = goods;
    }

    public Integer getGoodsImageId()
    {
        return goodsImageId;
    }

    public void setGoodsImageId(Integer goodsImageId)
    {
        this.goodsImageId = goodsImageId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public GoodsInfo getGoods()
    {
        return goods;
    }

    public void setGoods(GoodsInfo goods)
    {
        this.goods = goods;
    }

    @Override
    public String toString()
    {
        return "GoodsImage{" +
                       "goodsImageId=" + goodsImageId +
                       ", url='" + url + '\'' +
                       ", description='" + description + '\'' +
                       ", priority=" + priority +
                       ", createTime=" + createTime +
                       ", goods=" + goods +
                       '}';
    }
}
