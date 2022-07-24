package com.fuyusakaiori.csms.service;


import com.fuyusakaiori.csms.entity.CampusArea;
import com.fuyusakaiori.csms.exception.CampusAreaException;

import java.util.List;

public interface CampusAreaService
{
    String AREALISTKEY = "AREALISTKEY";

    List<CampusArea> findAllCampusArea() throws CampusAreaException;
}
