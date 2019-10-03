package com.arc.generator.mapper;

import com.arc.generator.model.domain.meta.TableMeta;
import org.apache.ibatis.annotations.Param;

public interface MetaMapper {

    /**
     *
     * @param tableSchema 数据库
     * @param tableName 表名称
     * @return TableMeta
     */
    TableMeta get(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);
}
