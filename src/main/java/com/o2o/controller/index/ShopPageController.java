package com.o2o.controller.index;

import com.o2o.dto.Message;
import com.o2o.entity.CampusArea;
import com.o2o.entity.ShopCategory;
import com.o2o.entity.ShopInfo;
import com.o2o.service.CampusAreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopInfoService;
import com.o2o.utils.RequestUtil;
import com.o2o.utils.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h2>店铺查询页面</h2>
 */
@Controller
@RequestMapping(value = "/index")
public class ShopPageController
{
    @Autowired
    private CampusAreaService campusAreaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopInfoService shopInfoService;

    @RequestMapping(value = "/search-init", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSearchInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 获取店铺父类型编号
        int parentId = RequestUtil.getParameterByInt(request, "parentId");
        try {
            List<ShopCategory> categories;
            if (parentId >= 1){
                ShopCategory condition = new ShopCategory();
                condition.setParentCategoryId(parentId);
                categories = shopCategoryService.findAllShopCategory(condition);
            }else{
                categories = shopCategoryService.findAllShopCategory();
            }
            map.put("categories", categories);
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        // 获取店铺列表
        List<CampusArea> areas;
        try {
            areas = campusAreaService.findAllCampusArea();
            map.put("areas", areas);
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        map.put("success", true);

        return map;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchShopInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        // 获取页码
        int pageIndex = RequestUtil.getParameterByInt(request, "pageIndex");
        int pageSize = RequestUtil.getParameterByInt(request, "pageSize");
        // 获取查询条件
        if (pageIndex >= 1 && pageSize >= 1){
            int parentId = RequestUtil.getParameterByInt(request, "parentId");
            int id = RequestUtil.getParameterByInt(request, "id");
            int areaId = RequestUtil.getParameterByInt(request, "areaId");
            String name = RequestUtil.getParameterByString(request, "name");
            Message<ShopInfo> message = shopInfoService.findShopInfo(getCondition(parentId, id, areaId, name), pageIndex, pageSize);
            if (message.getState() == State.SUCCESS.getState()){
                map.put("success", true);
                map.put("shops", message.getList());
                map.put("count", message.getCount());
            }else {
                map.put("success", false);
                map.put("message", "查询店铺信息失败!");
            }
        }else{
            map.put("success", false);
            map.put("message", "传入的页号和页大小存在问题!");
        }

        return map;
    }

    private ShopInfo getCondition(int parentId, int id, int areaId, String name){
        ShopInfo condition = new ShopInfo();
        ShopCategory category = new ShopCategory();
        if (id >= 1){
            category.setShopCategoryId(id);
            condition.setCategory(category);
        }else if (parentId >= 1){
            category.setParentCategoryId(parentId);
            condition.setCategory(category);
        }
        if (areaId >= 1){
            CampusArea area = new CampusArea();
            area.setCampusAreaId(areaId);
            condition.setCampusArea(area);
        }
        if (name != null){
            condition.setShopName(name);
        }
        condition.setStatus(1);
        return condition;
    }
}
