package com.neptune.shop.core.cache.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.neptune.shop.core.cache.AbstractCacheClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RedisClient extends AbstractCacheClient {

    private static final String LOCK_VALUE_PREFIX = "lock:";

    /**
     * <h3>spring redis template</h3>
     */
    @Resource
    private StringRedisTemplate template;

    //============================================ string ============================================

    /**
     * <h3>获取单个键值对</h3>
     */
    public String get(String key){
        if (StrUtil.isEmpty(key))
            return StrUtil.EMPTY;
        return template.opsForValue().get(key);
    }

    /**
     * <h3>获取多个键值对</h3>
     */
    public List<String> mget(Collection<String> keys){
        return template.opsForValue().multiGet(keys);
    }

    /**
     * <h3>获取值的长度</h3>
     */
    public long strlen(String key){
        Long length = template.opsForValue().size(key);
        return Objects.isNull(length) ? 0: length;
    }

    /**
     * <h3>设置单个键值对</h3>
     */
    public void set(String key, String value){
        template.opsForValue().set(key, value);
    }

    /**
     * <h3>设置键值对和过期时间</h3>
     */
    public void set(String key, String value, long time, TimeUnit unit){
        template.opsForValue().set(key, value, time, unit);
    }

    /**
     * <h3>批量设置键值对</h3>
     */
    public void mset(Map<String, String> keyAndValue){
        template.opsForValue().multiSet(keyAndValue);
    }

    /**
     * <h3>获取旧值, 设置新值</h3>
     */
    public String getset(String key, String value){
        return template.opsForValue().getAndSet(key, value);
    }

    public boolean del(String key){
        if (StrUtil.isEmpty(key))
            return false;
        return BooleanUtil.isTrue(template.delete(key));
    }

    /**
     * <h3>自增: 值必须是整数</h3>
     */
    public long incr(String key){
        return incrby(key, 1);
    }

    /**
     * <h3>自增: 可以指定增加的值</h3>
     */
    public long incrby(String key, long delta){
        Long value = template.opsForValue().increment(key, delta);
        return Objects.isNull(value) ? 0: value;
    }

    public long decr(String key){
        return decrby(key, 1);
    }

    public long decrby(String key, long delta){
        Long value = template.opsForValue().decrement(key, delta);
        return Objects.isNull(value) ? 0: value;
    }

    public boolean setnx(String key){
        return setnx(key, LOCK_VALUE_PREFIX, 10, TimeUnit.SECONDS);
    }

    public boolean setnx(String key, long time, TimeUnit unit){
        return setnx(key, LOCK_VALUE_PREFIX, time, unit);
    }

    public boolean setnx(String key, String prefix){
        return setnx(key, prefix, 10, TimeUnit.SECONDS);
    }

    public boolean setnx(String key, String prefix, long time, TimeUnit unit){
        return BooleanUtil.isTrue(template.opsForValue().setIfAbsent(
                key, prefix, time, unit));
    }

    public boolean expire(String key, long time, TimeUnit unit){
        return BooleanUtil.isTrue(template.expire(key, time, unit));
    }

    public boolean exists(String key){
        return BooleanUtil.isTrue(template.hasKey(key));
    }

    //============================================ hash ============================================

    public Object hget(String key, String field){
        if (StrUtil.isEmpty(key) || StrUtil.isEmpty(field))
            return null;
        return template.opsForHash().get(key, field);
    }

    public List<Object> hmget(String key, Collection<Object> field){
        if (StrUtil.isEmpty(key) || CollectionUtil.isEmpty(field))
            return Collections.emptyList();
        return template.opsForHash().multiGet(key, field);
    }

    public void hset(String key, String field, String value){
        if (StrUtil.isEmpty(key) || StrUtil.isEmpty(field) || StrUtil.isEmpty(value))
            return;
        template.opsForHash().put(key, field, value);
    }

    public void hmset(String key, Map<?, ?> fieldAndValue){
        if (StrUtil.isEmpty(key) || MapUtil.isEmpty(fieldAndValue))
            return;
        template.opsForHash().putAll(key, fieldAndValue);
    }

    public Collection<Object> hkeys(String key){
        if (StrUtil.isEmpty(key))
            return Collections.emptyList();
        return template.opsForHash().keys(key);
    }

    public Collection<Object> hvals(String key){
        if (StrUtil.isEmpty(key))
            return Collections.emptyList();
        return template.opsForHash().values(key);
    }

    public Map<Object, Object> hgetAll(String key){
        return template.opsForHash().entries(key);
    }

    public long hdel(String key, Object... fields){
        if (StrUtil.isEmpty(key) || fields.length <= 0)
            return 0;
        return template.opsForHash().delete(key, fields);
    }

    //============================================ list ============================================



    //============================================ set ============================================

}
