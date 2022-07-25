package com.neptune.shop.core.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/index")
public class IndexController
{
    @RequestMapping(value = "/")
    public String indexPage(){
        return "index/index";
    }

    @RequestMapping(value = "/shop-list")
    public String shopListPage(){
        return "index/shop-list";
    }

    @RequestMapping("/shop-detail")
    public String shopDetailPage(){
        return "index/shop-detail";
    }
}
