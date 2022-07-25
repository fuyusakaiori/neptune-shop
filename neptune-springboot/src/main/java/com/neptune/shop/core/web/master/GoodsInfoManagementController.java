package com.neptune.shop.core.web.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptune.shop.core.dto.ImageWrapper;
import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.GoodsCategory;
import com.neptune.shop.core.entity.GoodsInfo;
import com.neptune.shop.core.entity.ShopInfo;
import com.neptune.shop.core.exception.GoodsImageException;
import com.neptune.shop.core.exception.GoodsInfoException;
import com.neptune.shop.core.service.GoodsCategoryService;
import com.neptune.shop.core.service.GoodsInfoService;
import com.neptune.shop.core.util.RequestUtil;
import com.neptune.shop.core.util.captcha.CheckKaptcha;
import com.neptune.shop.core.util.enums.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop-management")
public class GoodsInfoManagementController
{
    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @RequestMapping(value = "/goods/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertGoodsInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 1. 验证码
        if (!CheckKaptcha.checkVerifyCode(request)){
            map.put("success", false);
            map.put("message", "验证码错误!");
            return map;
        }
        // 2. 解析对象
        String json = RequestUtil.getParameterByString(request, "goods");
        if (json == null){
            map.put("success", false);
            map.put("message", "传入的商品信息为空!");
            return map;
        }
        ObjectMapper mapper = new ObjectMapper();
        GoodsInfo goods = null;
        try {
            goods = mapper.readValue(json, GoodsInfo.class);
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        // 3. 解析图片
        ImageWrapper thumbnail;
        List<ImageWrapper> detailImages = new ArrayList<>();
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            thumbnail = setImage(request, detailImages);
        }
        catch (IOException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        // 4. 准备新增商品
        if (goods != null && thumbnail != null && detailImages.size() > 0){
            try {
                Object obj = request.getSession().getAttribute("shop");
                if (obj == null){
                    map.put("success", false);
                    map.put("message", "没有权限向当前店铺添加商品!");
                }
                ShopInfo shop = (ShopInfo) obj;
                goods.setShopInfo(shop);
                Message<GoodsInfo> message = goodsInfoService.insertGoodsInfo(goods, thumbnail, detailImages);
                if (message.getState() == State.SUCCESS.getState()){
                    map.put("success", true);
                }else{
                    map.put("success", false);
                    map.put("message", "新增商品信息失败!");
                }
            }
            catch (GoodsInfoException | GoodsImageException e) {
                map.put("success", false);
                map.put("message", e.getMessage());
            }
        }

        return map;
    }

    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateGoodsInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 如果只是修改商品状态, 那么就不需要经过验证码
        boolean status = RequestUtil.getParameterByBoolean(request, "status");
        if (!status && !CheckKaptcha.checkVerifyCode(request)){
            map.put("success", false);
            map.put("message", "验证码错误!");
            return map;
        }
        ShopInfo shop = (ShopInfo) request.getSession().getAttribute("shop");
        if (shop == null){
            map.put("success", false);
            map.put("message", "没有权限向店铺中添加商品");
        }

        String json = RequestUtil.getParameterByString(request, "goods");
        if (json == null){
            map.put("success", false);
            map.put("message", "传入的商品信息为空!");
            return map;
        }
        ObjectMapper mapper = new ObjectMapper();
        GoodsInfo goods = null;
        try {
            goods = mapper.readValue(json, GoodsInfo.class);
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }

        ImageWrapper thumbnail;
        List<ImageWrapper> detailImages = new ArrayList<>();
        try {
            thumbnail = setImage(request, detailImages);
        }
        catch (IOException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }

        if (goods.getGoodsId() > 0){
            try {
                goods.setShopInfo(shop);
                Message<GoodsInfo> message = goodsInfoService.updateGoodsInfo(goods, thumbnail, detailImages);
                if (message.getState() == State.SUCCESS.getState()){
                    map.put("success", true);
                }else{
                    map.put("success", false);
                    map.put("message", "新增商品信息失败!");
                }
            }
            catch (GoodsInfoException | GoodsImageException e) {
                map.put("success", false);
                map.put("message", e.getMessage());
            }
        }

        return map;
    }

    @RequestMapping(value = "/goods/find", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findGoodsInfoById(@RequestParam int id , HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        if (id <= 0){
            map.put("success", false);
            map.put("message", "商品编号为空!");
            return map;
        }
        try {
            Message<GoodsInfo> goodsMessage = goodsInfoService.findGoodsInfo(id);
            Message<GoodsCategory> categoryMessage = goodsCategoryService.findShopGoodsCategory(goodsMessage.getElement().getShopInfo().getShopId());
            if (goodsMessage.getState() == State.SUCCESS.getState() &&
                        categoryMessage.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("goods", goodsMessage.getElement());
                map.put("categories", categoryMessage.getList());
            }else{
                map.put("success", false);
                map.put("message", "查询商品信息失败!");
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAllGoodsInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        int pageIndex = RequestUtil.getParameterByInt(request, "pageIndex");
        int pageSize = RequestUtil.getParameterByInt(request, "pageSize");
        // 注: 空对象直接强制转换不会报错
        ShopInfo shop = (ShopInfo) request.getSession().getAttribute("shop");
        if (pageIndex >= 1 && pageSize >= 1 && shop != null && shop.getShopId() >= 1){
            GoodsInfo condition = new GoodsInfo();
            // 请求中提取出条件
            int categoryId = RequestUtil.getParameterByInt(request, "category");
            int status = RequestUtil.getParameterByInt(request, "status");
            String goodsName = RequestUtil.getParameterByString(request, "goodsName");
            // 设置条件
            if (goodsName != null) {
                condition.setGoodsName(goodsName);
            }
            if (categoryId >= 1){
                GoodsCategory category = new GoodsCategory();
                category.setGoodsCategoryId(categoryId);
                condition.setCategory(category);
            }
            if (status >= 1){
                condition.setStatus(status);
            }
            condition.setShopInfo(shop);
            // 开始查询
            try {
                Message<GoodsInfo> message = goodsInfoService.findAllGoodsInfo(condition, pageIndex, pageSize);
                if (message.getState() == State.SUCCESS.getState()){
                    map.put("success", true);
                    map.put("goodsList", message.getList());
                }else{
                    map.put("success", false);
                    map.put("message", message.getInfo());
                }
            }
            catch (Exception e) {
                map.put("success", false);
                map.put("message", e.getMessage());
            }
        }

        return map;
    }


    private ImageWrapper setImage(HttpServletRequest request, List<ImageWrapper> detailImages) throws IOException
    {
        ImageWrapper thumbnail = null;
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (cmr.isMultipart(request)){
            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
            CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) mhsr.getFile("thumbnail");
            if (thumbnailFile != null){
                thumbnail = new ImageWrapper();
                thumbnail.setImage(thumbnailFile.getInputStream());
                thumbnail.setFilename(thumbnailFile.getOriginalFilename());
            }
            CommonsMultipartFile detailImage = null;
            int index = 0;
            // 注: 只要取出来的图片流为空那么就跳出循环
            while ( (detailImage = (CommonsMultipartFile) mhsr.getFile("detail-" + (++index)) ) != null){
                ImageWrapper wrapper = new ImageWrapper();
                wrapper.setImage(detailImage.getInputStream());
                wrapper.setFilename(detailImage.getOriginalFilename());
                detailImages.add(wrapper);
            }
        }
        return thumbnail;
    }

}
