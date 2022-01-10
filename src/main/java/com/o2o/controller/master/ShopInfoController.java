package com.o2o.controller.master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h2>负责跳转到相应店铺管理页面</h2>
 */
@Controller
@RequestMapping(value = "/shop-admin", method = RequestMethod.GET)
public class ShopInfoController
{
    /**
     * <p>跳转到管理店铺信息的首页</p>
     * <p>从管理首页选择是否需要新增或者修改店铺的信息</p>
     * @return 管理店铺信息的页面
     */
    @RequestMapping(value = "/index")
    public String shopManagementPage(){
        return "shop/index";
    }
}
