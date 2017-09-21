package com.status.test;

import com.status.model.QueryObj;
import com.status.service.IJournalService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by SevenFin on 2015/9/11.
 */
public class JournalTest {

    private IJournalService journalService;

    @Before
    public void before(){
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
                ,"classpath:conf/spring-mybatis.xml"});
        journalService = (IJournalService) context.getBean("journalService");
    }

    @Test
    public void queryByYear() throws Exception{

        QueryObj obj = new QueryObj();
        obj.setVendor("CQVIP");
        obj.setType(14);
        obj.setCondition(" and I_Prior=3000");
        obj.setBeginYear("2000");
        obj.setEndYear("2015");
        System.out.println(journalService.queryByYear(obj,null));
    }
}
