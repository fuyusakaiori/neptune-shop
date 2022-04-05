package com.o2o.common;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class User {

    @Resource(name = "", type = Course.class)
    public Course course;

    @Bean(name = "teacher")
    public Teacher getTeacher(){
        return new Teacher();
    }

}
