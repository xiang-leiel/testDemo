package com.example.test.utils.mp;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/13 2:49 PM
 */

public class Generator {

    public static void doGenerator(String author, final String dir, String project_url, String tablePrefix, String table_name, final boolean isNeedController) {
        // 自定义需要填充的字段
        ArrayList<TableFill> tableFillList = new ArrayList<>();
        //tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        GlobalConfig globalConfig = new GlobalConfig().setOutputDir(project_url)// 输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setAuthor(author)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper").setXmlName("%sMapper").setServiceName("I%sService").setServiceImplName("%sServiceImpl");
        if (isNeedController) {
            globalConfig.setControllerName("%sController");
        }
        AutoGenerator mpg = new AutoGenerator()
                .setGlobalConfig(
                        // 全局配置
                        globalConfig)
                .setDataSource(
                        // 数据源配置
                        new DataSourceConfig()
                                .setDriverName("com.mysql.jdbc.DriverFactory")
                                .setUsername("root")
                                .setPassword("leiel")
                                .setUrl("jdbc:mysql://47.97.218.94:3306/test?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull"))

                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // .setCapitalMode(true)// 全局大写命名
                                // .setDbColumnUnderline(true)//全局下划线命名
                                // 此处可以修改为您的表前缀
                                .setTablePrefix(new String[]{tablePrefix})
                                // 表名生成策略
                                .setNaming(NamingStrategy.underline_to_camel)
                                // 需要生成的表
                                .setInclude(new String[]{table_name})
                        // .setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.xdong.ripple.CommonEntity")
                        // 自定义实体，公共字段
                        // .setSuperEntityColumns(new String[]{"test_id"})
                        //.setTableFillList(tableFillList)
                        // 自定义 mapper 父类
                        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
                        // 自定义 com.example.test.config.service 父类
                        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
                        // 自定义 com.example.test.config.service 实现类父类
                        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
                        // 自定义 controller 父类
                        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        // .setEntityLombokModel(true)
                        // Boolean类型字段是否移除is前缀处理
                        // .setEntityBooleanColumnRemoveIsPrefix(true)
                        // .setRestControllerStyle(true)
                        // .setControllerMappingHyphenStyle(true)
                        // .entityTableFieldAnnotationEnable(true)
                        //.setLogicDeleteFieldName("is_delete")
                )
                .setPackageInfo(
                        // 包配置
                        new PackageConfig()
                                //.setModuleName("User")
                                // 自定义包路径
                                .setParent("src.main")
                                // 这里是控制器包名，默认 web
                                .setEntity("java.com.example.test.entity." + dir)
                                .setMapper("java.com.example.test.mapper." + dir)
                                .setService("java.com.example.test.com.example.test.config.service." + dir)
                                .setServiceImpl("java.com.example.test.impl." + dir)
                                .setXml("resources.mybatis.mapper." + dir)
                                .setController(null)
                )
                .setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<>();
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        }
                        // .setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
                        // "/templates/mapper.xml" + ((1 == result) ? ".ftl" : ".vm")) {
                        // // 自定义输出文件目录
                        // @Override
                        // public String outputFile(TableInfo tableInfo) {
                        // return "/Users/stone/Downloads/mybatis/xml/" + tableInfo.getEntityName() + ".xml";
                        // }
                        // }))
                ).setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml("/templates/mapper.xml.vm")
                        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                        // .setController("...");
                        // .setEntity("...");
                        // .setMapper("...");
                        // .setXml("...");
                        // .setService("...");
                        // .setServiceImpl("...");
                );
        // 执行生成
        mpg.execute();
    }

}
