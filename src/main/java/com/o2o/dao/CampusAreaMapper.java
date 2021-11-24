package com.o2o.dao;

import com.o2o.entity.CampusArea;

import java.util.List;

public interface CampusAreaMapper
{
    /**
     * 查询校园所有区域
     * @return campusAreas
     */
    List<CampusArea> findAllCampusArea();
}
