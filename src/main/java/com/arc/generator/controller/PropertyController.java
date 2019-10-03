package com.arc.generator.controller;

import com.arc.generator.config.properties.ArcGeneratorProperties;
import com.arc.generator.config.properties.TestValue;
import com.arc.generator.mapper.MetaMapper;
import com.arc.generator.model.domain.meta.TableMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/test")
    public ResponseEntity<Map<String, Object>> page() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "arc");
        map.put("age", 12);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(500);
        ResponseEntity<Map<String, Object>> body = bodyBuilder.body(map);
        return body;
    }

    @Autowired
    private MetaMapper metaMapper;

    /**
     * @param tableSchema 数据库
     * @param tableName   表名称
     * @return TableMeta
     */
    @GetMapping(value = "/test/mate/{tableSchema}/{tableName}")
    public ResponseEntity meate(@PathVariable String tableSchema, @PathVariable String tableName) {
        TableMeta tableMeta = metaMapper.get(tableSchema, tableName);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(201);
        ResponseEntity<Object> body = bodyBuilder.body(tableMeta);
        return body;
    }

    @Autowired
    private ArcGeneratorProperties generatorProperties;

    @Autowired
    private TestValue testValue;

    @GetMapping(value = "/test/value")
    public ResponseEntity getvalue() {
        Map<String, Object> map = new HashMap<>();
        map.put("generatorProperties", generatorProperties);
        map.put("testValue", testValue);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(500);
        ResponseEntity<Map<String, Object>> body = bodyBuilder.body(map);
        return body;
    }
}
