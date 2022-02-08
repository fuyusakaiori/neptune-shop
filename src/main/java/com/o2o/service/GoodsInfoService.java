package com.o2o.service;

import com.o2o.dto.ImageWrapper;
import com.o2o.dto.Message;
import com.o2o.entity.GoodsInfo;
import com.o2o.exception.GoodsImageException;
import com.o2o.exception.GoodsInfoException;

import java.util.List;

public interface GoodsInfoService
{
    /**
     * <h3>新增商品: 负责新增缩率图和详情图</h3>
     * @param goods 商品
     * @param thumbnail 商品的缩略图
     * @param detailImages 商品的详情图集合
     * @return 是否新增成功
     */
    Message<GoodsInfo> insertGoodsInfo(GoodsInfo goods, ImageWrapper thumbnail, List<ImageWrapper> detailImages) throws GoodsInfoException, GoodsImageException;

    /**
     * <h3>更新商品</h3>
     */
    Message<GoodsInfo> updateGoodsInfo(GoodsInfo condition, ImageWrapper thumbnail, List<ImageWrapper> detailImages) throws GoodsInfoException, GoodsImageException;

    /**
     * <h3>负责根据编号查询商品</h3>
     * @param id 商品编号
     */
    Message<GoodsInfo> findGoodsInfo(int id);

    /**
     * <h3>根据条件查询商品</h3>
     * @param condition 条件
     * @param pageIndex 页号
     * @param pageSize 页大小
     * @return 商品集合
     */
    Message<GoodsInfo> findAllGoodsInfo(GoodsInfo condition, int pageIndex, int pageSize);
}
