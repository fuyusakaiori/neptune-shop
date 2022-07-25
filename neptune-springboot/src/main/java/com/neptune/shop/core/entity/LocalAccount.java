package com.neptune.shop.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>本地账号</h2>
 */
@Data
@NoArgsConstructor
@ToString
public class LocalAccount {
    private Integer localId;
    private String username;
    private String password;
    private Date createTime;
    private Date updateTime;
    // 关联的用户
    private UserInfo userInfo;

}
