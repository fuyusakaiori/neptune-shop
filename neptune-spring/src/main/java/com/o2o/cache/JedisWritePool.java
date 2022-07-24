package com.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisWritePool
{
    private JedisPool jedisPool;

    public JedisWritePool(JedisPoolConfig config, String host, int port){
        jedisPool = new JedisPool(config, host, port);
    }

    public JedisPool getJedisPool()
    {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool)
    {
        this.jedisPool = jedisPool;
    }
}
