package com.fuyusakaiori.csms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuyusakaiori.csms.cache.JedisUtil;
import com.fuyusakaiori.csms.dao.HeadLineMapper;
import com.fuyusakaiori.csms.entity.HeadLine;
import com.fuyusakaiori.csms.exception.HeadLineException;
import com.fuyusakaiori.csms.service.HeadLineService;
import lombok.extern.slf4j.Slf4j;
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
    @Resource
    private HeadLineMapper headLineMapper;

    @Resource
    private JedisUtil.Keys keys;
    @Resource
    private JedisUtil.Strings strings;

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
        if (!keys.isExist(key)){
            String linesJson;
            try {
                lines = headLineMapper.findHeadLine(condition);
                linesJson = mapper.writeValueAsString(lines);
            }
            catch (JsonProcessingException e) {
                throw new HeadLineException(e.getMessage());
            }
            strings.set(key, linesJson);
        }else{
            String linesJson = strings.get(key);
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
