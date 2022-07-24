package com.fuyusakaiori.csms.dao;


import com.fuyusakaiori.csms.entity.HeadLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>头条轮播图</h2>
 */
@Mapper
public interface HeadLineMapper
{
    /**
     * <h3>根据条件查询头条轮播图: 轮播图的名字、轮播图的状态</h3>
     * @param condition 查询条件
     * @return 查询得到的结果集
     */
    List<HeadLine> findHeadLine(@Param("condition") HeadLine condition);

    /**
     * <h3>根据轮播图的编号查询</h3>
     * @param id 轮播图编号
     * @return 查询得到的轮播图对象
     */
    HeadLine findHeadLineById(@Param("id") int id);
}
