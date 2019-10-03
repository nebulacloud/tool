package com.arc.generator.service.impl;

import com.arc.generator.config.properties.ArcGeneratorProperties;
import com.arc.generator.mapper.MetaMapper;
import com.arc.generator.model.domain.meta.TableMeta;
import com.arc.generator.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BootStrap {

    private static final Logger logger = LoggerFactory.getLogger(BootStrap.class);

    @Autowired
    private ArcGeneratorProperties generatorProperties;

//    @Value("${meta.schemaName}")
//    private String tableSchema;
//    @Value("${meta.tableName}")
//    private String tableName;

    @Autowired
    private FreemarkerGenerator generator;

    @Autowired
    private MetaMapper mapper;

    public void start() throws Exception {
        //1、通过数据库获取表的信息
        //2、构建一些实体
        TableMeta meta = createTableMate();
        generator.produce(meta);
    }

    private TableMeta createTableMate() {
        TableMeta meta = mapper.get(generatorProperties.getDatabase().getSchemaName(),generatorProperties.getDatabase().getTableName());
        if (meta == null) {
            throw new IllegalArgumentException("\n指定的表不存在，请检查表的名称或数据库是否配置正确！\nPlease check schemaName and tableName are correct. ");
        }
        logger.info("表的元信息为{}", JacksonUtils.toJson(meta));
        return meta;
    }

}
