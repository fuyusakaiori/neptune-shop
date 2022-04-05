package com.o2o.utils.enums;

/**
 * <h2>封装操作店铺的行为产生的各种状态信息</h2>
 */
public enum State
{
    // 枚举实例
    CHECK(2, "审核中"), SUCCESS(1, "操作成功"), FAILURE(0,"操作失败"),
    INVALID_SHOP_ID(-1, "无效的店铺 ID"), INVALID_CATEGORY_ID(-2, "无效的类型 ID"), INVALID_GOODS_ID(-3, "无效的商品 ID"),
    NULL(-4, "空对象"), REPEAT(-7, "账号重复");


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
