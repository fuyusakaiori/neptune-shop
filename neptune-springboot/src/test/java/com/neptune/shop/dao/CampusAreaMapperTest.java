package com.neptune.shop.dao;


import com.neptune.shop.core.dao.CampusAreaMapper;
import com.neptune.shop.core.entity.CampusArea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * <h3>测试校园区域接口中的方法</h3>
 */
@SpringBootTest
public class CampusAreaMapperTest {

    @Resource
    private CampusAreaMapper campusAreaMapper;

    @Test
    public void findAllCampusAreaMapperTest(){
        List<CampusArea> areas = campusAreaMapper.findAllCampusArea();
        areas.forEach(System.out::println);
    }
}
