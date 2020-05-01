package com.project.demo.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudcommons.api.base.service.impl.BaseServiceImpl;
import com.project.demo.entity.Demo;
import com.project.demo.mapper.DemoMapper;
import com.project.demo.service.DemoService;

@Service
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements DemoService {

	@Autowired
	DemoMapper demoMapper;
	
	public Object getObjectByID(Map<String, Object> paramMap) {
		return demoMapper.getObjectByID(paramMap);
	}


}
