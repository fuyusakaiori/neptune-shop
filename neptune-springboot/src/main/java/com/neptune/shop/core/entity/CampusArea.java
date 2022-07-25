package com.neptune.shop.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <h2>校园区域实体类</h2>
 */
@Data
@NoArgsConstructor
@ToString
public class CampusArea {
    // 标识符
    private Integer campusAreaId;
    // 名称
    private String campusAreaName;
    // 权重值
    private Integer priority;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date updateTime;
}
