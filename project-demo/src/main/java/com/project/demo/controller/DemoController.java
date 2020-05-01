package com.project.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crudcommons.api.base.controller.BaseController;
import com.crudcommons.api.base.utils.ApiResultModel;
import com.project.demo.entity.Demo;
import com.project.demo.service.DemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/demo")
@Api(tags = "demo相关接口")
public class DemoController extends BaseController{
	
	@Autowired
	protected DemoService demoService;
	
	@ApiOperation(value = "批量添加接口")
	@RequestMapping(value = "/saveBatch", method = RequestMethod.POST, consumes = "application/json")
	public ApiResultModel saveBatch(@RequestBody Map<String, Object> paramMap) {
		ApiResultModel result = new ApiResultModel();
		List<Demo> list = new ArrayList<Demo>();
		for (int i = 0; i < 2; i++) {
			Demo obj = new Demo();
			obj.setCode("1234");
			list.add(obj);
			
		}
		result.setResult(demoService.saveBatch(list));
		return result;
	}
	
	@RequestMapping(value = "/getObjectByID", method = RequestMethod.POST, consumes = "application/json")
	public ApiResultModel getObjectByID(@RequestBody Map<String, Object> paramMap) {
		ApiResultModel result = new ApiResultModel();
		result.setResult(demoService.getObjectByID(paramMap));
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("九九乘法表");
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < i+1; j++) {
				System.out.print(i+" * "+j+" = "+i*j+"   ");
			}
			System.out.println("");
		}
	}

}
