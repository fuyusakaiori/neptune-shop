package com.o2o.service.impl;

import com.o2o.dao.ShopCategoryMapper;
import com.o2o.entity.ShopCategory;
import com.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService
{
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    /**
     * <h3>传入店铺类型一定要带有父类型, 否则查询出来的就是全部店铺类型</h3>
     * @param category 父类型
     * @return 店铺类型列表
     */
    @Override
    public List<ShopCategory> findAllShopCategory(ShopCategory category) {
        return shopCategoryMapper.findAllShopCategory(category);
    }

    /**
     * <h3>查询所有的店铺类型</h3>
     * @return 店铺类型列表
     */
    @Override
    public List<ShopCategory> findAllShopCategory() {
        return shopCategoryMapper.findAllShopCategory();
    }
}
