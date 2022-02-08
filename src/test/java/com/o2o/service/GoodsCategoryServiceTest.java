package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.TestUtils;
import com.o2o.dto.Message;
import com.o2o.entity.GoodsCategory;
import com.o2o.exception.GoodsCategoryException;
import com.o2o.utils.enums.State;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsCategoryServiceTest extends BaseTest
{
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Test
    public void B_findShopCategoryServiceTest(){
        Message<GoodsCategory> message = goodsCategoryService.findShopGoodsCategory(11);
        message.getList().forEach(System.out::println);
        Assert.assertEquals(message.getState(), State.SUCCESS.getState());
    }

    @Test
    public void A_batchInsertGoodsCategoryServiceTest() throws GoodsCategoryException {
        Message<GoodsCategory> message = goodsCategoryService.batchInsertGoodsCategory(TestUtils.getGoodsCategory());
        Assert.assertEquals(message.getState(), State.SUCCESS.getState());
    }

    @Test
    public void C_deleteGoodsCategoryServiceTest() throws GoodsCategoryException {
        Message<GoodsCategory> message = goodsCategoryService.deleteGoodsCategory(12, 11);

        Assert.assertEquals(State.SUCCESS.getState(), message.getState());
    }
}
