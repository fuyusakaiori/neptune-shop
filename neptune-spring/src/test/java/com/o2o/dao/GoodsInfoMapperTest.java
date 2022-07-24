package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.GoodsCategory;
import com.o2o.entity.GoodsInfo;
import com.o2o.entity.ShopInfo;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsInfoMapperTest extends BaseTest
{
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Test
    @Ignore
    public void A_insertGoodsInfoMapperTest(){
        GoodsInfo goods = new GoodsInfo();
        goods.setGoodsName("测试商品-4");
        goods.setPriority(10);
        goods.setStatus(1);
        GoodsCategory category = new GoodsCategory();
        category.setGoodsCategoryId(11);
        category.setShopId(9);
        goods.setCategory(category);
        ShopInfo shop = new ShopInfo();
        shop.setShopId(9);
        goods.setShopInfo(shop);
        int result = goodsInfoMapper.insertGoodsInfo(goods);
        Assert.assertEquals(1, result);
    }

    @Test
    @Ignore
    public void updateGoodsInfoMapperTest(){
        GoodsInfo goods = new GoodsInfo();
        goods.setGoodsId(4);
        goods.setUpdateTime(new Date());
        goods.setGoodsName("测试商品-2");
        goods.setDescription("测试商品-2的描述");
        int result = goodsInfoMapper.updateGoodsInfo(goods);
        Assert.assertEquals(1, result);
    }

    @Test
    @Ignore
    public void findGoodsInfoByIdMapperTest(){
        GoodsInfo goods = goodsInfoMapper.findGoodsInfoById(4);
        System.out.println(goods);
    }

    @Test
    public void B_findAllGoodsInfoMapperTest(){
        GoodsInfo condition = new GoodsInfo();
        condition.setGoodsName("测试商品");
        GoodsCategory category = new GoodsCategory();
        category.setGoodsCategoryName("测试");
        condition.setCategory(category);
        ShopInfo shop = new ShopInfo();
        shop.setShopId(9);
        condition.setShopInfo(shop);

        List<GoodsInfo> goodsList = goodsInfoMapper.findAllGoodsInfo(condition, 1, 2);
        goodsList.forEach(System.out::println);
        Assert.assertEquals(2, goodsList.size());
        int count = goodsInfoMapper.getGoodsInfoCount(condition);
        Assert.assertEquals(3, count);
    }
}
