package com.fuyusakaiori.csms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * <h2>商品信息</h2>
 */
@Data
@NoArgsConstructor
@ToString
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
}
