package com.arc.generator.config.properties;

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
public class ProjectProperties {

    private String rootNamespace = "com.arc";
    private String mapperNamespace = "com.arc.mapper";
    private String modelNamespace = "com.arc.model.domain";

}
