package com.o2o;

import com.o2o.entity.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <h2>测试工具类</h2>
 */
public class TestUtils
{

    /**
     * 生成用于测试使用的店铺信息实例
     * @return 店铺信息测试用例
     */
    public static ShopInfo getShopInstance(){
        // 1. 创建插入对象
        ShopInfo shop = new ShopInfo();
        // 2. 设置相关数据
        shop.setShopName("测试用例~");
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        UserInfo master = new UserInfo();
        master.setUserId(1);
        shop.setMaster(master);
        CampusArea area = new CampusArea();
        area.setCampusAreaId(2);
        shop.setCampusArea(area);
        ShopCategory category = new ShopCategory();
        category.setShopCategoryId(1);
        shop.setCategory(category);

        return shop;
    }

    /**
     * 生成图片测试用例
     * @return 图片文件类
     */
    public static File getImageFile(){
        return new File("D:\\图片\\涩图\\1637216699245.jpg");
    }

    public static List<GoodsCategory> getGoodsCategory(){
        List<GoodsCategory> categories = new ArrayList<>();
        GoodsCategory first = new GoodsCategory();
        first.setGoodsCategoryName("景品");
        first.setPriority(2);
        first.setShopId(11);
        Collections.addAll(categories, first);
        return categories;
    }
}
