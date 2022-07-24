package com.o2o.dao;


import com.o2o.BaseTest;
import com.o2o.entity.GoodsImage;
import com.o2o.entity.GoodsInfo;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsImageMapperTest extends BaseTest
{
    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Test
    public void A_batchInsertGoodsImageMapperTest(){
        List<GoodsImage> images = new ArrayList<>();
        for (int index = 0;index < 3;index++){
            GoodsImage image = new GoodsImage();
            image.setUrl("图片-" + (index + 1) + "的地址");
            image.setDescription("这是图片" + (index + 1) + "的描述");
            image.setPriority(index + 1);
            GoodsInfo goods = new GoodsInfo();
            goods.setGoodsId(3);
            image.setGoods(goods);
            images.add(image);
        }
        int result = goodsImageMapper.batchInsertGoodsImage(images);
        Assert.assertEquals(3, result);
    }

    @Test
    public void B_findAllGoodsImageMapperTest(){
        List<GoodsImage> images = goodsImageMapper.findAllGoodsImage(3);
        images.forEach(System.out::println);
        Assert.assertEquals(3, images.size());
    }

    @Test
    public void C_deleteGoodsImageMapperTest(){
        int result = goodsImageMapper.deleteGoodsImage(3);
        Assert.assertEquals(3, result);
    }
}
