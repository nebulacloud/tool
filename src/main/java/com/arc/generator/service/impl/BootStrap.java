package com.arc.generator.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BootStrap {

    private static final Logger logger = LoggerFactory.getLogger(BootStrap.class);

//    @Value("${meta.schemaName}")
//    private String tableSchema;
//    @Value("${meta.tableName}")
//    private String tableName;

    @Autowired
    private FreemarkerGenerator generator;

    public void start() {
        //1、通过数据库获取表的信息
        //2、构建一些实体代码
        generator.produce();
    }

}
