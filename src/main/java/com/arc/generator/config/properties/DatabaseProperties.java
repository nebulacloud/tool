package com.arc.generator.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class DatabaseProperties {

    public String schemaName = "";

    public String tableName = "";

    public String tableAlias = "";
}
