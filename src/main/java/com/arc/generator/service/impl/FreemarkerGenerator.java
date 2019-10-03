package com.arc.generator.service.impl;

import com.arc.generator.config.properties.ArcGeneratorProperties;
import com.arc.generator.model.domain.meta.TableMeta;
import com.arc.generator.service.FileService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreemarkerGenerator implements InitializingBean, FileService {


    private static final Logger logger = LoggerFactory.getLogger(FreemarkerGenerator.class);

    @Autowired
    private ArcGeneratorProperties generatorProperties;

    private static final String JAVA_FILE_SUFFIX = ".java";

    private static final String MAPPER_FILE_SUFFIX = ".xml";

    public String outputPath;

    public Map<String, Object> dataModel;


    @Autowired
    private Configuration configuration;

    @Override
    public boolean createService() {
        boolean flag = false;
        //创建 model 文件
        //创建 mapper接口文件
        //创建 mapper.xml文件
        //创建 service 文件
        //创建 service.impl 文件
        //创建 controller 文件
        System.out.println("        //创建 model 文件\n" +
                "        //创建 mapper接口文件\n" +
                "        //创建 mapper.xml文件\n" +
                "        //创建 service 文件\n" +
                "        //创建 service.impl 文件\n" +
                "        //创建 controller 文件");
        return flag;

    }
    @Override
    public void afterPropertiesSet() throws Exception {
        dataModel = new HashMap<>(40);
        File curDir = new File(this.getClass().getClassLoader().getResource(".").getPath());
        File outDir = new File(curDir.getParent() + "/output");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        outputPath = outDir.getPath();
        String tableAlias = generatorProperties.getDatabase().getTableAlias();
        if (!tableAlias.endsWith("_")) {
            tableAlias += "_";
        }
    }

    /**
     * 生成
     *
     * @param meta
     * @throws Exception
     */
    public void produce(TableMeta meta) throws Exception {
        generateModel(meta);
        generateMapper(meta);
    }

    // 创建 generate java model class
    private void generateModel(TableMeta meta) throws Exception {
        Template template = configuration.getTemplate("model.ftl");
        logger.info("Use template file: {}. ", template.getName());

        String className = meta.getClassName();

        dataModel.put("meta", meta);
        dataModel.put("javaPackage", generatorProperties.getProject().getRootNamespace());

        File javaFile = createFile(className + JAVA_FILE_SUFFIX);
        template.process(dataModel, new FileWriter(javaFile));
    }

    // generate mybatis mapper xml
    private void generateMapper(TableMeta meta) throws Exception {
        Template template = configuration.getTemplate("mapper.ftl");
        logger.info("Use template file: {}. ", template.getName());

        dataModel.put("meta", meta);
        dataModel.put("javaPackage", generatorProperties.getProject().getRootNamespace());
        dataModel.put("columnPrefix", generatorProperties.getDatabase().getTableAlias());

        File mapperFile = createFile(meta.getMapperName() + MAPPER_FILE_SUFFIX);
        template.process(dataModel, new FileWriter(mapperFile));
    }

    private File createFile(String fileName) throws IOException {
        File newFile = new File(outputPath + "//" + fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        return newFile;
    }

}
