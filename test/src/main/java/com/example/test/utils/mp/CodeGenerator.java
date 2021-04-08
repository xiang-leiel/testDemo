package com.example.test.utils.mp;


import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/4 4:39 PM
 */

public class CodeGenerator {

    public static void main(String[] args) {

        //全局配置
        GlobalConfig config = new GlobalConfig();
        //是否开启AR模式
        config.setActiveRecord(true)
                .setAuthor("leiel")
                .setOutputDir("/Users/leiel/IdeaProjects/test/src/main/java")
                //文件覆盖
                .setFileOverride(true)
                //主键策略
                .setIdType(IdType.AUTO)
                //service首字符是否为I
                .setServiceName("%sSerivice")
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://47.97.218.94:3306/test?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull")
                .setDriverName("com.mysql.jdbc.DriverFactory")
                .setUsername("root")
                .setPassword("leiel");

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel) //下划线转驼峰命名
                .setTablePrefix("")
                .setInclude("user_info");  //生成的表

        //包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.example.test")
                .setMapper("dao")
                .setEntity("entity")
                .setService("seivice")
                .setXml("dao");

        //整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);
        autoGenerator.execute();

    }

}
