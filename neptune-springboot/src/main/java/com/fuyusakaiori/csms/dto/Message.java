package com.fuyusakaiori.csms.dto;

import com.fuyusakaiori.csms.util.enums.State;

import java.util.List;


/**
 * <h2>对店铺实体类和操作店铺的返回信息进行封装</h2>
 * <p>1. 包含店铺实体类</p>
 * <p>2. 新增、删除、修改、查询操作成功或者失败后的提示信息</p>
 * <p>3. 这种实体类只是位于业务层和实体类层的中间商, 起封装的作用, 不需要存储于数据库中</p>
 * <p>4. 实体类中的信息都是被后台创建的中间信息, 是需要被外部获取的, 但是不可以被修改</p>
 *
 * <h3>注: 大多数的实体类都会具有对应的信息类, 锁采用泛型封装</h3>
 */
public class Message<T>
{
    // 如果对店铺的行为成功, 就会返回相应的店铺实体类
    private T element;
    // 如果是对店铺集合的行为, 那么就需要返回相应的店铺集合
    private List<T> list;

    // 对店铺的行为产生的各种状态标志符
    private int state;
    // 对店铺的行为产生的提示信息
    private String info;
    // 操作的店铺的数量
    private int count;

    public Message() { }

    /**
     * 操作店铺的行为失败, 产生对应的失败信息, 不包含店铺实体类
     * @param shopState 店铺信息枚举类
     */
    public Message(State shopState){
        this.state = shopState.getState();
        this.info = shopState.getInfo();
    }

    /**
     * 操作单个店铺的行为成功, 产生对应的成功信息, 包含店铺实体类
     * @param shopState 店铺信息枚举类
     * @param shop 店铺信息
     */
    public Message(State shopState, T shop){
        this.state = shopState.getState();
        this.info = shopState.getInfo();
        this.element = shop;
        this.count = 1;
    }

    public Message(State shopState, List<T> shops, int count){
        this.state = shopState.getState();
        this.info = shopState.getInfo();
        this.list = shops;
        this.count = count;
    }

    public T getElement()
    {
        return element;
    }

    public List<T> getList()
    {
        return list;
    }

    public int getState() {
        return state;
    }

    public String getInfo()
    {
        return info;
    }

    public int getCount()
    {
        return count;
    }


}
