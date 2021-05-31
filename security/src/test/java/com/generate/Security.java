package com.generate;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class Security {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E:\\javacode\\manage\\security\\src\\main\\java");
        gc.setAuthor("zs");
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql:///securitydemo");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.security");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //默认关闭不需要的生成内容
        //templateConfig.setXml(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);

        String  tableNames="sys_role,sys_role_menu,sys_menu,sys_user,sys_user_role";
        strategy.setInclude(tableNames.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("sys_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
