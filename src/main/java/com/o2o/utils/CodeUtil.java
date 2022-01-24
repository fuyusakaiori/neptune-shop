package com.o2o.utils;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * <h2>验证码判断</h2>
 */
public class CodeUtil
{
    /**
     * <h3>确保验证码的填写是正确的</h3>
     */
    public static boolean checkVerifyCode(HttpServletRequest request){
        // 注: 因为上传的表单中包含文件流, 所以后台没有办法直接识别, 所以整个请求中都是没有携带任何参数的 =》 Spring 中配置
        String actual = RequestUtil.getParameterByString(request, "actual");
        String expected = String.valueOf(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
        if (actual != null) actual = actual.toLowerCase();
        return expected.toLowerCase().equals(actual);
    }
}
