package com.fuyusakaiori.csms.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <h2>提取请求中不同类型的数据</h2>
 */
@Slf4j
public class RequestUtil {

    /**
     * 获取请求中存放的字符串类型的数据
     * @param request 请求
     * @param key 数据对应的名称
     * @return 请求中的数据
     */
    public static String getParameterByString(HttpServletRequest request, String key){
        String value = request.getParameter(key);
        if (value == null || "".equals(value))
            return null;
        // TODO 等待解决的问题: 为什么需要考虑异常处理？这么几行代码怎么抛出异常？一定要把头尾的空白去掉吗？
        return value.trim();
    }

    /**
     * 获取请求中存放的整型类型的数据
     * @param request 请求
     * @param key 数据对应的名称
     * @return 数据
     */
    public static int getParameterByInt(HttpServletRequest request, String key){
        try {
            // 存储的数据可能不符合转换成整型数字的规范, 有可能抛出异常, 所以需要捕获
            return Integer.parseInt(request.getParameter(key));
        }
        catch (NumberFormatException e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    public static boolean getParameterByBoolean(HttpServletRequest request, String key){
        try {
            return Boolean.parseBoolean(request.getParameter(key));
        }
        catch (Exception e) {
            log.debug(e.getMessage());
            return false;
        }
    }

    // 其余类型的数据使用较少, 暂且不提供

}
