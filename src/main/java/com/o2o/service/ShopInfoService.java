package com.o2o.service;

import com.o2o.dto.ShopMessage;
import com.o2o.entity.ShopInfo;

import java.io.File;

public interface ShopInfoService
{

    /**
     * 新增店铺
     * @param shop 店铺信息
     * @param image 店铺的缩略图
     * @return 店铺是否操作成功的所有相关信息
     */
    ShopMessage insertShopInfo(ShopInfo shop, File image);
}
