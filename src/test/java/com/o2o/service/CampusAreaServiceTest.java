package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.entity.CampusArea;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CampusAreaServiceTest extends BaseTest
{
    @Autowired
    private CampusAreaService campusAreaService;

    @Test
    public void findAllCampusAreaService(){
        List<CampusArea> cla = campusAreaService.findAllCampusArea();
        Assert.assertEquals("西苑", cla.get(0).getCampusAreaName());
    }
}
