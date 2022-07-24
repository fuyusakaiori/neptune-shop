package com.o2o.jedis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.o2o.BaseTest;
import com.o2o.cache.JedisUtil;
import com.o2o.cache.JedisWritePool;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
public class JedisTest extends BaseTest {

    @Resource
    private JedisUtil jedisUtil;

    @Autowired
    @Qualifier("jedisWritePool")
    private JedisWritePool jedisPool;

    @Test
    @DisplayName("测试 Jedis 连接")
    public void jedisTest(){
        Assertions.assertNotNull(jedisPool);
        Assertions.assertNotNull(jedisUtil);
    }

    @Test
    @DisplayName("测试 Lettuce 布隆过滤器")
    public void lettuceBloomFilterTest(){
        // 1. 创建 Redis 连接对象
        RedisURI redisURI = new RedisURI("47.101.45.234", 6380, Duration.of(60, ChronoUnit.SECONDS));
        Assertions.assertNotNull(redisURI);
        // 2. 创建 Redis 客户端对象
        RedisClient redisClient = RedisClient.create(redisURI);
        // 3. 创建同步连接
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 4. 创建异步或者同步命令
        RedisCommands<String, String> commands = connection.sync();
        // 5. 使用命令: 测试布隆过滤器 需要指令扩展 建议直接 Redisson 一步到位
        for (int i = 0; i < 100; i++) {
            String result = commands.set("key" + i, "value" + i);
            Assertions.assertEquals("OK", result);
            String value = commands.get("key" + i);
            Assertions.assertEquals("value" + i, value);
        }
    }

    @Test
    public void redissonBloomFilterTest(){
        // 1. 创建配置对象
        Config config = new Config();
        config.useSingleServer().setAddress("redis://47.101.45.234:6380");
        // 2. 创建客户端对象
        RedissonClient redissonClient = Redisson.create(config);
        // 3. 创建布隆过滤器
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("book");
        bloomFilter.tryInit(1000, 0.01);
        bloomFilter.add("Java");
        bloomFilter.add("Python");
        bloomFilter.add("C#");
        // 4. 测试
        Assertions.assertTrue(bloomFilter.contains("C#"));
        // 注: redssion 对字符串的支持非常差, 所以会出现你放在 redis 中的布隆过滤器用不了
    }

    @Test
    public void guavaBloomFilterTest(){
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10000, 0.01);
        for (int index = 0; index < 10000; index++) {
            bloomFilter.put("value" + index);
        }
        System.out.println(bloomFilter.mightContain("value22"));
        System.out.println(bloomFilter.mightContain("value114"));

    }

}
