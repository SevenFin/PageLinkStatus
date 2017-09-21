package com.status.test;

import com.status.model.User;
import com.status.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by SevenFin on 2015/9/11.
 */
public class UserTest {

    private IUserService userService;

    @Before
    public void before(){
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
                ,"classpath:conf/spring-mybatis.xml"});
        userService = (IUserService) context.getBean("userService");
    }

    @Test
    public void selectUser(){
        System.out.println(userService.selectUser("yxy","123"));
    }
}
