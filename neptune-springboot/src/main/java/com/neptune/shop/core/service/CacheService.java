package com.neptune.shop.core.service;

public interface CacheService
{
    /**
     * <h3>根据传入的匹配模式删除相应的 KEY</h3>
     * @param pattern 模式
     */
    void removeKeys(String pattern);
}
