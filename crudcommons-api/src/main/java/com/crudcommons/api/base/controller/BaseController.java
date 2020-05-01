package com.crudcommons.api.base.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crudcommons.api.base.service.BaseService;
import com.crudcommons.api.base.utils.ApiResultModel;
import com.crudcommons.api.base.utils.BaseUtils;

import io.swagger.annotations.ApiOperation;

/**
 * 平台基础BaseController <br/>
 * 提供基础的增删改查等操作
 * @author gaojinxin
 *
 */
public abstract class BaseController extends BaseUtils implements InitializingBean{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 如果需要调用子类独有的方法、则需要在子类中注入实现类<br/>
	 * 
	 * <pre>
	 * &#64;Autuwired
	 * ServeyService service;
	 * service.getContentTypeByPage()
	 * </pre>
	 */
	protected BaseService<?> service;
	
	/**
	 * bean初始化后对bean的回调
	 */
	public void afterPropertiesSet() throws Exception {
		logger.info("bean初始化后调用afterPropertiesSet方法, {}",this);
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldType = field.getType().getSimpleName();
			if (fieldType.contains("Service")) {
				String mapperFieldName = fieldType.replace("Service", "");
				if (this.getClass().getSimpleName().replaceAll("Controller", "").equals(mapperFieldName)) {
					field.setAccessible(true);
					Object service = field.get(this);
					if (service instanceof BaseService) {
						this.service = (BaseService<?>) service;
						break;
					}
				}
			}
		}
		if (service == null) {
			logger.error("service 为空, {}", this);
		}
		
	}
	
	/**
	 * 新增对象
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "新增对象接口")
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	public ApiResultModel save(@RequestBody Map<String, Object> paramMap) {
		ApiResultModel result = new ApiResultModel();
		result.setResult(service.save(paramMap));
		return result;
	}
	
	/**
	 * 根据主键修改对象
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "根据主键修改对象接口")
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
	public ApiResultModel update(@RequestBody Map<String, Object> paramMap) {
		ApiResultModel result = new ApiResultModel();
		result.setResult(service.update(paramMap));
		return result;
	}
	
	/**
	 * 根据url上的ID获取对象
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "根据url上的ID获取对象接口")
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ApiResultModel get(@PathVariable("id") String id) {
		ApiResultModel result = new ApiResultModel();
		result.setResult(service.getObjectById(id));
		return result;
	}
	
	/**
	 * 根据参数获取列表
	 * 用法：
	 * <pre>
	 * 排序：	排序字段必须为数据库字段
	 *	paramMap.put("orderBy", "id desc"); 按照id降序
	 *	paramMap.put("orderBy", "id asc,sort desc"); 多个字段排序
	 * 分页：	实际采用limit，如果不设置分页则搜索全部
	 * 	paramMap.put("pageCount", 20); 页面条数
	 *	paramMap.put("pageNum", 1); 当前页码
	 * 范围查询：采用sql中between and
	 * 	--如想应用范围查询，则参数中必有between作为KEY，否则将变为in查询
	 * 	paramMap.put("between", "sort,date"); 采用范围查询的字段以,分隔
	 *	paramMap.put("column", new String[]{'开始','结束'});
	 * null查询：
	 *	paramMap.put("column", "is null");
	 *	paramMap.put("column", "is not null");
	 * in:
	 *	paramMap.put("column", Set<>||List等集合);
	 * 去重:
	 * 	paramMap.put("distinct", true);
	 * 模糊查询:
	 * 	前端传入的参数默采用精确查询,如果需要模糊查询，需要把相关参数加入到fuzzy中, 用逗号分割
	 * 	paramMap.put("fuzzy","name,title");
	 * </pre>
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "根据参数获取列表接口")
	@RequestMapping(value = "/queryList", method = RequestMethod.POST, consumes = "application/json")
	public ApiResultModel queryList(@RequestBody Map<String, Object> paramMap) {
		ApiResultModel result = new ApiResultModel();
		List<?> list = null;
		if(isNotEmpty(paramMap)) {
			String[] between = (paramMap.get("between")+"").split(",");
			for (String betweenKey : between) {
				if(isNotEmpty(paramMap.get(betweenKey))) {
					List<Object> betweenList = (List<Object>) paramMap.get(betweenKey);
					String[] betweens = new String[betweenList.size()];
					betweenList.toArray(betweens);
					paramMap.put(betweenKey, betweens);
				}
			}
			list = service.queryListByExample(paramMap);
		}else {
			list = service.queryAll();
		}
		result.setResult(list);
		return result;
	}
	
	/**
	 * 根据参数获取分页数据
	 * 用法：
	 * <pre>
	 * 排序：	排序字段必须为数据库字段
	 *	paramMap.put("orderBy", "id desc"); 按照id降序
	 *	paramMap.put("orderBy", "id asc,sort desc"); 多个字段排序
	 * 分页：	必须参数
	 * 	paramMap.put("pageCount", 20); 页面条数
	 *	paramMap.put("pageNum", 1); 当前页码
	 * 范围查询：采用sql中between and
	 * 	--如想应用范围查询，则参数中必有between作为KEY，否则将变为in查询
	 * 	paramMap.put("between", "sort,date"); 采用范围查询的字段以逗号分隔
	 *	paramMap.put("column", new String[]{'开始','结束'});
	 * null查询：
	 *	paramMap.put("column", "is null");
	 *	paramMap.put("column", "is not null");
	 * in:
	 *	paramMap.put("column", Set<>||List等集合);
	 * 去重:
	 * 	paramMap.put("distinct", true);
	 * 模糊查询:
	 * 	前端传入的参数默采用精确查询,如果需要模糊查询，需要把相关参数加入到fuzzy中, 用逗号分割
	 * 	paramMap.put("fuzzy","name,title");
	 * </pre>
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "根据参数获取分页数据接口")
	@RequestMapping(value = "/page", method = RequestMethod.POST, consumes = "application/json")
	public ApiResultModel page(@RequestBody Map<String, Object> paramMap) {
		ApiResultModel result = new ApiResultModel();
		if(isNotEmpty(paramMap)) {
			String[] between = (paramMap.get("between")+"").split(",");
			for (String betweenKey : between) {
				if(isNotEmpty(paramMap.get(betweenKey))) {
					List<Object> betweenList = (List<Object>) paramMap.get(betweenKey);
					String[] betweens = new String[betweenList.size()];
					betweenList.toArray(betweens);
					paramMap.put(betweenKey, betweens);
				}
			}
			result.setResult(service.getPage(paramMap));
		}
		return result;
	}
	
	/**
	 * 根据url上的ID删除对象<br/>
	 * --此删除为硬删除
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "根据url上的ID删除对象接口")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ApiResultModel delete(@PathVariable("id") String id) {
		ApiResultModel result = new ApiResultModel();
		result.setResult(service.deleteObjectById(id));
		return result;
	}
	
	/**
	 * 根据url上的ID删除对象<br/>
	 * --此删除为软删除
	 * @param paramMap
	 * @return ApiResultModel
	 */
	@ApiOperation(value = "根据url上的ID删除对象接口")
	@RequestMapping(value = "/deletePseudo/{id}", method = RequestMethod.GET)
	public ApiResultModel deletePseudo(@PathVariable("id") String id) {
		ApiResultModel result = new ApiResultModel();
		result.setResult(service.deletePseudoObjectById(id));
		return result;
	}

}
