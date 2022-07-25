package com.neptune.shop.service;

import com.neptune.shop.core.dto.ImageWrapper;
import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.CampusArea;
import com.neptune.shop.core.entity.ShopInfo;
import com.neptune.shop.core.service.ShopInfoService;
import com.neptune.shop.core.util.enums.State;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopInfoServiceTest
{
    @Resource
    private ShopInfoService shopInfoService;

    @Test
    @Ignore
    public void insertShopInfoServiceTest() throws FileNotFoundException {
        ShopInfo shop = null;
        File image = null;
        ImageWrapper wrapper = new ImageWrapper();
        wrapper.setFilename(image.getName());
        wrapper.setImage(new FileInputStream(image));
        Message<ShopInfo> message = shopInfoService.insertShopInfo(shop, wrapper);
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }

    @Test
    public void updateShopInfoServiceTest() throws FileNotFoundException
    {
        ShopInfo shop = new ShopInfo();
        shop.setShopId(11);
        File image = new File("D:\\图片\\涩图\\1629107036280.jpg");
        ImageWrapper wrapper = new ImageWrapper();
        wrapper.setFilename(image.getName());
        wrapper.setImage(new FileInputStream(image));
        Message<ShopInfo> message = shopInfoService.updateShopInfo(shop, wrapper);
        Assert.assertEquals(message.getInfo(), State.SUCCESS.getInfo());
    }

    @Test
    public void selectShopInfoServiceTest(){
        ShopInfo condition = new ShopInfo();
        CampusArea area = new CampusArea();
        area.setCampusAreaId(2);
        condition.setCampusArea(area);
        condition.setShopName("店");
        Message<ShopInfo> message = shopInfoService.findShopInfo(condition, 2, 2);
        List<ShopInfo> shops = message.getList();
        Assert.assertEquals(1, shops.size());
    }
}
