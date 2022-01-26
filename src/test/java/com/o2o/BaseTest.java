package com.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <h3>所有测试类的基类, 只要继承基类测试类就可以进行测试, 避免各种手动加载配置文件</h3>
 * <p>Spring 与 JUnit 整合: JUnit 启动时加载 SpringIOC 容器</p>
 * <p>1.采用 SpringJUnit4ClassRunner.class 运行测试?</p>
 * <p>2.告诉 Spring 所有配置文件的路径</p>
 */
// TODO 详细了解这两个注解
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest
{

}
