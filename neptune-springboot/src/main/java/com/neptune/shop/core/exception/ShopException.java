package com.neptune.shop.core.exception;

/**
 * <h2>操作店铺信息的过程中产生的异常</h2>
 * <p>自定义异常必须继承运行时异常, 否则事务失败时是无法回滚的</p>
 */
public class ShopException extends RuntimeException
{

    public ShopException(String message){
        super(message);
    }
}
