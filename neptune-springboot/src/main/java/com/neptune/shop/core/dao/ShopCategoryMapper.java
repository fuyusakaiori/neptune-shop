package com.neptune.shop.core.dao;


import com.neptune.shop.core.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>店铺类型</h2>
 */
@Mapper
public interface ShopCategoryMapper
{

    /**
     * <h4>1. 如果传入的店铺类型为空, 那么就是查询所有的父类型</h4>
     * @return 返回所有父类型
     */
    List<ShopCategory> findAllShopCategory();

    /**
     * <h3>查询所有的店铺类型</h3>
     * <h4>2. 如果传入的店铺类型没有父类型, 那么就等同于查询所有店铺子类型</h4>
     * <h4>3. 如果传入的店铺类型有父类型, 那么就等同于查询该父类型下的所有子类型</h4>
     * @param category 父类型
     * @return 所有的店铺类型
     */
    List<ShopCategory> findAllShopCategory(@Param("category") ShopCategory category);

}
