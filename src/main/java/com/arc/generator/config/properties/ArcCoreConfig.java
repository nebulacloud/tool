package com.arc.generator.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 注册配置，自动读取配置文件中配置
 *
 * @author 叶超
 * @since 2019/5/8 22:23
 */
@Configuration
@EnableConfigurationProperties(ArcGeneratorPropertiesProvider.class)
public class ArcCoreConfig {
}
