package com.fuyusakaiori.csms.service;

import com.fuyusakaiori.csms.dto.ImageWrapper;
import com.fuyusakaiori.csms.dto.Message;
import com.fuyusakaiori.csms.entity.GoodsCategory;
import com.fuyusakaiori.csms.entity.GoodsInfo;
import com.fuyusakaiori.csms.entity.ShopInfo;
import com.fuyusakaiori.csms.exception.GoodsImageException;
import com.fuyusakaiori.csms.exception.GoodsInfoException;
import com.fuyusakaiori.csms.util.enums.State;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsInfoServiceTest
{
    @Resource
    private GoodsInfoService goodsInfoService;

    @Test
    public void insertGoodsInfoServiceTest() throws GoodsImageException, GoodsInfoException, FileNotFoundException
    {
        // 插入的商品
        GoodsInfo goods = new GoodsInfo();
        goods.setGoodsName("测试商品-1");
        goods.setPriority(10);
        goods.setStatus(1);
        GoodsCategory category = new GoodsCategory();
        category.setGoodsCategoryId(11);
        category.setShopId(9);
        goods.setCategory(category);
        ShopInfo shop = new ShopInfo();
        shop.setShopId(9);
        goods.setShopInfo(shop);
        // 插入的图片
        File file = new File("D:\\图片\\涩图\\illust_81903269_20210413_093023.jpg");
        ImageWrapper thumbnail = new ImageWrapper();
        thumbnail.setImage(new FileInputStream(file));
        thumbnail.setFilename(file.getName());

        List<ImageWrapper> detailImages = new ArrayList<>();
        for (int index = 0;index < 3;index++){
            ImageWrapper wrapper = new ImageWrapper();
            String filename = "D:\\图片\\涩图\\" + (index + 1) + ".jpg";
            wrapper.setImage(new FileInputStream(new File(filename)));
            wrapper.setFilename(filename);
            detailImages.add(wrapper);
        }
        Message<GoodsInfo> message = goodsInfoService.insertGoodsInfo(goods, thumbnail, detailImages);
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }

    @Test
    public void updateGoodsInfoServiceTest() throws FileNotFoundException, GoodsImageException, GoodsInfoException
    {
        GoodsInfo goods = new GoodsInfo();
        goods.setGoodsId(4);
        goods.setGoodsName("测试商品-3");
        goods.setPriority(10);
        goods.setStatus(1);
        goods.setDescription("测试商品-3的描述");
        ShopInfo shop = new ShopInfo();
        shop.setShopId(9);
        goods.setShopInfo(shop);
        // 更新的图片
        File file = new File("D:\\图片\\涩图\\1629107036216.jpg");
        ImageWrapper thumbnail = new ImageWrapper();
        thumbnail.setImage(new FileInputStream(file));
        thumbnail.setFilename(file.getName());
        List<ImageWrapper> detailImages = new ArrayList<>();
        for (int index = 0;index < 3;index++){
            ImageWrapper wrapper = new ImageWrapper();
            String filename = "D:\\图片\\涩图\\" + (index + 4) + ".jpg";
            wrapper.setImage(new FileInputStream(new File(filename)));
            wrapper.setFilename(filename);
            detailImages.add(wrapper);
        }
        Message<GoodsInfo> message = goodsInfoService.updateGoodsInfo(goods, thumbnail, detailImages);
        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }

    @Test
    public void findAllGoodsInfoServiceTest(){
        GoodsInfo condition = new GoodsInfo();
        condition.setGoodsName("测试商品");
        GoodsCategory category = new GoodsCategory();
        category.setGoodsCategoryName("测试");
        condition.setCategory(category);
        ShopInfo shop = new ShopInfo();
        shop.setShopId(9);
        condition.setShopInfo(shop);

        Message<GoodsInfo> message = goodsInfoService.findAllGoodsInfo(condition, 1, 2);
        message.getList().forEach(System.out::println);
        Assert.assertEquals(2, message.getList().size());
        Assert.assertEquals(3, message.getCount());
    }
}
