package com.fuyusakaiori.csms.dao;


import com.fuyusakaiori.csms.entity.HeadLine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineMapperTest
{
    @Resource
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
