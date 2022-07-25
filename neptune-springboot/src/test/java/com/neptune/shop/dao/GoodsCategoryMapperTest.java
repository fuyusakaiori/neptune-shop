package com.neptune.shop.dao;


import com.neptune.shop.core.dao.GoodsCategoryMapper;
import com.neptune.shop.core.entity.GoodsCategory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsCategoryMapperTest
{

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Test
    public void B_findShopGoodsCategoryMapperTest(){
        List<GoodsCategory> categories = goodsCategoryMapper.findShopGoodsCategory(9);
        categories.forEach(System.out::println);
        Assert.assertEquals(5, categories.size());
    }


    @Test
    public void A_batchInsertGoodsCategoryMapperTest(){
        // 刚才的问题在于 SQL 出现异常, 导致 mapper 无法被正常解析, 出现在第三个异常中
        int result = goodsCategoryMapper.batchInsertGoodsCategory(null);
        System.out.printf("插入的行数: {%d}", result);
    }

    @Test
    public void A_deleteGoodsCategoryMapperTest(){
        int result = goodsCategoryMapper.deleteGoodsCategory(11, 11);
        Assert.assertEquals(1, result);
    }
}
