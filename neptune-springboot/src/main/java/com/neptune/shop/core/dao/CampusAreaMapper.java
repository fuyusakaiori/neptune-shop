package com.neptune.shop.core.dao;


import com.neptune.shop.core.entity.CampusArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CampusAreaMapper
{
    /**
     * <h3>查询所有校园区域</h3>
     *
     * @return campusAreas
     */
    List<CampusArea> findAllCampusArea();
}
