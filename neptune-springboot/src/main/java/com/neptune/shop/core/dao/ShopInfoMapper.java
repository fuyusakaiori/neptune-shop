package com.neptune.shop.core.dao;


import com.neptune.shop.core.entity.ShopInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>店铺信息</h2>
 */
@Mapper
public interface ShopInfoMapper
{
    /**
     * 新增店铺信息
     * @param shopInfo 店铺信息对象
     * @return 是否插入成功: 插入失败返回 -1
     */
    int insertShopInfo(ShopInfo shopInfo);

    /**
     * <p>更新店铺的信息</p>
     * <p>注: 如果使用 @Param 注解, 那么 mapper 没有办法将其识别成一个对象, 需要通过字段获取属性</p>
     * @param shopInfo 店铺信息对象: 里面只包含需要更新的信息
     * @return 是否更新成功
     */
    int updateShopInfo(ShopInfo shopInfo);

    /**
     * <p>根据店铺 ID 查询店铺信息</p>
     * @param id 店铺 ID
     * @return 店铺信息
     */
    ShopInfo findShopInfoById(@Param("id") int id);

    /**
     * <h3>1. 根据传入的查询条件查询得到对应店铺集合</h3>
     * <h3>2. 将查询得到的店铺集合进行分页</h3>
     * @param condition 所有查询条件都封装在一个对象中: 可以按照店铺名称、店铺类别、店铺状态、区域编号、老板信息
     * @param start 每页的起始值
     * @param pageSize 每页大小
     * @return 店铺的集合
     */
    List<ShopInfo> findShopInfo(@Param("condition") ShopInfo condition, @Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * <h3>统计查询的店铺数量, 根据店铺数量决定分页查询</h3>
     * @param condition 查询条件
     * @return 店铺数量
     */
    int getShopInfoCount(@Param("condition") ShopInfo condition);
}
