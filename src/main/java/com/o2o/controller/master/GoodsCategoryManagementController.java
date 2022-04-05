package com.o2o.controller.master;

import com.o2o.dto.Message;
import com.o2o.entity.GoodsCategory;
import com.o2o.entity.ShopInfo;
import com.o2o.exception.GoodsCategoryException;
import com.o2o.service.GoodsCategoryService;
import com.o2o.utils.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop-management")
public class GoodsCategoryManagementController
{
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    private static final Logger logger = LoggerFactory.getLogger(GoodsCategoryManagementController.class);

    @RequestMapping(value = "/goods-category/find", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findShopGoodsCategory(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        Object object = request.getSession().getAttribute("shop");
        if (object == null) {
            map.put("success", false);
            map.put("message", "没有权限访问该店铺的商品类型信息!");
            return map;
        }
        try {
            ShopInfo shop = (ShopInfo) object;
            Message<GoodsCategory> message = goodsCategoryService.findShopGoodsCategory(shop.getShopId());
            if (message.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("categories", message.getList());
            }else{
                map.put("success", false);
                map.put("message", message.getInfo());
            }
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * <h3>1. 这个注解的作用就等同于从请求中取出 JSON 字符串然后解析成对象</h3>
     * <h3>2. 也就说这个注解可以直接帮你完成 JSON 字符串解析的过程</h3>
     * TODO 注: 源码之后需要了解
     * @param categories 添加的商品类型集合
     */
    @RequestMapping(value = "/goods-category/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertShopGoodsCategory(@RequestBody List<GoodsCategory> categories, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 1. 检验用户是否有全向向当前的店铺添加商品类型: 也就是防止将 1 号店铺的商品类型添加到 2 号店铺中去
        try {
            // 2. 这里可能发生强制转换异常, 所以还是将其放在异常快中
            Object obj = request.getSession().getAttribute("shop");
            if (obj == null){
                map.put("success", false);
                map.put("message", "无法向店铺添加商品类型");
            }else{
                // 3. 判断传入的集合是否为空
                if (categories == null || categories.isEmpty()){
                    map.put("success", false);
                    map.put("message", "传入的商品类型集合为空!");
                }else{
                    // 4. 设置每个商品类型的店铺信息
                    ShopInfo shop = (ShopInfo) obj;
                    categories.forEach(category -> category.setShopId(shop.getShopId()));
                    Message<GoodsCategory> message = goodsCategoryService.batchInsertGoodsCategory(categories);
                    if (message.getState() == State.SUCCESS.getState()){
                        map.put("success", true);
                    }else{
                        map.put("success", false);
                        map.put("message", "商品类型添加失败!");
                    }
                }
            }
        }
        catch (GoodsCategoryException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return map;
    }


    @RequestMapping(value = "/goods-category/delete", method = RequestMethod.POST)
    @ResponseBody
    // TODO 研究接收参数的方式
    public Map<String, Object> deleteShopGoodsCategory(Integer categoryId, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 1. 从权限的角度验证是否可以删除
        Object obj = request.getSession().getAttribute("shop");
        if (obj == null || categoryId == null || categoryId <= 0){
            map.put("success", false);
            map.put("message", "没有权限删除商品或者传入的商品类型编号错误!");
            return map;
        }
        try {
            ShopInfo shop = (ShopInfo) obj;
            Message<GoodsCategory> categoryMessage = goodsCategoryService.deleteGoodsCategory(categoryId, shop.getShopId());
            if (categoryMessage.getState() == State.SUCCESS.getState()){
                map.put("success", true);
            }else {
                map.put("success", false);
                map.put("message", categoryMessage.getState() == State.FAILURE.getState() ?
                                           "该商品类型下还有其他商品, 无法删除!": "删除失败!");
            }
            return map;
        }
        catch (GoodsCategoryException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
    }
}
