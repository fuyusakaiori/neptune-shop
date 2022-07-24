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
        // 1. 根据子类型查询
        ShopCategory child = new ShopCategory();
        List<ShopCategory> categories = shopCategoryMapper.findAllShopCategory();
        Assert.assertEquals(3, categories.size());
        categories.forEach(System.out::println);
    }
}
