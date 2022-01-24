package com.o2o.service.impl;

import com.o2o.dao.ShopInfoMapper;
import com.o2o.dto.ShopMessage;
import com.o2o.entity.ShopInfo;
import com.o2o.exception.ShopException;
import com.o2o.service.ShopInfoService;
import com.o2o.utils.ImageUtil;
import com.o2o.utils.PageUtil;
import com.o2o.utils.PathUtil;
import com.o2o.utils.enums.ShopState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopInfoServiceImpl implements ShopInfoService
{
    @Autowired
    private ShopInfoMapper shopInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(ShopInfoServiceImpl.class);

    /**
     * 添加店铺信息
     * @param shop 店铺信息
     * @param image 店铺的缩略图（用户可以暂时不上传图片？）
     * @return 店铺信息是否添加成功
     */
    @Override
    // 确保事务失败之后可以回滚
    @Transactional
    public ShopMessage insertShopInfo(ShopInfo shop, InputStream image, String fileName)
    {
        // 1. 店铺信息为空, 是不可以直接添加的, 返回相应的信息
        if (shop == null)
            return new ShopMessage(ShopState.NULL_SHOP);
        // TODO 店铺信息中还包含对应的店铺老板、店铺类别、店铺所在区域都是需要相应的验证的

        // 2. 给店铺设置默认信息
        shop.setStatus(ShopState.CHECK.getState());
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        // 3. 添加店铺信息
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
        // 4. 生成图片
        if (image != null){
            try {
                insertShopImage(shop, image, fileName);
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
     * <p>根据店铺编号返回店铺信息</p>
     */
    @Override
    public ShopMessage findShopInfoById(int id) {
        // 0. 如果传入的店铺编号是非法的, 那么返回的店铺信息也就是非法的
        return id > 0 ? new ShopMessage(ShopState.SUCCESS, shopInfoMapper.findShopInfoById(id)):
                       new ShopMessage(ShopState.NULL_SHOP);
    }

    @Override
    @Transactional
    public ShopMessage updateShopInfo(ShopInfo shop, InputStream image, String fileName) throws ShopException {
        // 0. 检查传入的店铺信息是否非法: 店铺 ID 没有办法更改吧, 这个判断有必要吗?
        if (shop == null || shop.getShopId() <= 0) return new ShopMessage(ShopState.FAILURE);
        // 1. 检查是否需要更新图片: 如果传入的流不等于空那就需要更新图片
        try {
            if (image != null){
                // 1.1 先查询到原来店铺的图片地址
                ShopInfo update = shopInfoMapper.findShopInfoById(shop.getShopId());
                logger.debug("查询得到的 img_url :{}", update.getImageURL());
                // 1.2 如果此前已经上传过图片了, 那么就需要删除此前的图片（每个店铺只拥有一张图片）
                if (update.getImageURL() != null){
                    if(!ImageUtil.deleteImagePath(update.getImageURL()))
                        throw new ShopException(ShopState.FAILURE.getInfo());
                }
                // 注: 插入不成功的原因: 水印的源文件被删除了
                insertShopImage(shop, image, fileName);
            }
            // 2. 更新店铺信息
            shop.setUpdateTime(new Date());
            int result = shopInfoMapper.updateShopInfo(shop);
            if (result <= 0){
                return new ShopMessage(ShopState.FAILURE);
            }
            return new ShopMessage(ShopState.SUCCESS, shop);
        }
        catch (Exception e) {
            throw new ShopException(e.getMessage());
        }
    }

    @Override
    public ShopMessage findShopInfo(ShopInfo condition, int pageIndex, int pageSize) {
        int start = PageUtil.pageIndexToRowIndex(pageSize, pageIndex);
        List<ShopInfo> shops = shopInfoMapper.findShopInfo(condition, start, pageSize);
        int count = shopInfoMapper.getShopInfoCount(condition);

        return shops == null || shops.size() == 0 ? new ShopMessage(ShopState.NULL_SHOP):
                       new ShopMessage(ShopState.SUCCESS, shops, count);
    }

    /**
     * <p>添加图片, 并且给店铺实体类设置图片的相对地址</p>
     * <p>这里只需要提供店铺的编号就可以, 工具类中会自动生成对应的绝对路径的前缀</p>
     * @param shop 店铺信息
     * @param image 图片对应的流对象
     * @param fileName 图片原始名称
     */
    private void insertShopImage(ShopInfo shop, InputStream image, String fileName){

        logger.debug("{}",shop.getShopId());
        String imageURL = ImageUtil.generateThumbnailator(image,
                fileName, PathUtil.getShopImagePath(shop.getShopId()));
        shop.setImageURL(imageURL);
    }
}
