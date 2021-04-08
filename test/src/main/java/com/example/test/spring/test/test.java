package com.example.test.spring.test;

import com.example.test.spring.config.Appconfig;
import com.example.test.spring.entity.ExampleOne;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;


/**
 * @Description 
 * @author leiel
 * @Date 2020/6/20 10:43 AM
 */

public class test {

    @Test
    public void test() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Appconfig.class);

        System.out.println(ac.getBean(ExampleOne.class));

        ObjectFactory<ExampleOne> oneObjectFactory = new ObjectFactory<ExampleOne>() {
            @Override
            public ExampleOne getObject() throws BeansException {
                return new ExampleOne();
            }
        };

        for(int i = 0; i < 2; i++) {
            System.out.println(oneObjectFactory.getObject());
        }


    }

}
