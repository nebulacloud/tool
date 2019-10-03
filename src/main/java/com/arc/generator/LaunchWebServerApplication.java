package com.arc.generator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类，项目将打包为jar，内置tomcat
 *
 * @author X
 */
@Slf4j
@MapperScan("com.arc.generator.mapper")
@SpringBootApplication
//        (exclude = {
//                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//                org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class
//        })
public class LaunchWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaunchWebServerApplication.class, args);
    }


}
