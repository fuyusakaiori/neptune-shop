package com.fuyusakaiori.csms.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <h2>动态数据源</h2>
 * <h3>1. 继承 AbstractRoutingDataSource 抽象类: 实现特定的路由方法, 从而根据读写操作选择不同的数据库</h3>
 * <h3>2. 内部会调用 determineCurrentLookupKey 获取到当前数据源对应的 Key 值</h3>
 * <h3>3. 然后调用 determineTargetDataSource 根据数据源 Key 值获取到相应的数据源对象, 然后使用该数据源对象</h3>
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
    /**
     * <h3>1. 返回对应数据源对应的 LookUpKey</h3>
     * <h3>2. AbstractRoutingDataSource 就有对应方法使用 LookUpKey 并寻找到对应的数据源</h3>
     * <h3>3. 然后设置拦截器, 拦截器会判断当前执行的 SQL 究竟是读还是写, 并设置对应的数据源类型</h3>
     * @return LookUpKey
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDatabaseType();
    }
}
