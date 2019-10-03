package com.arc.generator.service.impl;

import com.arc.generator.config.properties.ArcGeneratorProperties;
import com.arc.generator.mapper.MetaMapper;
import com.arc.generator.model.domain.meta.TableMeta;
import com.arc.generator.service.FileService;
import com.arc.generator.utils.JacksonUtils;
import freemarker.core.Environment;
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
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreemarkerGenerator implements InitializingBean, FileService {


    private static final Logger log = LoggerFactory.getLogger(FreemarkerGenerator.class);

    @Autowired
    private ArcGeneratorProperties generatorProperties;

    @Autowired
    private MetaMapper mapper;

    private static final String JAVA_FILE_SUFFIX = ".java";

    private static final String MAPPER_FILE_SUFFIX = ".xml";

    public String output;

    public Map<String, Object> dataModel = new HashMap<>(40);

    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        //准备输出目录
        log.debug("准备输出目录");
        //        dataModel = new HashMap<>(40);
        //
        //        File curDir = new File(this.getClass().getClassLoader().getResource(".").getPath());
        //        File outDir = new File(curDir.getParent() + "/output");
        //        if (!outDir.exists()) {
        //            outDir.mkdirs();
        //        }
        //        //T:\Project\Za\tool\target\output
        //        output = outDir.getPath();
        //
        //        System.out.println(output);
        //        System.out.println(output);
        //        String tableAlias = generatorProperties.getDatabase().getTableAlias();
        //        if (!tableAlias.endsWith("_")) {
        //            tableAlias += "_";
        //        }
    }

    public File getDefaultFile(String outputParameter) {
        if (outputParameter == null || outputParameter.trim().length() == 0 || "".equals(outputParameter.trim())) {
            outputParameter = "/output";
        }

        System.out.println(outputParameter);
        System.out.println(outputParameter);
        System.out.println(outputParameter);
        System.out.println(outputParameter);
        System.out.println(outputParameter);


        log.debug("参数 outputParameter={}", outputParameter);
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(".");
        String path = resource.getPath();

        System.out.println(classLoader);
        System.out.println(resource);
        System.out.println(path);

        File currentClassPathDir = new File(path);

        File outDir = new File(currentClassPathDir.getParent() + outputParameter);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        return outDir;
    }

    @Override
    public Map<String, Object> produce() {
        return produce(generatorProperties);
    }

    @Override
    public Map<String, Object> produce(ArcGeneratorProperties arcGeneratorProperties) {
        //创建 model 文件
        //创建 mapper接口文件
        //创建 mapper.xml文件
        //创建 service 文件
        //创建 service.impl 文件
        //创建 controller 文件
        Map<String, Object> parameterMap = new HashMap<>();
        try {
            log.info("创建 model 文件");
            //generateModel(meta);


            TableMeta meta = createTableMate();
            parameterMap.put("meta", meta);
            parameterMap.put(TableMeta.class.getName(), meta);

            //输出目录
//            output = getDefaultFile(arcGeneratorProperties.getProject().getOutput()).getPath();
            //包的信息 rootNamespace
            parameterMap.put("javaPackage", arcGeneratorProperties.getProject().getRootNamespace());

            String rootNamespace = arcGeneratorProperties.getProject().getRootNamespace();
            parameterMap.put("rootNamespace", rootNamespace);
            parameterMap.put("modelNamespace", rootNamespace + ".model");
            parameterMap.put("mapperNamespace", rootNamespace + ".mapper");
            parameterMap.put("serviceNamespace", rootNamespace + ".service");
            parameterMap.put("serviceImplNamespace", rootNamespace + ".impl");
            parameterMap.put("controllerNamespace", rootNamespace + ".controller");
            parameterMap.put("date", new Date());
//
//            parameterMap.put("modelNamespace", arcGeneratorProperties.getProject().getModelNamespace());
//            parameterMap.put("mapperNamespace", arcGeneratorProperties.getProject().getMapperNamespace());
//            parameterMap.put("serviceImplNamespace", arcGeneratorProperties.getProject().getMapperNamespace()+".impl");
//            parameterMap.put("controllerNamespace", arcGeneratorProperties.getProject().getControllerNamespace());
//

            parameterMap.put(ArcGeneratorProperties.class.getName(), arcGeneratorProperties);

            //--------------- create model
            generateStandardModel(parameterMap);

            generateStandard(parameterMap, "service.ftl");


//            log.info("创建 mapper接口文件");
//            generateMapper(meta);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("e ={}", e);
        }

        parameterMap.put("result", true);
        return parameterMap;
    }

    //--------------------------------------test --model
    private void generateStandardModel(Map<String, Object> parameterMap) throws Exception {
        log.debug("Freemarker configuration ={}", configuration);
        log.debug("参数 parameterMap ={}", JacksonUtils.toJson(parameterMap));

        Template template = configuration.getTemplate("model.ftl");
        log.info("Use template file: {}. ", template.getName());


        //输出文件处理
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(".");
        //target  目录
        String path = new File(resource.getPath()).getParent();
        //todo 参数校验    target +  传入参数
        path = path + File.separator + ((ArcGeneratorProperties) parameterMap.get(ArcGeneratorProperties.class.getName())).getProject().getOutput() + File.separator;

        TableMeta tableMeta = (TableMeta) parameterMap.get(TableMeta.class.getName());
        String className = tableMeta.getClassName();
        String newFilePath = path + className + JAVA_FILE_SUFFIX;

        System.out.println(newFilePath);
        System.out.println(newFilePath);
        System.out.println(newFilePath);
        parameterMap.put("output", newFilePath);
        log.debug("文件名称={}", newFilePath);
        File javaFile = new File(newFilePath);
        if (!javaFile.exists()) {
            //createNewFile这个方法只能在一层目录下创建文件，不能跳级创建，尽管可以用mkdir(s)创建多层不存在的目录，但是不要直接一个File对象搞定目录和文件都需要创建的情况，可以在已有目录下直接用createNewFile创建文件
            if (!javaFile.getParentFile().exists()) {
                boolean mkdirs = javaFile.getParentFile().mkdirs();
                log.debug("父级路径创建结果={}", mkdirs);
                System.out.println(mkdirs);
                System.out.println(mkdirs);
                System.out.println("mkdirs");
            }

            boolean result = javaFile.createNewFile();
            System.out.println(result);
        }
        FileWriter writer = new FileWriter(javaFile);
        Environment processingEnvironment = template.createProcessingEnvironment(parameterMap, writer, null);
        writer.close();
        System.out.println(processingEnvironment);
    }

    private void generateStandard(Map<String, Object> parameterMap, String templatePath) throws Exception {
        log.debug("Freemarker configuration ={}", configuration);
        log.debug("模板路径 ={}", templatePath);
        log.debug("参数 parameterMap ={}", JacksonUtils.toJson(parameterMap));

        Template template = configuration.getTemplate(templatePath);
        log.info("Use template file: {}. ", template.getName());


        //输出文件处理
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(".");
        //target  目录
        String path = new File(resource.getPath()).getParent();
        //todo 参数校验    target +  传入参数
        path = path + File.separator + ((ArcGeneratorProperties) parameterMap.get(ArcGeneratorProperties.class.getName())).getProject().getOutput() + File.separator;

        TableMeta tableMeta = (TableMeta) parameterMap.get(TableMeta.class.getName());
        String className = tableMeta.getClassName();
        String newFilePath = path + className+"Service" + JAVA_FILE_SUFFIX;

        System.out.println(newFilePath);
        System.out.println(newFilePath);
        System.out.println(newFilePath);
        parameterMap.put("output", newFilePath);
        log.debug("文件名称={}", newFilePath);
        File javaFile = new File(newFilePath);
        if (!javaFile.exists()) {
            //createNewFile这个方法只能在一层目录下创建文件，不能跳级创建，尽管可以用mkdir(s)创建多层不存在的目录，但是不要直接一个File对象搞定目录和文件都需要创建的情况，可以在已有目录下直接用createNewFile创建文件
            if (!javaFile.getParentFile().exists()) {
                boolean mkdirs = javaFile.getParentFile().mkdirs();
                log.debug("父级路径创建结果={}", mkdirs);
                System.out.println(mkdirs);
                System.out.println(mkdirs);
                System.out.println("mkdirs");
            }

            boolean result = javaFile.createNewFile();
            System.out.println(result);
        }
        FileWriter writer = new FileWriter(javaFile);
        Environment processingEnvironment = template.createProcessingEnvironment(parameterMap, writer, null);
        writer.close();
        System.out.println(processingEnvironment);
    }


    /**
     * 创建表
     *
     * @return
     */
    private TableMeta createTableMate() {
        TableMeta meta = mapper.get(generatorProperties.getDatabase().getSchemaName(), generatorProperties.getDatabase().getTableName());
        if (meta == null) {
            throw new IllegalArgumentException("\n指定的表不存在，请检查表的名称或数据库是否配置正确！\nPlease check schemaName and tableName are correct. ");
        }
        log.info("表的元信息为{}", JacksonUtils.toJson(meta));
        return meta;
    }

    // 创建 generate java model class
    private void generateModel(TableMeta meta) throws Exception {
        dataModel.put("meta", meta);
        dataModel.put("javaPackage", generatorProperties.getProject().getRootNamespace());

        String className = meta.getClassName();
        File javaFile = forceCreateFile(output + File.separator + className + JAVA_FILE_SUFFIX);

        Template template = configuration.getTemplate("model.ftl");
        log.info("Use template file: {}. ", template.getName());
        template.process(dataModel, new FileWriter(javaFile));
    }

    // generate mybatis mapper xml
    private void generateMapper(TableMeta meta) throws Exception {
        Template template = configuration.getTemplate("mapper.ftl");
        log.info("Use template file: {}. ", template.getName());

        dataModel.put("meta", meta);
        dataModel.put("javaPackage", generatorProperties.getProject().getRootNamespace());
        dataModel.put("columnPrefix", generatorProperties.getDatabase().getTableAlias());

        File mapperFile = createFileForce(meta.getMapperName() + MAPPER_FILE_SUFFIX);
        template.process(dataModel, new FileWriter(mapperFile));
    }

    /**
     * 全名？
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private File forceCreateFile(String fileName) throws IOException {
        log.debug("文件名称={}", fileName);
        File newFile = new File(fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        return newFile;
    }


    private File createFileForce(String fileName) throws IOException {
        log.debug("文件名称={}", fileName);
        File newFile = new File(output + File.separator + fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        return newFile;
    }

}
