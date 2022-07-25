package com.neptune.shop.core.service.impl;


import com.neptune.shop.core.dao.GoodsImageMapper;
import com.neptune.shop.core.dao.GoodsInfoMapper;
import com.neptune.shop.core.dto.ImageWrapper;
import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.GoodsImage;
import com.neptune.shop.core.entity.GoodsInfo;
import com.neptune.shop.core.exception.GoodsImageException;
import com.neptune.shop.core.exception.GoodsInfoException;
import com.neptune.shop.core.service.GoodsInfoService;
import com.neptune.shop.core.util.ImageUtil;
import com.neptune.shop.core.util.PageUtil;
import com.neptune.shop.core.util.PathUtil;
import com.neptune.shop.core.util.enums.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GoodsInfoServiceImpl implements GoodsInfoService
{
    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsImageMapper goodsImageMapper;


    @Override
    @Transactional
    public Message<GoodsInfo> insertGoodsInfo(GoodsInfo goods, ImageWrapper thumbnail, List<ImageWrapper> detailImages) throws GoodsInfoException, GoodsImageException
    {
        // 1. 如果传入的商品信息为空、或者商品所属的店铺信息有误那么都会直接返回
        if (goods == null || goods.getShopInfo() == null || goods.getShopInfo().getShopId() <= 0)
            return new Message<>(State.FAILURE);
        // 2 先添加缩略图后给对象设置相应的图片地址: 因为商品信息中包含了店铺编号, 可以找到对应的路径
        if (thumbnail != null){
            setImageURL(goods, thumbnail);
        }
        // 3. 先添加商品信息
        try {
            goods.setCreateTime(new Date());
            goods.setUpdateTime(new Date());
            goods.setStatus(1);
            int result = goodsInfoMapper.insertGoodsInfo(goods);
            if (result <= 0)
                throw new GoodsInfoException("添加商品类型失败! 影响行数=[" + result + "]");
        }
        catch (Exception e) {
            throw new GoodsInfoException("添加商品类型失败!");
        }
        // 4 开始添加详情图
        if (detailImages != null && !detailImages.isEmpty()){
            setDetailImage(goods, detailImages);
        }

        return new Message<>(State.SUCCESS);
    }

    @Override
    @Transactional
    public Message<GoodsInfo> updateGoodsInfo(GoodsInfo condition, ImageWrapper thumbnail, List<ImageWrapper> detailImages)
            throws GoodsInfoException, GoodsImageException {
        if (condition == null || condition.getShopInfo() == null || condition.getShopInfo().getShopId() <= 0 || condition.getGoodsId() <= 0)
            return new Message<>(State.FAILURE);
        GoodsInfo goods;
        try {
            // 1. 先查询需要更新的商品
            goods = goodsInfoMapper.findGoodsInfoById(condition.getGoodsId());
        }
        catch (Exception e) {
            throw new GoodsInfoException("查询商品信息失败!");
        }

        // 2. 如果传入的缩率图不为空, 就更新图片
        if (thumbnail != null){
            if (!ImageUtil.deleteImagePath(goods.getImageURL()))
                throw new GoodsImageException("删除商品旧缩略图失败!");
            setImageURL(condition, thumbnail);
        }
        // 3. 更新商品信息
        try {
            condition.setUpdateTime(new Date());
            int result = goodsInfoMapper.updateGoodsInfo(condition);
            if (result <= 0)
                throw new GoodsImageException("更新商品信息失败!");
        }
        catch (Exception e) {
            throw new GoodsImageException("更新商品信息出现异常! 错误原因:[" + e.getMessage() + "]");
        }
        // 4. 如果传入的详情图集合不为空, 那也更新图片
        if (detailImages != null && !detailImages.isEmpty()){
            for (GoodsImage image : goods.getImages()) {
                if (!ImageUtil.deleteImagePath(image.getUrl())){
                    throw new GoodsImageException("删除商品详情图失败!");
                }
            }
            log.debug("商品详情图删除成功!");
            // 移除详情图表中的相应的记录
            try {
                int result = goodsImageMapper.deleteGoodsImage(goods.getGoodsId());
                if (result <= 0)
                    throw new GoodsImageException("删除详情图记录失败!");
            }
            catch (GoodsImageException e) {
                throw new GoodsImageException("删除详情图记录出现异常! 错误原因:[" + e.getMessage() + "]");
            }
            setDetailImage(condition, detailImages);
        }

        return new Message<>(State.SUCCESS);
    }

    @Override
    public Message<GoodsInfo> findGoodsInfo(int id) {
        if (id <= 0)
            return new Message<>(State.INVALID_GOODS_ID);
        GoodsInfo goods = goodsInfoMapper.findGoodsInfoById(id);
        return new Message<>(State.SUCCESS, goods);
    }

    @Override
    public Message<GoodsInfo> findAllGoodsInfo(GoodsInfo condition, int pageIndex, int pageSize) {
        int start = PageUtil.pageIndexToRowIndex(pageSize, pageIndex);
        List<GoodsInfo> goodsList = goodsInfoMapper.findAllGoodsInfo(condition, start, pageSize);
        int count = goodsInfoMapper.getGoodsInfoCount(condition);
        return goodsList.isEmpty() ? new Message<>(State.NULL) : new Message<>(State.SUCCESS, goodsList, count);
    }

    /**
     * <h3>负责设置缩略图</h3>
     * @param goods 商品
     * @param thumbnail 缩略图
     */
    private void setImageURL(GoodsInfo goods, ImageWrapper thumbnail){
        String target = PathUtil.getShopImagePath(goods.getShopInfo().getShopId());
        String relative = ImageUtil.generateThumbnailator(thumbnail, target);
        goods.setImageURL(relative);
    }

    /**
     * <h3>负责设置详情图</h3>
     * @param goods 商品
     * @param detailImages 详情图
     */
    private void setDetailImage(GoodsInfo goods, List<ImageWrapper> detailImages) throws GoodsImageException{
        List<GoodsImage> images = new ArrayList<>();
        detailImages.forEach(wrapper -> {
            String relative = ImageUtil.generateThumbnailator(wrapper, PathUtil.getShopImagePath(goods.getShopInfo().getShopId()));
            GoodsImage image = new GoodsImage();
            image.setUrl(relative);
            image.setGoods(goods);
            image.setCreateTime(new Date());
            images.add(image);
        });

        if (!images.isEmpty()){
            try {
                int result = goodsImageMapper.batchInsertGoodsImage(images);
                if (result <= 0)
                    throw new GoodsImageException("批量插入图片失败! 影响行数=[" + result + "]");
            }
            catch (GoodsImageException e) {
                throw new GoodsImageException("批量插入图片失败! 错误原因:[" + e.getMessage() + "]");
            }
        }
    }
}
