package com.o2o.dao;

import com.o2o.entity.ShopInfo;

/**
 * <h2>店铺信息</h2>
 */
public interface ShopInfoMapper
{
    /**
     * 新增店铺信息
     * @param shopInfo 店铺信息对象
     * @return 是否插入成功: 插入失败返回 -1
     */
    int insertShopInfo(ShopInfo shopInfo);

    /**
     * 更新店铺的信息
     * @param shopInfo 店铺信息对象: 里面只包含需要更新的信息
     * @return 是否更新成功
     */
    int updateShopInfo(ShopInfo shopInfo);
}
