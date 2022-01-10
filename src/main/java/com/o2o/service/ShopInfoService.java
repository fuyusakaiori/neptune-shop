package com.o2o.service;

import com.o2o.dto.ShopMessage;
import com.o2o.entity.ShopInfo;

import java.io.File;
import java.io.InputStream;

public interface ShopInfoService
{

    /**
     * 新增店铺
     * @param shop 店铺信息
     * @param image 店铺的缩略图对应的流对象
     * @param fileName 传入的是流对象, 无法像文件对象那样可以直接调用 API 获取名称, 只能单独传入
     * @return 店铺是否操作成功的所有相关信息
     */
    ShopMessage insertShopInfo(ShopInfo shop, InputStream image, String fileName);


}
