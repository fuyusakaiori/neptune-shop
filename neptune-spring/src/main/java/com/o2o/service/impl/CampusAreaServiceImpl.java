package com.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.cache.JedisUtil;
import com.o2o.dao.CampusAreaMapper;
import com.o2o.entity.CampusArea;
import com.o2o.exception.CampusAreaException;
import com.o2o.service.CampusAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>校园区域</h2>
 * <h3>注: 校园区域信息改变频率较低, 所以可以使用缓存</h3>
 * <h3>问题: 解决 Redis 无法连接的问题, 就是远程连接不上, 估计防火墙开了</h3>
 */
@Service
@Slf4j
public class CampusAreaServiceImpl implements CampusAreaService
{
    @Resource
    private CampusAreaMapper campusAreaMapper;

    @Resource
    private JedisUtil.Keys keys;
    @Resource
    private JedisUtil.Strings strings;



    @Override
    @Transactional
    public List<CampusArea> findAllCampusArea() throws CampusAreaException
    {
        List<CampusArea> areas;
        ObjectMapper mapper = new ObjectMapper();
        // 如果缓存中没有想要的数据, 就从数据库中取出来, 否则就直接返回缓存中的内容
        if (!keys.isExist(CampusAreaService.AREALISTKEY)){
            String areasJson;
            try {
                areas = campusAreaMapper.findAllCampusArea();
                areasJson = mapper.writeValueAsString(areas);
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage());
                throw new CampusAreaException(e.getMessage());
            }
            strings.set(AREALISTKEY, areasJson);
        }else{
            log.debug("Redis 存在相应的缓存");
            // 字符串中存储的是所有的区域
            String areasJson = strings.get(CampusAreaService.AREALISTKEY);
            // 设置好相应的反射信息才能够解析
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, CampusArea.class);
            try {
                areas = mapper.readValue(areasJson, javaType);
            }
            catch (IOException e) {
                log.error(e.getMessage());
                throw new CampusAreaException(e.getMessage());
            }
        }

        return areas;
    }
}
