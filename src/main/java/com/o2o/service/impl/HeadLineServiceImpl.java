package com.o2o.service.impl;

import com.o2o.dao.HeadLineMapper;
import com.o2o.entity.HeadLine;
import com.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService
{
    @Autowired
    private HeadLineMapper headLineMapper;

    /**
     * TODO <h3>之后增设缓存层的时候, 业务层就会有非常大的作用</h3>
     * @param condition 查询条件
     * @return 轮播图集合
     */
    @Override
    public List<HeadLine> findHeadLine(HeadLine condition) {
        return headLineMapper.findHeadLine(condition);
    }
}
