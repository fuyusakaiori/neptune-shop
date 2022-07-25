package com.neptune.shop.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>店铺类型</h2>
 */
@Data
@NoArgsConstructor
@ToString
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
}
