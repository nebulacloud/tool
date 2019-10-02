package com.mybatis.generator.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatis.generator.meta.TableMeta;

public interface MetaMapper {

    /**
     *
     * @param tableSchema 数据库
     * @param tableName 表名称
     * @return TableMeta
     */
    TableMeta get(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);
}
