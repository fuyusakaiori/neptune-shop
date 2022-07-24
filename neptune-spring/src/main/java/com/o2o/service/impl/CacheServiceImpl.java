package com.o2o.service.impl;

import com.o2o.cache.JedisUtil;
import com.o2o.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {
    @Resource
    private JedisUtil.Keys keys;

    @Override
    public void removeKeys(String pattern) {
        Set<String> keySet = this.keys.keys(pattern);
        keySet.forEach(key -> keys.delete(key));
    }
}
