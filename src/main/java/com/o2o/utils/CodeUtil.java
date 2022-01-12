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
        String actual = RequestUtil.getParameterByString(request, "actual");
        String expected = String.valueOf(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
        return !expected.equals(actual);
    }
}
