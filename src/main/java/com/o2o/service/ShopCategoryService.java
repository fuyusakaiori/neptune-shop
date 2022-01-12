package com.o2o.service;

import com.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService
{
        /**
         * <h3>查询所有的店铺类型</h3>
         * <h4>1. 如果传入的店铺类型没有父类型, 那么就等同于查询所有店铺类型</h4>
         * <h4>2. 如果传入的店铺类型有父类型, 那么就等同于查询该父类型下的所有子类型</h4>
         * @param category 父类型
         * @return 所有的店铺类型
         */
        List<ShopCategory> findAllShopCategory(ShopCategory category);

        /**
         * <h3>查询所有店铺类型: 不需要任何条件</h3>
         * @return 所有店铺类型
         */
        List<ShopCategory> findAllShopCategory();

}
