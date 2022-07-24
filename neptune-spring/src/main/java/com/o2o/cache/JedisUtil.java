package com.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Set;

public class JedisUtil
{
    public Keys KEYS;
    // Redis 采用键值对存储: 这里选用最泛用的字符串作为值
    public Strings STRINGS;
    /**
     * <h3>1. 每个 JedisPool 中有很多个 Jedis 对象</h3>
     * <h3>2. 每个 Jedis 对象中有很多 Key-Value </h3>
     */
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisWritePool jedisWritePool){
        jedisPool = jedisWritePool.getJedisPool();
    }

    /**
     * <h3>获取缓存池中的对象</h3>
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * <h3>用于处理 Jedis 对象中的 Key 值</h3>
     */
    public class Keys{
        /**
         * <h3>删除所有 Key</h3>
         * @return 所有 Key 组成的字符串
         */
        public String flush(){
            Jedis jedis = getJedis();
            String result = jedis.flushAll();
            // 键值对对象需要关闭
            jedis.close();
            return result;
        }

        /**
         * <h3>删除指定的 Key 值</h3>
         * @return 删除的 Key 的数量
         */
        public Long delete(String ...key){
            Jedis jedis = getJedis();
            Long count = jedis.del(key);
            jedis.close();
            return count;
        }

        /**
         * <h3>判断 Key 是否存在</h3>
         */
        public boolean isExist(String key){
            Jedis jedis = getJedis();
            Boolean exists = jedis.exists(key);
            jedis.close();
            return exists;
        }

        public Set<String> keys(String pattern){
            Jedis jedis = getJedis();
            Set<String> keys = jedis.keys(pattern);
            jedis.close();
            return keys;
        }
    }

    public class Strings{
        public String get(String key){
            Jedis jedis = getJedis();
            String value = jedis.get(key);
            jedis.close();
            return value;
        }

        public String set(String key, String value){
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        public String set(byte[] key, byte[] value){
            Jedis jedis = getJedis();
            String result = jedis.set(key, value);
            jedis.close();
            return result;
        }
    }
}
