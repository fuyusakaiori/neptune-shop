package com.neptune.shop.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>商品类型</h2>
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GoodsCategory
{
    private Integer goodsCategoryId;
    private String goodsCategoryName;
    private Integer priority;
    // 商品归属的店铺: 商品类别中只需要记录店铺编号就可以了, 没有必要记录店铺的所有信息
    private Integer shopId;
    private Date createTime;
    // TODO 每种商品应该是不能够删除的, 如果商品类型下包含相应的商品, 那么怎么删除?

}
