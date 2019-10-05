package com.arc.generator.controller;

import com.arc.generator.config.properties.ArcGeneratorPropertiesProvider;
import com.arc.generator.config.properties.DatabaseProperties;
import com.arc.generator.config.properties.ProjectProperties;
import com.arc.generator.config.properties.TestValue;
import com.arc.generator.mapper.MetaMapper;
import com.arc.generator.model.domain.meta.TableMeta;
import com.arc.generator.service.impl.FreemarkerGeneratorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/10/3 15:29
 */
@Slf4j
@RestController
public class PropertyController {

    @Autowired
    private ArcGeneratorPropertiesProvider generatorProperties;

    @Autowired
    private TestValue testValue;

    @Autowired
    private MetaMapper metaMapper;

    @GetMapping(value = "/test")
    public ResponseEntity<Map<String, Object>> page() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "arc");
        map.put("age", 12);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(500);
        return bodyBuilder.body(map);
    }

    /**
     * @param tableSchema 数据库
     * @param tableName   表名称
     * @return TableMeta
     */
    @GetMapping(value = "/test/mate/{tableSchema}/{tableName}")
    public ResponseEntity meate(@PathVariable String tableSchema, @PathVariable String tableName) {
        TableMeta tableMeta = metaMapper.get(tableSchema, tableName);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(201);
        return bodyBuilder.body(tableMeta);
    }

    /**
     * 测试从配置文件中读取配置
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/test/value")
    public ResponseEntity getValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("generatorProperties", generatorProperties);
        map.put("testValue", testValue);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(500);
        return bodyBuilder.body(map);
    }

    @Autowired
    private FreemarkerGeneratorServiceImpl freemarkerGeneratorServiceImpl;

    /**
     * 测试生成逻辑
     *
     * @return map
     */
    @GetMapping(value = "/test/basic")
    public ResponseEntity free() {
        return ResponseEntity.ok(freemarkerGeneratorServiceImpl.produce());
    }

    @GetMapping(value = "/test/2")
    public ResponseEntity test2(
            @RequestParam(required = true) String schemaName,
            @RequestParam(required = true) String tableName,
            @RequestParam(required = true) String tableAlias,
            @RequestParam(required = true) String rootNamespace,
            @RequestParam(required = true) String mapperNamespace,
            @RequestParam(required = true) String modelNamespace,
            @RequestParam(required = true) String output

    ) {

        //todo 默认别名          tableAlias =tableAlias==null:;
        ArcGeneratorPropertiesProvider arcGeneratorPropertiesProvider = new ArcGeneratorPropertiesProvider();
        arcGeneratorPropertiesProvider.setProject(new ProjectProperties(rootNamespace, mapperNamespace, modelNamespace,output));
        arcGeneratorPropertiesProvider.setDatabase(new DatabaseProperties(schemaName,tableName,tableAlias));
        return ResponseEntity.ok(freemarkerGeneratorServiceImpl.produce(arcGeneratorPropertiesProvider));
    }




}
