package com.crudcommons.api.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface InsertMapper<T> {

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等
     *
     * @param recordList
     * @return	
     */
    @InsertProvider(type = InsertListPlusProvider.class, method = "insertList")
    int insertList(List<T> recordList);
}