package com.arc.generator.service;

import com.arc.generator.config.properties.ArcGeneratorPropertiesProvider;

import java.util.Map;

/**
 * 创建代码的服务
 *
 * @author 叶超
 * @since 2019/10/3 8:01
 */
public interface FreemarkerGeneratorService {

    Map<String, Object> produce();

    Map<String, Object> produce(ArcGeneratorPropertiesProvider arcGeneratorPropertiesProvider);
}
