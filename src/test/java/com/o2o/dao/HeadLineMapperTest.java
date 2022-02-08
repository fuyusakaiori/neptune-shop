package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.HeadLine;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineMapperTest extends BaseTest
{
    @Autowired
    private HeadLineMapper headLineMapper;

    @Test
    public void findHeadLineMapperTest(){
        HeadLine condition = new HeadLine();
        condition.setStatus(1);
        condition.setHeadlineName("测试用例");
        List<HeadLine> lines = headLineMapper.findHeadLine(condition);
        lines.forEach(System.out::println);
        Assert.assertEquals(2, lines.size());
    }
}
