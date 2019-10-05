package com.arc.generator.service.impl;

import com.arc.generator.config.properties.ArcGeneratorPropertiesProvider;
import com.arc.generator.mapper.MetaMapper;
import com.arc.generator.model.domain.meta.TableMeta;
import com.arc.generator.utils.JacksonUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreemarkerGeneratorServiceImpl implements InitializingBean, com.arc.generator.service.FreemarkerGeneratorService {


    private static final Logger log = LoggerFactory.getLogger(FreemarkerGeneratorServiceImpl.class);

    @Autowired
    private ArcGeneratorPropertiesProvider generatorPropertiesProvider;

    @Autowired
    private MetaMapper mapper;

    private static final String JAVA_FILE_SUFFIX = ".java";


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
        return produce(generatorPropertiesProvider);
    }

    String modelFtl = "model.ftl";
    String requestFtl = "request.ftl";
    String responseFtl = "response.ftl";
    String mapperInterfaceFtl = "mapperInterface.ftl";
    String mapperXmlFtl = "mapperXml.ftl";
    String serviceFtl = "service.ftl";
    String serviceImplFtl = "serviceImpl.ftl";
    String controllerFtl = "controller.ftl";

    /**
     * 创建 model 文件
     * 创建 mapper接口文件
     * 创建 mapper.xml文件
     * 创建 service 文件
     * 创建 service.impl 文件
     * 创建 controller 文件
     *
     * @param arcGeneratorPropertiesProvider
     * @return
     */
    @Override
    public Map<String, Object> produce(ArcGeneratorPropertiesProvider arcGeneratorPropertiesProvider) {
        Map<String, Object> parameterMap = new HashMap<>();
        try {
            log.info("创建 model 文件");
            //generateModel(meta);
            log.info("创建 mapper接口文件");
            //generateMapper(meta);
            log.info("创建 mapper.xml文件");


            //数据准备

            TableMeta meta = createTableMate();
            parameterMap.put("meta", meta);
            parameterMap.put(TableMeta.class.getName(), meta);

            //输出目录
//            output = getDefaultFile(arcGeneratorProperties.getProject().getOutput()).getPath();
            //包的信息 rootNamespace
            parameterMap.put("javaPackage", arcGeneratorPropertiesProvider.getProject().getRootNamespace());
            String rootNamespace = arcGeneratorPropertiesProvider.getProject().getRootNamespace();
            parameterMap.put("rootNamespace", rootNamespace);
            parameterMap.put("modelNamespace", rootNamespace + ".model");
            parameterMap.put("requestNamespace", rootNamespace + ".request");
            parameterMap.put("mapperNamespace", rootNamespace + ".mapper");
            parameterMap.put("serviceNamespace", rootNamespace + ".service");
            parameterMap.put("serviceImplNamespace", rootNamespace + ".impl");
            parameterMap.put("controllerNamespace", rootNamespace + ".controller");
            parameterMap.put("lowerCaseFirstWordClassName", meta.getLowerCaseFirstWordClassName());
            parameterMap.put("createTime", new Date());

            String tableAlias = generatorPropertiesProvider.getDatabase().getTableAlias();
            if (!tableAlias.endsWith("_")) {
                tableAlias += "_";
            }
            parameterMap.put("tableAlias", tableAlias);


            parameterMap.put(ArcGeneratorPropertiesProvider.class.getName(), arcGeneratorPropertiesProvider);

            //--------------- create model
            //generateStandard(parameterMap, serviceFtl);

            //测试中先写死的输出路径
            String output = "C:\\Users\\Z\\Desktop\\Za\\temp\\";
            String modelOutputFileName = output + meta.getClassName() + ".java";
            String requestOutputFileName = output + meta.getClassName() + "Request.java";
            String responseOutputFileName = output + meta.getClassName() + "Response.java";
            String mapperInterfaceOutputFileName = output + meta.getClassName() + "Mapper.java";
            String mapperXmlOutputFileName = output + meta.getClassName() + "Mapper.xml";
            String serviceOutputFileName = output + meta.getClassName() + "Service.java";
            String serviceImplOutputFileName = output + meta.getClassName() + "ServiceImpl.java";
            String controllerOutputFileName = output + meta.getClassName() + "Controller.java";

            process(parameterMap, modelFtl, modelOutputFileName);
            process(parameterMap, requestFtl, requestOutputFileName);
            process(parameterMap, mapperInterfaceFtl, mapperInterfaceOutputFileName);
            process(parameterMap, mapperXmlFtl, mapperXmlOutputFileName);
            process(parameterMap, serviceFtl, serviceOutputFileName);
            process(parameterMap, serviceImplFtl, serviceImplOutputFileName);
            process(parameterMap, controllerFtl, controllerOutputFileName);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("e ={}", e);
        }
        parameterMap.put("result", true);
        return parameterMap;
    }

    //---------------------通用的
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
        path = path + File.separator + ((ArcGeneratorPropertiesProvider) parameterMap.get(ArcGeneratorPropertiesProvider.class.getName())).getProject().getOutput() + File.separator;

        TableMeta tableMeta = (TableMeta) parameterMap.get(TableMeta.class.getName());
        String className = tableMeta.getClassName();
        String newFilePath = path + className + "ServiceImpl" + JAVA_FILE_SUFFIX;

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
//        FileWriter writer = new FileWriter(javaFile);
//        Environment processingEnvironment = template.createProcessingEnvironment(parameterMap, writer, null);
//        writer.flush();
//        writer.close();
//        System.out.println(processingEnvironment);

        template.process(parameterMap, new FileWriter(javaFile));
    }


    /**
     * 获取表的元数据
     *
     * @return 表的元数据
     */
    private TableMeta createTableMate() {
        TableMeta meta = mapper.get(generatorPropertiesProvider.getDatabase().getSchemaName(), generatorPropertiesProvider.getDatabase().getTableName());
        try {
            FileOutputStream fos = new FileOutputStream("T:\\Project\\Za\\tool\\src\\main\\resources\\templates\\version-1\\SerializableTableMeta.txt");
            ObjectOutputStream objectOutputStream = null;
            objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(meta);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (meta == null) {
            throw new IllegalArgumentException("\n指定的表不存在，请检查表的名称或数据库是否配置正确！\nPlease check schemaName and tableName are correct. ");
        }
        log.info("表的元信息为{}", JacksonUtils.toJson(meta));
        return meta;
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
        //output + File.separator + fileName
        File newFile = new File(fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        return newFile;
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
        path = path + File.separator + ((ArcGeneratorPropertiesProvider) parameterMap.get(ArcGeneratorPropertiesProvider.class.getName())).getProject().getOutput() + File.separator;

        TableMeta tableMeta = (TableMeta) parameterMap.get(TableMeta.class.getName());
        String className = tableMeta.getClassName();
        String newFilePath = path + className + JAVA_FILE_SUFFIX;

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
//        FileWriter writer = new FileWriter(javaFile);
//        Environment processingEnvironment = template.createProcessingEnvironment(parameterMap, writer, null);
//        writer.close();
//        System.out.println(processingEnvironment);

        template.process(parameterMap, new FileWriter(javaFile));

    }

    public Object process(Map<String, Object> parameterMap, String templateName, String outputFileFullName) throws IOException, TemplateException {
        if (parameterMap == null) {
            parameterMap = new HashMap<>(1);
            parameterMap.put("result", false);
        }
        //todo 数据与模板合成 并输出
        log.debug("参数 parameterMap ={}", JacksonUtils.toJson(parameterMap));
        log.debug("Freemarker configuration ={},templateName={},outputFileFullName={}", configuration, templateName, outputFileFullName);
        Template template = configuration.getTemplate(templateName);

        File outputFile = new File(outputFileFullName);
        if (!outputFile.exists()) {
            //createNewFile这个方法只能在一层目录下创建文件，不能跳级创建，尽管可以用mkdir(s)创建多层不存在的目录，但是不要直接一个File对象搞定目录和文件都需要创建的情况，可以在已有目录下直接用createNewFile创建文件
            if (!outputFile.getParentFile().exists()) {
                boolean mkdirs = outputFile.getParentFile().mkdirs();
                log.debug("父级路径创建结果={}", mkdirs);
                System.out.println(mkdirs);
                System.out.println(mkdirs);
                System.out.println("mkdirs");
            }

            boolean result = outputFile.createNewFile();
            System.out.println(result);
        }

        FileWriter writer = new FileWriter(outputFile);
//        Environment processingEnvironment = template.createProcessingEnvironment(parameterMap, writer, null);
        template.process(parameterMap, writer);
        writer.flush();
        writer.close();
        return parameterMap;
//        log.debug("模板输出后返回processingEnvironment={}", processingEnvironment);

    }

}
