package com.o2o.dao;

import com.o2o.entity.CampusArea;

import java.util.List;

public interface CampusAreaMapper
{
    /**
     * <h3>查询所有校园区域</h3>
     * @return campusAreas
     */
    List<CampusArea> findAllCampusArea();
}
