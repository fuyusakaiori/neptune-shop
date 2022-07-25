package com.neptune.shop.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>微信账号</h2>
 */
@Data
@ToString
@NoArgsConstructor
public class WechatAccount
{
    private Integer wechatId;
    // 关联微信账号的标识符
    private Integer openId;
    private Date createTime;
    // 关联的用户信息
    private UserInfo userInfo;
}
