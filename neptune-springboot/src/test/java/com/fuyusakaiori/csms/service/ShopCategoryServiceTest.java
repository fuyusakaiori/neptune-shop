package com.fuyusakaiori.csms.service;


import com.fuyusakaiori.csms.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryServiceTest
{
    @Resource
    private ShopCategoryService shopCategoryService;

    @Test
    public void findAllShopCategoryService(){
        List<ShopCategory> categories = shopCategoryService.findAllShopCategory();
        Assert.assertEquals(3, categories.size());
    }
}
