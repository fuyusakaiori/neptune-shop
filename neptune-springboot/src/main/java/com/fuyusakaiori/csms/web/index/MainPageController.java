package com.fuyusakaiori.csms.web.index;

import com.fuyusakaiori.csms.entity.HeadLine;
import com.fuyusakaiori.csms.entity.ShopCategory;
import com.fuyusakaiori.csms.service.HeadLineService;
import com.fuyusakaiori.csms.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h2>首页</h2>
 */
@Controller
@RequestMapping(value = "/index")
public class MainPageController
{
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;

    /**
     * <h3>初始化首页的展示信息</h3>
     */
    @RequestMapping(value = "/init")
    @ResponseBody
    public Map<String, Object> initMainPage(){
        Map<String, Object> map = new HashMap<>();
        List<ShopCategory> categories;
        // 1. 取出所有父类型店铺
        try {
            categories = shopCategoryService.findAllShopCategory();
            map.put("categories", categories);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        // 2. 取出所有可用的轮播图
        List<HeadLine> lines;
        try {
            HeadLine condition = new HeadLine();
            condition.setStatus(1);
            lines = headLineService.findHeadLine(condition);
            map.put("lines", lines);
        }catch (Exception e){
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        // 两个查询操作都执行成功之后才认为整个查询过程成功
        map.put("success", true);
        return map;
    }
}