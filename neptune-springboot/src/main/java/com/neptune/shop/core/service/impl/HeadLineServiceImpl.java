package com.neptune.shop.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptune.shop.core.cache.redis.RedisClient;
import com.neptune.shop.core.dao.HeadLineMapper;
import com.neptune.shop.core.entity.HeadLine;
import com.neptune.shop.core.exception.HeadLineException;
import com.neptune.shop.core.service.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HeadLineServiceImpl implements HeadLineService
{
    @Autowired
    @Qualifier("headLineMapper")
    private HeadLineMapper headLineMapper;

    @Autowired
    @Qualifier("redisClient")
    private RedisClient redisClient;

    /**
     * TODO 需要单元测试
     * @param condition 查询条件
     * @return 轮播图集合
     */
    @Override
    @Transactional
    public List<HeadLine> findHeadLine(HeadLine condition) {
        String key = HeadLineService.HEADLINESKEY;
        List<HeadLine> lines;
        ObjectMapper mapper = new ObjectMapper();

        // 每次都是根据状态查询轮播图的, 所以状态信息也需要存储在 KEY 值中
        if (condition != null && condition.getStatus() != null){
            // 不同状态的轮播图存在不同的键值对中
            key += "-" + condition.getStatus();
        }
        if (redisClient.exists(key)){
            String linesJson;
            try {
                lines = headLineMapper.findHeadLine(condition);
                linesJson = mapper.writeValueAsString(lines);
            }
            catch (JsonProcessingException e) {
                throw new HeadLineException(e.getMessage());
            }
            redisClient.set(key, linesJson);
        }else{
            String linesJson = redisClient.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                lines = mapper.readValue(linesJson, javaType);
            }
            catch (IOException e) {
                throw new HeadLineException(e.getMessage());
            }
        }

        return lines;
    }
}
