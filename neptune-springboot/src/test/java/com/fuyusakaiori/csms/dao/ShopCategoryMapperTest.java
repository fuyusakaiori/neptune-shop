package com.fuyusakaiori.csms.dao;


import com.fuyusakaiori.csms.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryMapperTest
{
    @Resource
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
