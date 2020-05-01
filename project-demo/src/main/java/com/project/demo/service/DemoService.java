package com.project.demo.service;

import java.util.Map;

import com.crudcommons.api.base.service.BaseService;
import com.project.demo.entity.Demo;

public interface DemoService extends BaseService<Demo> {

	Object getObjectByID(Map<String, Object> paramMap);

}
