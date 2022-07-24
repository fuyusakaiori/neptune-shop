package com.fuyusakaiori.csms.service;


import com.fuyusakaiori.csms.dto.Message;
import com.fuyusakaiori.csms.entity.GoodsCategory;
import com.fuyusakaiori.csms.exception.GoodsCategoryException;
import com.fuyusakaiori.csms.util.enums.State;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsCategoryServiceTest
{
    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Test
    public void B_findShopCategoryServiceTest()
    {
        Message<GoodsCategory> message = goodsCategoryService.findShopGoodsCategory(11);
        message.getList().forEach(System.out::println);
        Assert.assertEquals(message.getState(), State.SUCCESS.getState());
    }

    @Test
    public void A_batchInsertGoodsCategoryServiceTest() throws GoodsCategoryException
    {
        Message<GoodsCategory> message = goodsCategoryService.batchInsertGoodsCategory(null);
        Assert.assertEquals(message.getState(), State.SUCCESS.getState());
    }

    @Test
    public void C_deleteGoodsCategoryServiceTest() throws GoodsCategoryException
    {
        Message<GoodsCategory> message = goodsCategoryService.deleteGoodsCategory(11, 9);

        Assert.assertEquals(State.FAILURE.getState(), message.getState());
    }
}
