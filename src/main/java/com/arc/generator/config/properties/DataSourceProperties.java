package com.arc.generator.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * jdbc 连接相关配置
 *
 * @author 叶超
 * @since: 2019/5/8 22:24
 */
@Setter
@Getter
@ToString
public class DataSourceProperties {

    //驱动名称
    private String username = "root";
    private String password = "admin";
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&useSSL=false&serverTimezone=GMT%2B8";


}
