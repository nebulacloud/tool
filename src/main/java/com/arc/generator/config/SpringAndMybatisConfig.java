package com.arc.generator.config;

import com.arc.generator.config.properties.ArcGeneratorProperties;
import com.arc.generator.config.properties.TestValue;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@ComponentScan("com")
//@PropertySource("classpath:application.properties")
//@MapperScan("com.arc.generator.mapper")
public class SpringAndMybatisConfig {

    @Autowired
    private ArcGeneratorProperties generatorProperties;

    @Autowired
    private TestValue testValue;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;

//    @Value("${spring.datasource.url}")
//    private String url;

//    @Value("${spring.datasource.username}")
//    private String username;

//    @Value("${spring.datasource.password}")
//    private String password;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        //        String username = "root";
//        String password = "admin";
//        String driverClassName = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&useSSL=false&serverTimezone=GMT%2B8";

        String driverClassName=generatorProperties.getDataSource().getDriverClassName();
        String url=generatorProperties.getDataSource().getUrl();
        String username=generatorProperties.getDataSource().getUsername();
        String password=generatorProperties.getDataSource().getPassword();



//        String username = testValue.getUsername();
//        String password = testValue.getPassword();
//        String driverClassName = testValue.getDriverClassName();
//        String url = testValue.getUrl();


        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        return factory.getObject();
    }

//    @Bean
//    public Configuration freemarkerConfiguration() throws Exception {
//        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerAutoConfiguration("classpath:templates");
//        bean.setTemplateLoaderPath("classpath:templates");
//        bean.setDefaultEncoding("UTF-8");
//        return bean.createConfiguration();
//    }

}
