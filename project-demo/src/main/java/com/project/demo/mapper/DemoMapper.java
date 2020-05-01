package com.project.demo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.crudcommons.api.base.mapper.BaseMapper;
import com.project.demo.entity.Demo;

@Mapper
public interface DemoMapper extends BaseMapper<Demo>{

	Demo getObjectByID(Map<String, Object> paramMap);

}
