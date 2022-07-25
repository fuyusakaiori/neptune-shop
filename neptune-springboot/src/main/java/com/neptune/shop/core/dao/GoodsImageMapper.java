package com.neptune.shop.core.dao;


import com.neptune.shop.core.entity.GoodsImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <h2>商品详情图管理</h2>
 */
@Mapper
public interface GoodsImageMapper
{
    /**
     * <h3>1. 如果传入相应的商品编号, 那么就仅查询该商品下的详情图</h3>
     * <h3>2. 如果没有传入商品编号, 那么就查询出所有的详情图</h3>
     * @param id 商品编号
     * @return 详情图集合
     */
    List<GoodsImage> findAllGoodsImage(int id);

    /**
     * <h3>1. 只需要传入详情图的集合就可以了</h3>
     * <h3>2. 详情图的实际生成在业务层完成</h3>
     * @param images 详情图集合
     * @return 是否插入成功
     */
    int batchInsertGoodsImage(List<GoodsImage> images);

    /**
     * <h3>删除指定商品下的所有详情图</h3>
     * @param id 商品编号
     * @return 是否删除成功
     */
    int deleteGoodsImage(int id);
}
