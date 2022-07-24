package com.fuyusakaiori.csms.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisWritePool {

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
