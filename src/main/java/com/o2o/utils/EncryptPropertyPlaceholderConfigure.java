package com.o2o.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <h2>负责在 Spring 中创建数据源的的时候的解密操作</h2>
 * <h3>为什么这个父类已经被废弃了</h3>
 */
public class EncryptPropertyPlaceholderConfigure extends PropertyPlaceholderConfigurer
{
    // 加密的内容: 用户名和密码
    private final static String[] encryptProperty = {"jdbc.username", "jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncrypted(propertyName)){
            // 如果已经加密就解密后返回
            return DESUtil.getDecryption(propertyValue);
        }else{
            // 如果没有加密, 就返回原本的值
            return propertyValue;
        }
    }

    /**
     * <h3>字段是否已经被加密</h3>
     */
    private boolean isEncrypted(String propertyName){
        for (String encrypt : encryptProperty) {
            if (encrypt.equals(propertyName)){
                return true;
            }
        }
        return false;
    }
}
