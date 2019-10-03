package com.arc.generator.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 叶超
 * @since 2019/5/22 22:17
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectProperties {

    private String rootNamespace = "com.arc";
    private String mapperNamespace = "com.arc.mapper";
    private String modelNamespace = "com.arc.model.domain";
    private String serviceNamespace;
    private String serviceImplNamespace;
    private String controllerNamespace;
    private String output;

    public ProjectProperties(String rootNamespace, String mapperNamespace, String modelNamespace, String output) {
        this.rootNamespace = rootNamespace;
        this.mapperNamespace = mapperNamespace;
        this.modelNamespace = modelNamespace;
        this.output = output;
    }

}
