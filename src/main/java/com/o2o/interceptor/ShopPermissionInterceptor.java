package com.o2o.interceptor;

import com.o2o.entity.ShopInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShopPermissionInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ShopInfo shop = (ShopInfo) request.getSession().getAttribute("shop");
        List<ShopInfo> shops = (List<ShopInfo>) request.getSession().getAttribute("shops");
        if (shop != null && shops != null){
            for (ShopInfo current : shops) {
                if (current.getShopId() == shop.getShopId())
                    return true;
            }
        }
        return false;
    }
}
