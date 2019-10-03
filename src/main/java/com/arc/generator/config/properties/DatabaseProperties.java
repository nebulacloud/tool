package com.arc.generator.config.properties;

import lombok.*;

/**
 * 表的名称、在mapper中的别名 相关属性
 *
 * @author 叶超
 * @since 2019/5/22 21:47
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseProperties {

    public String schemaName = "";

    public String tableName = "";

    public String tableAlias = "";
}
