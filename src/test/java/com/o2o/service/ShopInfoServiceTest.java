package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.TestUtils;
import com.o2o.dto.Message;
import com.o2o.entity.CampusArea;
import com.o2o.entity.ShopInfo;
import com.o2o.utils.enums.State;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ShopInfoServiceTest extends BaseTest
{
    @Autowired
    private ShopInfoService shopInfoService;

    @Test
    @Ignore
    public void insertShopInfoServiceTest() throws FileNotFoundException
    {
        ShopInfo shop = TestUtils.getShopInstance();
        File image = TestUtils.getImageFile();
        Message message = shopInfoService.insertShopInfo(shop, new FileInputStream(image), image.getName());
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }

    @Test
    public void updateShopInfoServiceTest() throws FileNotFoundException {
        ShopInfo shop = new ShopInfo();
        shop.setShopId(11);
        File image = new File("D:\\图片\\涩图\\1629107036280.jpg");
        Message message = shopInfoService.updateShopInfo(shop, new FileInputStream(image), "1629107036280.jpg");
        Assert.assertEquals(message.getInfo(), State.SUCCESS.getInfo());
    }

    @Test
    @Ignore
    public void selectShopInfoServiceTest(){
        ShopInfo condition = new ShopInfo();
        CampusArea area = new CampusArea();
        area.setCampusAreaId(2);
        condition.setCampusArea(area);
        condition.setShopName("店");
        Message message = shopInfoService.findShopInfo(condition, 2, 2);
        List<ShopInfo> shops = message.getList();
        Assert.assertEquals(1, shops.size());
    }
}
