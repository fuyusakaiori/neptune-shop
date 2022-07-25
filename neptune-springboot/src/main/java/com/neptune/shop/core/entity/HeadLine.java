package com.neptune.shop.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>头条信息</h2>
 */
@Data
@NoArgsConstructor
@ToString
public class HeadLine {
    private Integer headlineId;
    private String headlineName;
    private Integer priority;
    // 头条关联的页面地址
    private String link;
    // 头条图片
    private String imageURL;
    // 状态: 0 表示禁用 1 表示启用
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
