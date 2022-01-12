package com.o2o.service;


import com.o2o.BaseTest;
import com.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryServiceTest extends BaseTest
{
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void findAllShopCategoryService(){
        List<ShopCategory> categories = shopCategoryService.findAllShopCategory();
        Assert.assertEquals(3, categories.size());
    }
}
