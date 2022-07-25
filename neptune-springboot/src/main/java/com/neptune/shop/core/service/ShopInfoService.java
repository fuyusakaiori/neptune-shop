package com.neptune.shop.core.service;


import com.neptune.shop.core.dto.ImageWrapper;
import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.ShopInfo;
import com.neptune.shop.core.exception.ShopException;

public interface ShopInfoService
{

    /**
     * 新增店铺
     * @param shop 店铺信息
     * @param image 店铺的缩略图对应的流对象
     * @param fileName 传入的是流对象, 无法像文件对象那样可以直接调用 API 获取名称, 只能单独传入
     * @return 店铺是否操作成功的所有相关信息
     */
    Message<ShopInfo> insertShopInfo(ShopInfo shop, ImageWrapper wrapper) throws ShopException;

    /**
     * <p>根据店铺编号获取店铺信息</p>
     * @param id 店铺编号
     * @return 店铺信息
     */
    Message<ShopInfo> findShopInfoById(int id);

    /**
     * <p>更新店铺信息</p>
     */
    Message<ShopInfo> updateShopInfo(ShopInfo shop, ImageWrapper wrapper) throws ShopException;

    /**
     * TODO 前后端的分页有什么区别吗?
     * <h3>根据传入条件查询店铺信息</h3>
     * <p>注: 前端只能够传入页号, 而没有办法传入行号, 所以需要对应的转换工具</p>
     * @param condition 传入条件
     * @param pageIndex 页号
     * @param pageSize 页大小
     * @return 店铺信息的集合
     */
    Message<ShopInfo> findShopInfo(ShopInfo condition, int pageIndex, int pageSize);


}
