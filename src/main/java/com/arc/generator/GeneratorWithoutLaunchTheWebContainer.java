package com.arc.generator;

import com.arc.generator.config.SpringAndMybatisConfig;
import com.arc.generator.service.impl.BootStrap;
import com.arc.generator.service.impl.FreemarkerGeneratorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;


/**
 * 仅仅创建用表来创建 model、mapper、service、controller 。而不启动web容器
 * Do not launch the web container.
 */
public class GeneratorWithoutLaunchTheWebContainer {

    private static final Logger log = LoggerFactory.getLogger(BootStrap.class);

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringAndMybatisConfig.class);
        Map<String, Object> produce = context.getBean(FreemarkerGeneratorServiceImpl.class).produce();
        log.info("代码生成结果 {}", produce.get("result"));
        log.info("代码生成目录在 {}", produce.get("out"));
    }

}
