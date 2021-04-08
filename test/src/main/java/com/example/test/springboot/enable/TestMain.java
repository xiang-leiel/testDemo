package com.example.test.springboot.enable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description 
 * @author leiel
 * @Date 2020/7/3 8:46 PM
 */
public class TestMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ax = new  AnnotationConfigApplicationContext(JavaConfig.class);

        TestData test = ax.getBean(TestData.class);

        test.toString();

        String[] beanDefinitionNames = ax.getBeanDefinitionNames();

        for (int i = 0; i < beanDefinitionNames.length; i++) {
            System.out.println(beanDefinitionNames[i].getClass());
        }
    }
}
