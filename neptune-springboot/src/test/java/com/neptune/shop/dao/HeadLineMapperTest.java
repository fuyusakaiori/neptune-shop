package com.neptune.shop.dao;


import com.neptune.shop.core.dao.HeadLineMapper;
import com.neptune.shop.core.entity.HeadLine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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
    }
}
