package com.o2o.service.impl;

import com.o2o.dao.GoodsCategoryMapper;
import com.o2o.dto.Message;
import com.o2o.entity.GoodsCategory;
import com.o2o.exception.GoodsCategoryException;
import com.o2o.service.GoodsCategoryService;
import com.o2o.utils.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService
{
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public Message<GoodsCategory> findShopGoodsCategory(int id) {
        // 传入的店铺编号是非法的, 所以不会查询到任何产品信息
        if (id <= 0) return new Message<>(State.NULL_SHOPID);
        List<GoodsCategory> categories = goodsCategoryMapper.findShopGoodsCategory(id);
        return new Message<>(State.SUCCESS, categories, categories.size());
    }

    @Override
    @Transactional
    public Message<GoodsCategory> batchInsertGoodsCategory(List<GoodsCategory> categories) throws GoodsCategoryException {
        // 1. 传入的集合为空的时候, 就不执行插入操作
        if(categories == null || categories.isEmpty())
            return new Message<>(State.NULL_SHOP);
        try{
            // 3. 在实际添加商品类型之前, 需要新增每个商品类型创建的时间
            categories.forEach((category) -> {
                category.setCreateTime(new Date());
            });
            // 4. 根据返回结果确定是否添加成功, 如果没有任何的影响行数那么就是失败了
            int result = goodsCategoryMapper.batchInsertGoodsCategory(categories);
            if (result <= 0)
                throw new GoodsCategoryException("新增商品类型失败!");
        }catch (Exception e){
            // 2. 如果添加数据出现异常, 那么就需要抛出异常让事务回滚
            throw new GoodsCategoryException("新增商品类型失败![错误原因: " + e.getMessage() + "]");
        }
        return new Message<>(State.SUCCESS);
    }

    @Override
    @Transactional
    // TODO 如果某个商品类型被删除了, 那么该商品类型下所有的商品都需要将其类型置为空
    public Message<GoodsCategory> deleteGoodsCategory(int categoryId, int shopId) throws GoodsCategoryException {
        // 1. 如果传入的类型编号或者说店铺编号为空, 那么都是直接返回
        if (categoryId <= 0) return new Message<>(State.NULL_CATEGORY_ID);
        if (shopId <= 0) return new Message<>(State.NULL_SHOPID);

        try {
            int result = goodsCategoryMapper.deleteGoodsCategory(categoryId, shopId);
            // 2. 如果影响的行数那么就需要事务回滚
            if (result <= 0)
                throw new GoodsCategoryException("删除商品类型失败!");
        }
        catch (Exception e) {
            throw new GoodsCategoryException("删除商品类型错误![错误原因: " + e.getMessage() + "]");
        }
        return new Message<>(State.SUCCESS);
    }

}
