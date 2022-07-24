package com.o2o.interceptor;

import com.o2o.entity.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ShopLoginInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        // 认证成功
        if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getStatus() == 1){
            return true;
        }
        // 认证失败就重定向
        // TODO 为什么不直接使用重定向
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open('" + request.getContextPath() + "/local/login?type=2', '_self'");
        out.println("</script>");
        out.println("</html>");

        return false;
    }
}
