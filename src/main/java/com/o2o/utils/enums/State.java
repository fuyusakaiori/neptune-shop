package com.o2o.utils.enums;

/**
 * <h2>封装操作店铺的行为产生的各种状态信息</h2>
 */
public enum State
{
    // 枚举实例
    CHECK(1, "审核中"), OFFLINE(0, "非法店铺"), SUCCESS(200, "操作成功"),
    PASS(2, "通过"), ERROR(-1, "运行时异常"), FAILURE(500,"操作失败"),
    NULL_SHOPID(-2, "店铺编号为空"), NULL_SHOP(-3, "店铺信息为空"), NULL_CATEGORY_ID(-4, "类型编号为空");


    // 操作店铺的行为产生的各种状态标志符
    private int state;
    // 对店铺的行为产生的提示信息
    private String info;

    /**
     * 枚举类型默认构造函数就是私有的
     */
    State(int state, String info)
    {
        this.state = state;
        this.info = info;
    }

    public int getState() {
        return state;
    }

    public String getInfo()
    {
        return info;
    }
}
