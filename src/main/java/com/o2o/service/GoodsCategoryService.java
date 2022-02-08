package com.o2o.service;

import com.o2o.dto.Message;
import com.o2o.entity.GoodsCategory;
import com.o2o.exception.GoodsCategoryException;

import java.util.List;

public interface GoodsCategoryService
{
    /**
     * <h3>查询店铺拥有的所有商品类型</h3>
     * @param id 店铺编号
     * @return 商品信息集合
     */
    Message<GoodsCategory> findShopGoodsCategory(int id);


    /**
     * <h3>批量插入商品类型</h3>
     * @param categories 商品类型集合
     * @return 是否插入成功
     */
    Message<GoodsCategory> batchInsertGoodsCategory(List<GoodsCategory> categories) throws GoodsCategoryException;

    /**
     * <h3>删除商品类型</h3>
     * <h3>注: 使用两个条件限制的目的是在于避免传入空的店铺编号也可以进行删除, 因为空的编号可能是没有权限的</h3>
     * @param categoryId 商品类型编号
     * @param shopId 店铺类型编号
     * @return 是否删除成功
     */
    Message<GoodsCategory> deleteGoodsCategory(int categoryId, int shopId) throws GoodsCategoryException;

}
