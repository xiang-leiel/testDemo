package com.example.test.config;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @Description 
 * @author leiel
 * @Date 2020/8/5 2:05 PM
 */
@Configuration
@MapperScan("com.example.test.dao")
@ComponentScan("com.example.test.service")
public class AppConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClass("com.mysql.jdbc.Driver");
        driverManagerDataSource.setJdbcUrl("jdbc:mysql://47.97.218.94:3306/test?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
        driverManagerDataSource.setUser("root");
        driverManagerDataSource.setPassword("leiel");
        return driverManagerDataSource;
    }

}
