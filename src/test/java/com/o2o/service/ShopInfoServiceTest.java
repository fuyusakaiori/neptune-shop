package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.TestUtils;
import com.o2o.dto.ShopMessage;
import com.o2o.entity.ShopInfo;
import com.o2o.utils.enums.ShopState;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class ShopInfoServiceTest extends BaseTest
{
    @Autowired
    private ShopInfoService shopInfoService;

    @Test
    public void insertShopInfo(){
        ShopInfo shop = TestUtils.getShopInstance();
        File image = TestUtils.getImageFile();
        ShopMessage message = shopInfoService.insertShopInfo(shop, image);
        Assert.assertEquals(ShopState.SUCCESS.getState(), message.getState());
    }
}
