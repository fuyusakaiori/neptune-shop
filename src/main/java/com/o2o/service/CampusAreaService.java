package com.o2o.service;

import com.o2o.entity.CampusArea;
import com.o2o.exception.CampusAreaException;

import java.util.List;

public interface CampusAreaService
{
    String AREALISTKEY = "AREALISTKEY";

    List<CampusArea> findAllCampusArea() throws CampusAreaException;
}
