package com.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>动态数据源设置工具类</h2>
 * <h3>提供设置、获取、移除当前的动态数据源对应的 Key 值</h3>
 */
public class DynamicDataSourceHolder
{
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    // ThreadLocal 确保在多线程访问数据库的时候是线程安全的, 不会出现并发异常
    private static final ThreadLocal<String> context = new ThreadLocal<>();
    // 主从数据源对应的 LookUplKey
    public static final String DATABASE_MASTER = "master";
    public static final String DATABASE_SLAVE = "slave";

    /**
     * <h3>获取当前线程访问的数据源类型</h3>
     */
    public static String getDatabaseType(){
        return context.get() != null ? context.get(): DATABASE_MASTER;
    }

    /**
     * <h3>设置线程需要访问的数据源</h3>
     */
    public static void setDatabaseType(String type){
        logger.debug("访问的数据源类型: {}", type);
        context.set(type);
    }

    /**
     * <h3>移除当前线程访问的数据源类型</h3>
     */
    public static void removeDatabaseType(){
        context.remove();
    }

}
