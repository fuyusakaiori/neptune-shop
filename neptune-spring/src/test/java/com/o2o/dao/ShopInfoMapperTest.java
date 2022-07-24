package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.CampusArea;
import com.o2o.entity.ShopCategory;
import com.o2o.entity.ShopInfo;
import com.o2o.entity.UserInfo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopInfoMapperTest extends BaseTest
{
    @Autowired
    private ShopInfoMapper shopInfoMapper;

    /**
     * <p>注意提前准备测试数据</p>
     * <p>@Ignore 忽略该单元测试</p>
     * <p>数据库和表的字符集设置存在问题</p>
     */
    @Test
    @Ignore
    public void insertShopInfoMapper(){
        // 1. 创建插入对象
        ShopInfo shop = new ShopInfo();
        // 2. 设置相关数据
        shop.setShopName("test case");
        shop.setDescription("test case");
        shop.setAddress("test case");
        shop.setPhone("test case");
        shop.setPhone("test case");
        shop.setImageURL("test case");
        shop.setPriority(0);
        shop.setStatus(1);
        shop.setAdvice("test case");
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        // 注意: 使用 Set 方法给成员变量赋值, 比采用构造方法会灵活很多
        UserInfo master = new UserInfo();
        master.setUserId(1);
        shop.setMaster(master);
        CampusArea area = new CampusArea();
        area.setCampusAreaId(2);
        shop.setCampusArea(area);
        ShopCategory category = new ShopCategory();
        category.setShopCategoryId(1);
        shop.setCategory(category);
        // 3. 验证是否插入成功
        Assert.assertEquals(1, shopInfoMapper.insertShopInfo(shop));
    }

    @Test
    @Ignore
    public void updateShopInfoTest(){
        ShopInfo shop = new ShopInfo();
        shop.setShopId(1);
        shop.setAddress("unknown");
        shop.setAdvice("checking");
        shop.setUpdateTime(new Date());
        Assert.assertEquals(1, shopInfoMapper.updateShopInfo(shop));
    }

    @Test
    @Ignore
    public void selectShopInfoByIdMapper(){
        ShopInfo shop = shopInfoMapper.findShopInfoById(9);
        System.out.println(shop);
    }

    @Test
    @Ignore
    public void selectShopInfoMapper(){
        ShopInfo condition = new ShopInfo();
        ShopCategory category = new ShopCategory();
        category.setParentCategoryId(3);
        condition.setCategory(category);
        int count = shopInfoMapper.getShopInfoCount(condition);
        System.out.printf("查询到的店铺数量: %d\n", count);
        Assert.assertEquals(3, count);
        List<ShopInfo> shops = shopInfoMapper.findShopInfo(condition, 0, 10);
        shops.forEach(System.out::println);
        Assert.assertEquals(3, shops.size());
    }

}
