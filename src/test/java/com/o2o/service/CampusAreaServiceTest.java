package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.entity.CampusArea;
import com.o2o.exception.CampusAreaException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CampusAreaServiceTest extends BaseTest
{
    @Autowired
    private CampusAreaService campusAreaService;
    @Autowired
    private CacheService cacheService;

    @Test
    public void findAllCampusAreaService() throws CampusAreaException {
        List<CampusArea> areas = campusAreaService.findAllCampusArea();
        Assert.assertEquals(2, areas.size());
    }
}
