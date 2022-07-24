package com.fuyusakaiori.csms.service.impl;


import com.fuyusakaiori.csms.cache.JedisUtil;
import com.fuyusakaiori.csms.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService
{
    @Resource
    private JedisUtil.Keys keys;

    @Override
    public void removeKeys(String pattern) {
        Set<String> keySet = this.keys.keys(pattern);
        keySet.forEach(key -> keys.delete(key));
    }
}
