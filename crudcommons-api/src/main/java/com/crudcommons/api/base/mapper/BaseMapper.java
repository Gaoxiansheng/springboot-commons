package com.crudcommons.api.base.mapper;

import tk.mybatis.mapper.common.Mapper;

/**
 * 平台通用Mapper
 * 
 * @author gaojinxin
 *
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>, InsertMapper<T> {
	
 	 
}
