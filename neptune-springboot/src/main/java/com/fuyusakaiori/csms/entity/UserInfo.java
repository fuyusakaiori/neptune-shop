package com.fuyusakaiori.csms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>用户信息</h2>
 */
@Data
@NoArgsConstructor
@ToString
public class UserInfo {
    private Integer userId;
    private String username;
    private String avatarURL;
    private String email;
    private String gender;
    // 状态: 0 禁用 1 表示可用
    private Integer status;
    // 角色: 1 顾客 2 店铺管理员 3 超级管理员
    private Integer role;
    private Date createTime;
    private Date updateTime;
}
