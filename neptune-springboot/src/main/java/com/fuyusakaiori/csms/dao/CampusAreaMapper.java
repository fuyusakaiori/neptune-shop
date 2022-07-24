package com.fuyusakaiori.csms.dao;


import com.fuyusakaiori.csms.entity.CampusArea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
