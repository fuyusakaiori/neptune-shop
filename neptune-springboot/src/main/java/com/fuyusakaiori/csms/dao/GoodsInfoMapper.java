package com.fuyusakaiori.csms.dao;


import com.fuyusakaiori.csms.entity.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>商品信息管理</h2>
 */
@Mapper
public interface GoodsInfoMapper
{
    /**
     * <h3>新增商品</h3>
     * @param goods 商品
     * @return 是否新增成功
     */
    int insertGoodsInfo(GoodsInfo goods);

    /**
     * <h3>根据商品编号查询商品信息</h3>
     * @param id 编号
     * @return 商品信息
     */
    GoodsInfo findGoodsInfoById(int id);

    /**
     * <h3>根据商品的其余信息查询: 根据类型、所属店铺、商品名称、商品状态</h3>
     * @return 商品信息集合
     */
    List<GoodsInfo> findAllGoodsInfo(@Param("condition") GoodsInfo condition, @Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * <h3>获取查询结结果数量</h3>
     */
    int getGoodsInfoCount(@Param("condition") GoodsInfo condition);

    /**
     * <h3>更新商品信息</h3>
     * @param goods 商品
     * @return 是否更新成功
     */
    int updateGoodsInfo(GoodsInfo goods);
}
