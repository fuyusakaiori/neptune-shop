package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryMapperTest extends BaseTest
{
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    @Test
    public void findAllShopCategoryMapperTest(){
        // 1. 测试查询的店铺类型数量是否和预期一致
        List<ShopCategory> categories = shopCategoryMapper.findAllShopCategory();
        Assert.assertEquals(4, categories.size());
        // 2. 根据子类型查询
        ShopCategory child = new ShopCategory();
        categories = shopCategoryMapper.findAllShopCategory(child);
        Assert.assertEquals(2, categories.size());
        categories.forEach(System.out::println);
    }
}
