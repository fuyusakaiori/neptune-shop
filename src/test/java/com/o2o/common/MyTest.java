package com.o2o.common;

import org.junit.Test;

public class MyTest
{
    static Student s3 = new Student();

    static class Student{
        static Student s1 = new Student();
        Student s2 = new Student();
    }

    @Test
    public void test() throws ClassNotFoundException
    {
        Class.forName("com.o2o.common.MyTest");
    }

}
