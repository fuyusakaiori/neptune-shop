package com.o2o.entity;

import java.util.Date;
import java.util.List;

/**
 * 商品信息
 */
public class GoodsInfo
{
    private Integer goodsId;
    private String goodsName;
    private String description;
    private String imageURL;
    // 正常商品价格
    private Double normalPrice;
    // 促销商品价格
    private Double promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date updateTime;
    // -1 表示不可用 0 表示下架 1 表示可用
    private Integer status;
    // 单个商品可以用多个图片描述
    private List<GoodsImage> images;
    // 商品类型
    private GoodsCategory category;
    // 所属店铺
    // TODO 只有归属一个店铺吗?
    private ShopInfo shopInfo;

    public GoodsInfo()
    {
    }


    public Integer getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
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

    public Double getNormalPrice()
    {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice)
    {
        this.normalPrice = normalPrice;
    }

    public Double getPromotionPrice()
    {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice)
    {
        this.promotionPrice = promotionPrice;
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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public List<GoodsImage> getImages()
    {
        return images;
    }

    public void setImages(List<GoodsImage> images)
    {
        this.images = images;
    }

    public GoodsCategory getCategory()
    {
        return category;
    }

    public void setCategory(GoodsCategory category)
    {
        this.category = category;
    }

    public ShopInfo getShopInfo()
    {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo)
    {
        this.shopInfo = shopInfo;
    }

    @Override
    public String toString()
    {
        return "GoodsInfo{" +
                       "goodsId=" + goodsId +
                       ", goodsName='" + goodsName + '\'' +
                       ", description='" + description + '\'' +
                       ", imageURL='" + imageURL + '\'' +
                       ", normalPrice=" + normalPrice +
                       ", promotionPrice=" + promotionPrice +
                       ", priority=" + priority +
                       ", createTime=" + createTime +
                       ", updateTime=" + updateTime +
                       ", status=" + status +
                       ", images=" + images +
                       ", category=" + category +
                       ", shopInfo=" + shopInfo +
                       '}';
    }
}
