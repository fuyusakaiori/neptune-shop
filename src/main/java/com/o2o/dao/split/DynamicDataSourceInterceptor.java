package com.o2o.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * <h2>动态数据源拦截器: 设置动态数据源</h2>
 * <h3>1. 根据执行的 SQL 语句, 判断对数据库的操作是读还是写, 然后设置使用动态数据源工具类去设置</h3>
 * <h3>2. 执行读操作的 SQL, 访问从数据库, 执行写操作的 SQL, 访问主数据库</h3>
 * <p>注: 这个注解是干啥的?</p>
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor
{
    private final static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020";
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        String lookUpKey = DynamicDataSourceHolder.DATABASE_MASTER;
        // 1. 执行器是否开启了事务, 如果有那么直接访问主数据库 => 为啥?
        boolean flag = TransactionSynchronizationManager.isActualTransactionActive();
        // 2. 获取第一个参数, 第一个参数表示的就是这个语句的类型
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if (!flag){
            // 3. 如果执行器是执行查询操作, 那么可能访问从数据库, 如果是其余更新操作, 那么就是访问主数据库
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                // 4. 如果使用自增主键查询, 就访问主数据库 => 为啥?
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    lookUpKey = DynamicDataSourceHolder.DATABASE_SLAVE;
                }else{
                    // 5.1 如果查询语句后面还跟了更新语句, 这是可能的, 但是这个项目没有, 也是访问主数据库
                    // 5.2 如果查询语句之后还跟的是嵌套子查询, 那么依然是访问主数据库
                    // 5.3 使用正则表达式判断
                    // 5.4 这里的 SQL 语句包含占位符等一些信息, 会影响到正则表达式的判断, 所以需要提前处理器下
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(args[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\r\\n]", " ");
                    lookUpKey = sql.matches(REGEX) ? DynamicDataSourceHolder.DATABASE_MASTER: DynamicDataSourceHolder.DATABASE_SLAVE;
                }
            }
        }else {
            lookUpKey = DynamicDataSourceHolder.DATABASE_MASTER;
        }
        logger.debug("设置方法[{}], 访问的数据源[{}], SQL 类型[{}]", ms.getId(), lookUpKey, ms.getSqlCommandType().name());
        // 6. 设置访问的数据库类型 => Mybatis 配置文件中配置拦截器 => Spring 中配置对应的数据源对象
        DynamicDataSourceHolder.setDatabaseType(lookUpKey);
        return invocation.proceed();
        
    }

    /**
     * <h3>1. 只要执行的操作是基本的增删改查的 SQL 语句, 那么就是需要访问数据库的</h3>
     * <h3>2. 那么我们就应该对其进行拦截后, 指定访问主数据库还是从数据库</h3>
     * @param target 拦截到的执行器
     */
    @Override
    public Object plugin(Object target) {
        // 1. Executor 是 Mybatis 中的执行器, 执行器就是用于执行增删改查, 以及事务等操作的类
        if (target instanceof Executor){
            // 2. 如果拦截到的就是执行器的实现类, 那么就对其进行包装之后返回
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
