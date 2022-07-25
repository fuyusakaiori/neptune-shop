package com.neptune.shop.core.dao;


import com.neptune.shop.core.entity.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>商品种类</h2>
 */
@Mapper
public interface GoodsCategoryMapper
{
    /**
     * <h3>查询店铺拥有的所有子商品类型</h3>
     * TODO <h3>之后会完善分页查询功能</h3>
     * @param id 店铺编号
     * @return 商品信息集合
     */
    List<GoodsCategory> findShopGoodsCategory(int id);

    /**
     * <h3>批量插入商品类型</h3>
     * @param categories 商品类型集合
     * @return 是否插入成功
     */
    int batchInsertGoodsCategory(List<GoodsCategory> categories);

    /**
     * <h3>删除商品类型</h3>
     * <h3>注: 使用两个条件限制的目的是在于避免传入空的店铺编号也可以进行删除, 因为空的编号可能是没有权限的</h3>
     * @param categoryId 商品类型编号
     * @param shopId 店铺类型编号
     * @return 是否删除成功
     */
    int deleteGoodsCategory(@Param("categoryId") int categoryId, @Param("shopId") int shopId);
}
