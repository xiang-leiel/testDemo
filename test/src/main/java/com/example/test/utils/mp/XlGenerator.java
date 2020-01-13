package com.example.test.utils.mp;
/**
 * @Description 
 * @author leiel
 * @Date 2020/1/13 2:49 PM
 */

public class XlGenerator {

    public static void main(String[] args) {

        //作者
        String author = "leiel";
        //文件目录
        final String dir = "user";
        //本地项目路径 /Users/leiel/IdeaProjects/AttractInvestmentEnterpriseSecond/DeQing-InvestmentApp
        String project_url = "/Users/leiel/IdeaProjects/test";
        //前缀
        String tablePrefix = "";
        //表名
        String[] table_names = new String[]{"user_info"};
        boolean isNeedController = false;

        for(String table_name :table_names){
            Generator.doGenerator(author, dir, project_url, tablePrefix, table_name, isNeedController);
        }
    }
}
