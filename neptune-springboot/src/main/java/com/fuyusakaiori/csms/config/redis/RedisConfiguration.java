package com.fuyusakaiori.csms.config.redis;

import com.fuyusakaiori.csms.cache.JedisUtil;
import com.fuyusakaiori.csms.cache.JedisWritePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String hostname;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;

    @Bean(name = "config")
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(this.maxActive);
        poolConfig.setMaxIdle(this.maxIdle);
        poolConfig.setMaxWait(Duration.ofSeconds(maxWait));
        return poolConfig;
    }

    @Bean(name = "pool")
    public JedisWritePool getJedisWritePool(){
        return new JedisWritePool(getJedisPoolConfig(), this.hostname, this.port);
    }

    @Bean(name = "util")
    public JedisUtil getJedisUtil(){
        return new JedisUtil(getJedisWritePool());
    }

    @Bean("keys")
    public JedisUtil.Keys getKeys(){
        return getJedisUtil().new Keys();
    }

    @Bean(name = "strings")
    public JedisUtil.Strings getStrings(){
        return getJedisUtil().new Strings();
    }
}
