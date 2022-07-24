package com.fuyusakaiori.csms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>店铺信息</h2>
 */
@Data
@NoArgsConstructor
@ToString
public class ShopInfo {
    private Integer shopId;
    private String imageURL;
    private String shopName;
    private String description;
    private String phone;
    private String address;
    private Integer priority;
    // 店铺类型
    private ShopCategory category;
    // 店铺所属区域
    private CampusArea campusArea;
    // 店铺老板
    private UserInfo master;
    // 状态: 0 禁用 1 审核 2 通过
    private Integer status;
    // 系统管理员提供的建议
    private String advice;
    private Date createTime;
    private Date updateTime;
}
