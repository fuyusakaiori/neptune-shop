package com.neptune.shop.core.service;


import com.neptune.shop.core.entity.CampusArea;
import com.neptune.shop.core.exception.CampusAreaException;

import java.util.List;

public interface CampusAreaService
{
    String AREALISTKEY = "AREALISTKEY";

    List<CampusArea> findAllCampusArea() throws CampusAreaException;
}
