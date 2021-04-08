package com.example.test.spring.service;

import com.example.test.spring.entity.ExampleTwo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/20 10:59 AM
 */
@Component
public class MyBeanFactoryPostProcceser implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        //GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("exampleOne");

        //beanDefinition.setBeanClass(ExampleTwo.class);

    }
}
