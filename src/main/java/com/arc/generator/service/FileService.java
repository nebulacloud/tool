package com.arc.generator.service;

import com.arc.generator.config.properties.ArcGeneratorProperties;

import java.util.Map;

/**
 * @author 叶超
 * @since 2019/10/3 8:01
 */
public interface FileService {

    Map<String, Object> produce();

    Map<String, Object> produce(ArcGeneratorProperties arcGeneratorProperties);
}
