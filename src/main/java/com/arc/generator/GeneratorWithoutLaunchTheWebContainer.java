package com.arc.generator;

import com.arc.generator.config.SpringAndMybatisConfig;
import com.arc.generator.service.impl.BootStrap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 仅仅创建用表来创建 model、mapper、service、controller 。而不启动web容器
 * Do not launch the web container.
 */
public class GeneratorWithoutLaunchTheWebContainer {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringAndMybatisConfig.class);
        ac.getBean(BootStrap.class).start();
    }

}
