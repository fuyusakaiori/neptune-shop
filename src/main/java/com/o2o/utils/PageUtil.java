package com.o2o.utils;

/**
 * <h2>负责将页号转换为行号</h2>
 */
public class PageUtil
{
    /**
     * 页号转换为行号的工具
     * @param pageSize 页大小
     * @param pageIndex 页号
     * @return 行号
     */
    public static int pageIndexToRowIndex(int pageSize, int pageIndex){
        return pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
    }
}
