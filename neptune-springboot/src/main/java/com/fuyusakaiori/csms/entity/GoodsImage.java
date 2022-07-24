package com.fuyusakaiori.csms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>商品图片</h2>
 */
@Data
@NoArgsConstructor
@ToString
public class GoodsImage {
    private Integer goodsImageId;
    private String url;
    private String description;
    private Integer priority;
    private Date createTime;
    // 图片归属的商品
    private GoodsInfo goods;
    // TODO 为什么要直接使用标识符而不是整个实体类?
}
