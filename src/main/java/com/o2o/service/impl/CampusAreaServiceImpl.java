package com.o2o.service.impl;

import com.o2o.dao.CampusAreaMapper;
import com.o2o.entity.CampusArea;
import com.o2o.service.CampusAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
// 直接使用注解在 Spring 容器中创建接口实现类对象
public class CampusAreaServiceImpl implements CampusAreaService
{
    @Autowired
    // 不推荐使用 Autowired 自动注入
    @Qualifier("campusAreaMapper")
    private CampusAreaMapper campusAreaMapper;

    @Override
    public List<CampusArea> findAllCampusArea()
    {
        return campusAreaMapper.findAllCampusArea();
    }
}
