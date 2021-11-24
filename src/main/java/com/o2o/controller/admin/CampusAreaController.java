package com.o2o.controller.admin;

import com.o2o.entity.CampusArea;
import com.o2o.service.CampusAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CampusAreaController
{
    // 当前类创建对应的 Logger 对象
    Logger logger = LoggerFactory.getLogger(CampusAreaController.class);

    @Autowired
    private CampusAreaService campusAreaService;

    @RequestMapping(value = "/list-area", method = RequestMethod.GET)
    // 注: 交付给前端字符串的形式便于解析
    @ResponseBody
    private Map<String, Object> findAllCampusArea(){
        logger.info("==========start==========");
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<CampusArea> cal = campusAreaService.findAllCampusArea();
        try {
            map.put("rows", cal);
            map.put("total", cal.size());
        }catch (Exception e){
            map.put("success", false);
            map.put("error", e.toString());
        }
        long end = System.currentTimeMillis();
        logger.debug("cost-time:{}", end - start);
        logger.info("==========end==========");
        return map;
    }
}
