package com.example.test.reference;

import com.example.test.reference.autowired.I;
import com.example.test.reference.config.Appconfig;
import com.example.test.reference.resource.U;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/21 9:44 AM
 */
public class Test {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Appconfig.class);

        System.out.println(ac.getBean(I.class).getA1());

        System.out.println(ac.getBean(U.class).getB1());

    }

}
