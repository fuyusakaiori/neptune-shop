package com.o2o.service.impl;

import com.o2o.dao.ShopInfoMapper;
import com.o2o.dto.ShopMessage;
import com.o2o.entity.ShopInfo;
import com.o2o.exception.ShopException;
import com.o2o.service.ShopInfoService;
import com.o2o.utils.ImageUtil;
import com.o2o.utils.PathUtil;
import com.o2o.utils.enums.ShopState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
public class ShopInfoServiceImpl implements ShopInfoService
{
    @Autowired
    private ShopInfoMapper shopInfoMapper;

    private static Logger logger = LoggerFactory.getLogger(ShopInfoServiceImpl.class);

    /**
     * 添加店铺信息
     * @param shop 店铺信息
     * @param image 店铺的缩略图（用户可以暂时不上传图片？）
     * @return 店铺信息是否添加成功
     */
    @Override
    // 确保事务失败之后可以回滚
    @Transactional
    public ShopMessage insertShopInfo(ShopInfo shop, File image)
    {
        // 1. 店铺信息为空, 是不可以直接添加的, 返回相应的信息
        if (shop == null)
            return new ShopMessage(ShopState.NULL_SHOP);
        // TODO 店铺信息中还包含对应的店铺老板、店铺类别、店铺所在区域都是需要相应的验证的

        // 2. 给店铺设置默认信息
        shop.setStatus(ShopState.CHECK.getState());
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        // 4. 添加店铺信息
        try {
            // 这里有个细节: 由于之前我们在 Mybatis 中配置了自增主键, 所以不仅会在表里自动生成主键, 还会给实体类自动生成主键
            int result = shopInfoMapper.insertShopInfo(shop);
            if (result <= 0)
                throw new ShopException("[添加店铺信息失败]-根本原因:\t");
        }
        catch (Exception e) {
            logger.error(e.toString());
            throw new ShopException("[添加店铺信息失败]-根本原因:\t" + e.getMessage());
        }
        // 3. 生成图片
        if (image != null){
            try {
                insertShopImage(shop, image);
                int result = shopInfoMapper.updateShopInfo(shop);
                if (result <= 0)
                    throw new ShopException("[添加图片失败]-根本原因:\t");
            }
            catch (Exception e) {
                logger.error(e.toString());
                throw new ShopException("[图片生成失败]-根本原因:\t" + e.getMessage());
            }
        }

        // 5. 店铺信息添加成功, 就可以将成功的信息返回
        return new ShopMessage(ShopState.SUCCESS, shop);
    }

    /**
     * <p>添加图片, 并且给店铺实体类设置图片的相对地址</p>
     * <p>这里只需要提供店铺的编号就可以, 工具类中会自动生成对应的绝对路径的前缀</p>
     * @param shop 店铺信息
     * @param image 图片地址
     */
    private void insertShopImage(ShopInfo shop, File image){
        logger.debug("{}",shop.getShopId());
        String imageURL = ImageUtil.generateThumbnailator(image,
                PathUtil.getShopImagePath(shop.getShopId()));
        shop.setImageURL(imageURL);
    }
}
