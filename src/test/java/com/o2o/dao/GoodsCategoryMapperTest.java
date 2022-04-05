package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.TestUtils;
import com.o2o.entity.GoodsCategory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsCategoryMapperTest extends BaseTest
{

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Test
    @Ignore
    public void B_findShopGoodsCategoryMapperTest(){
        List<GoodsCategory> categories = goodsCategoryMapper.findShopGoodsCategory(9);
        categories.forEach(System.out::println);
        Assert.assertEquals(5, categories.size());
    }


    @Test
    @Ignore
    public void A_batchInsertGoodsCategoryMapperTest(){
        // 刚才的问题在于 SQL 出现异常, 导致 mapper 无法被正常解析, 出现在第三个异常中
        int result = goodsCategoryMapper.batchInsertGoodsCategory(TestUtils.getGoodsCategory());
        System.out.printf("插入的行数: {%d}", result);
    }

    @Test
    @Ignore
    public void A_deleteGoodsCategoryMapperTest(){
        int result = goodsCategoryMapper.deleteGoodsCategory(11, 11);
        Assert.assertEquals(1, result);
    }
}
