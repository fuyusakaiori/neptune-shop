package com.neptune.shop.core.web.index;

import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.GoodsCategory;
import com.neptune.shop.core.entity.GoodsInfo;
import com.neptune.shop.core.entity.ShopInfo;
import com.neptune.shop.core.service.GoodsCategoryService;
import com.neptune.shop.core.service.GoodsInfoService;
import com.neptune.shop.core.service.ShopInfoService;
import com.neptune.shop.core.util.RequestUtil;
import com.neptune.shop.core.util.enums.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/index")
public class ShopDetailController
{
    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private ShopInfoService shopInfoService;

    @RequestMapping(value = "/init-shop-detail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInfoDetail(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        int id = RequestUtil.getParameterByInt(request, "id");
        if (id >= 1){
            Message<ShopInfo> shopMessage = shopInfoService.findShopInfoById(id);
            if (shopMessage.getState() == State.SUCCESS.getState()){
                Message<GoodsCategory> goodsCategoryMessage = goodsCategoryService.findShopGoodsCategory(id);
                if (goodsCategoryMessage.getState() == State.SUCCESS.getState()){
                    map.put("success", true);
                    map.put("categories", goodsCategoryMessage.getList());
                    map.put("shop", shopMessage.getElement());
                }else{
                    map.put("success", false);
                    map.put("message", "商品类型信息查询失败!");
                }
            }else{
                map.put("success", false);
                map.put("message", "店铺信息查询失败!");
            }
        }else{
            map.put("success", false);
            map.put("message", "店铺信息为空!");
        }
        return map;
    }

    @RequestMapping(value = "/search-goods-init", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchGoodsInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        int pageIndex = RequestUtil.getParameterByInt(request, "pageIndex");
        int pageSize = RequestUtil.getParameterByInt(request, "pageSize");
        int id = RequestUtil.getParameterByInt(request, "id");
        if (pageIndex >= 1 && pageSize >= 1 && id >= 1){
            int categoryId = RequestUtil.getParameterByInt(request, "categoryId");
            String goodsName = RequestUtil.getParameterByString(request, "goodsName");
            Message<GoodsInfo> goodsMessage = goodsInfoService.findAllGoodsInfo(getGoodsCondition(id, categoryId, goodsName), pageIndex, pageSize);
            if (goodsMessage.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("goodsList", goodsMessage.getList());
            }else{
                map.put("success", false);
                map.put("message", "查询商品信息失败!");
            }
        }else{
            map.put("success", false);
            map.put("message", "传入的页号、页码或者店铺编号错误!");
        }
        return map;
    }

    private GoodsInfo getGoodsCondition(int id, int categoryId, String goodsName){
        GoodsInfo condition = new GoodsInfo();
        ShopInfo shop = new ShopInfo();
        shop.setShopId(id);
        if (categoryId >= 1){
            GoodsCategory category = new GoodsCategory();
            category.setGoodsCategoryId(categoryId);
            condition.setCategory(category);
        }
        if (goodsName != null){
            condition.setGoodsName(goodsName);
        }
        condition.setStatus(1);
        condition.setShopInfo(shop);
        return condition;
    }
}
