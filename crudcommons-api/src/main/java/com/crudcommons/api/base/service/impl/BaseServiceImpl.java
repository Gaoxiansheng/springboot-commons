package com.crudcommons.api.base.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.crudcommons.api.base.entity.BaseEntity;
import com.crudcommons.api.base.mapper.BaseMapper;
import com.crudcommons.api.base.service.BaseService;
import com.crudcommons.api.base.utils.BaseUtils;
import com.crudcommons.api.base.utils.ClassUtils;
import com.crudcommons.api.base.utils.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 平台基础BaseServiceImpl <br/>
 * 提供更丰富的增删改查等操作<br/>
 * 如需更复杂的操作，可在相应mapper写其他方法
 * @author gaojinxin
 *
 */
public abstract class BaseServiceImpl<T extends BaseEntity> extends BaseUtils implements BaseService<T>, InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected BaseMapper<T> baseMapper;

	public void afterPropertiesSet() throws Exception {
		
	}

	/**
	 * 保存一个实体，null属性也会保存<br/>
	 * --如果基础been属性为空，会帮你初始化基础been
	 */
	public int save(T paramT) {
		initBaseEntity(paramT,true);
		return baseMapper.insert(paramT);
	}
	
	/**
	 * 保存一个实体，null属性也会保存<br/>
	 * --如果基础been属性为空，会帮你初始化基础been
	 */
	public int save(Map<String, Object> paramMap) {
		if (isNotEmpty(paramMap)) {
			Class beanClass = ClassUtils.getGenericClass(this.getClass(), 0);
			T obj = (T)mapToBean(paramMap, beanClass);
			initBaseEntity(obj,true);
			return baseMapper.insert(obj);
		} else {
			return 0;
		}
	}

	/**
	 * 批量保存实体，null属性也会保存<br/>
	 * --如果基础been属性为空，会帮你初始化基础been
	 */
	public int saveBatch(List<T> paramList) {
		if (isNotEmpty(paramList)) {
			for (T t : paramList) {
				initBaseEntity(t, true);
			}
			return baseMapper.insertList(paramList);
		} else {
			return 0;
		}
	}

	/**
	 * 根据主键更新属性不为null的值<br/>
	 * --如果修改时间为空，会帮你新建修改时间
	 */
	public int update(T paramT) {
		initBaseEntityForUpdate(paramT);
		return baseMapper.updateByPrimaryKeySelective(paramT);
	}
	
	/**
	 * 根据主键更新属性不为null的值<br/>
	 * --如果修改时间为空，会帮你新建修改时间
	 */
	public int update(Map<String, Object> paramMap) {
		if (isNotEmpty(paramMap)) {
			Class beanClass = ClassUtils.getGenericClass(this.getClass(), 0);
			T obj = (T)mapToBean(paramMap, beanClass);
			initBaseEntityForUpdate(obj);
			return baseMapper.updateByPrimaryKeySelective(obj);
		} else {
			return 0;
		}
	}

	/**
	 * 批量更新<br/>
	 * 根据主键更新属性不为null的值<br/>
	 * --如果修改时间为空，会帮你新建修改时间
	 */
	public int updateBatch(List<T> paramList) {
		if (isNotEmpty(paramList)) {
			int result = 0;
			for (T t : paramList) {
				result += update(t);
			}
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * 根据主键删除数据<br/>
	 * --此删除为硬删除
	 */
	public int deleteObjectById(String paramK) {
		if (isNotEmpty(paramK)) {
			return baseMapper.deleteByPrimaryKey(paramK);
		} else {
			return 0;
		}
	}

	/**
	 * 批量删除<br/>
	 * 根据主键删除数据<br/>
	 * --此删除为硬删除
	 */
	public int deleteAllObjectByIds(List<String> paramList) {
		if (isNotEmpty(paramList)) {
			int result = 0;
			for (String id : paramList) {
				result += deleteObjectById(id);
			}
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * 根据主键获取对象
	 */
	public T getObjectById(String paramK) {
		if (isNotEmpty(paramK)) {
			return baseMapper.selectByPrimaryKey(paramK);
		} else {
			return null;
		}
	}

	/**
	 * 根据传入的条件获取第一条数据<br/>
	 * 用法：
	 * <pre>
	 * 排序：	排序字段必须为数据库字段
	 *	paramMap.put("orderBy", "id desc"); 按照id降序
	 *	paramMap.put("orderBy", "id asc,sort desc"); 多个字段排序
	 * 分页：	实际采用limit，如果不设置分页则搜索全部
	 * 	paramMap.put("pageCount", 20); 页面条数
	 *	paramMap.put("pageNum", 1); 当前页码
	 * 范围查询：采用sql中between and
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
	 */
	public T getObjectByExample(Map<String, Object> paramMap) {
		List<T> list = queryListByExample(paramMap);
		if (isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据传入的条件获取分页数据<br/>
	 * 用法：
	 * <pre>
	 * 排序：	排序字段必须为数据库字段
	 *	paramMap.put("orderBy", "id desc"); 按照id降序
	 *	paramMap.put("orderBy", "id asc,sort desc"); 多个字段排序
	 * 分页：	必须参数
	 * 	paramMap.put("pageCount", 20); 页面条数
	 *	paramMap.put("pageNum", 1); 当前页码
	 * 范围查询：采用sql中between and
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
	 */
	public Page<T> getPage(Map<String, Object> paramMap) {
		List<T> selectByExample = baseMapper.selectByExample(map2example(paramMap));
		PageInfo<T> pageInfo = new PageInfo<T>(selectByExample);
		Page resultPage = getResultPage(pageInfo);
		return resultPage;
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> queryList(T param) {
		if (isNotEmpty(param)) {
			return baseMapper.select(param);
		} else {
			return null;
		}
	}

	/**
	 * 根据传入的条件获取数据结果集<br/>
	 * 用法：
	 * <pre>
	 * 排序：	排序字段必须为数据库字段
	 *	paramMap.put("orderBy", "id desc"); 按照id降序
	 *	paramMap.put("orderBy", "id asc,sort desc"); 多个字段排序
	 * 分页：	实际采用limit，如果不设置分页则搜索全部
	 * 	paramMap.put("pageCount", 20); 页面条数
	 *	paramMap.put("pageNum", 1); 当前页码
	 * 范围查询：采用sql中between and
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
	 */
	public List<T> queryListByExample(Map<String, Object> paramMap) {
		if (isNotEmpty(paramMap)) {
			return baseMapper.selectByExample(map2example(paramMap));
		} else {
			return null;
		}
	}
	
	/**
	 * 查询全部结果
	 */
	public List<T> queryAll() {
		return baseMapper.selectAll();
	}

	/**
	 * 根据主键删除数据，实则改变数据的状态<br/>
	 * --此删除为软删除
	 */
	public int deletePseudoObjectById(String paramK) {
		if (isNotEmpty(paramK)) {
			try {
				Class<?> beanClass = ClassUtils.getGenericClass(getClass(), 0);
				BaseEntity entity = (BaseEntity) beanClass.newInstance();
				entity.setId(paramK);
				// 此位置可以增加要修改的字段
				initBaseEntityForUpdate(entity);
				return baseMapper.updateByPrimaryKeySelective((T) entity);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return 0;
		} else {
			return 0;
		}
	}

	/**
	 * 批量删除<br/>
	 * 根据主键删除数据，实则改变数据的状态<br/>
	 * --此删除为软删除
	 */
	public int deletePseudoAllObjectByIds(List<String> paramList) {
		if (isNotEmpty(paramList)) {
			int result = 0;
			for (String id : paramList) {
				result += deletePseudoObjectById(id);
			}
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * 获取主键
	 */
	public String generatePk() {
		return getUUID();
	}
	
	protected void pageStart(Map<String, Object> map) {
		Object pageNumObj = map.get("pageNum");// 当前页码
		Object pageCountObj = map.get("pageCount");// 分页大小
		if (pageNumObj != null && pageCountObj != null) {
			int pageNum = Integer.valueOf(pageNumObj + "");
			int pageCount = Integer.valueOf(pageCountObj + "");
			PageHelper.startPage(pageNum, pageCount, true, false, null);
		}
	}
	
	protected Example map2example(Map<String, Object> paramMap) {
		pageStart(paramMap);
		Class<?> tClass = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		// 组装查询
		Example example = new Example(tClass);
		Criteria criteria = example.createCriteria();
		String orderBy = null;
		Object fuzzy = paramMap.get("fuzzy");
		for (String property : paramMap.keySet()) {
			Object value = paramMap.get(property);
			if (value == null) {
				continue;
			}
			// 排序条件
			if ("orderBy".equalsIgnoreCase(property)) {
				orderBy = String.valueOf(value);
			} else if ("distinct".equalsIgnoreCase(property)) {
				example.setDistinct(Boolean.valueOf(value + ""));
			} else {
				// 查询条件
				if (isField(tClass, property)) {
					// 第一个参数是 Entity的Field名
					if (fuzzy != null && String.valueOf(fuzzy).contains(property)) {
						criteria.andLike(property, "%" + String.valueOf(value) + "%");
					}
					// in
					else if (value instanceof Collection<?>) {
						Collection<?> coll = (Collection<?>) value;
						criteria.andIn(property, coll);
					}
					// 区间
					else if (value.getClass().isArray()) {
						String[] arr = (String[]) value;
						criteria.andBetween(property, arr[0], arr[1]);
					}
					// null
					else if ("is null".equalsIgnoreCase(value + "")) {
						criteria.andIsNull(property);
					} else if ("is not null".equalsIgnoreCase(value + "")) {
						criteria.andIsNotNull(property);
					}
					// 精确匹配
					else {
						criteria.andEqualTo(property, value);
					}
				}
			}
		}
		if (orderBy != null) {
			example.setOrderByClause(orderBy);
		}
		return example;
	}
	
	protected boolean isField(Class<?> clazz, String fieldName) {
		try {
			clazz.getDeclaredField(fieldName);
			return true;
		} catch (Exception e) {
			try {
				clazz.getSuperclass().getDeclaredField(fieldName);
				return true;
			} catch (Exception e1) {
			}
		} 
		return false;
	}
	
	public Page getResultPage(PageInfo<? extends Object> currentPage) {
		List<?> list = currentPage.getList();
		// 共多少条
		long total = currentPage.getTotal();
		// 当前页码
		int pageNum = currentPage.getPageNum();
		if (pageNum == 0) {
			pageNum = 1;// 如果没查到数据 、结果为空、此时设置当前页码为1
		}
		// 分页大小
		int pageCount = currentPage.getPageSize();
		Page resultPage = new Page();
		resultPage.setPageNum(pageNum);
		resultPage.setPageCount(pageCount);
		long totalPage = 0;
		if (pageCount != 0) {
			totalPage = total % pageCount == 0 ? (total / pageCount) : (total / pageCount) + 1;
		}
		resultPage.setPageTotal((int) totalPage);
		resultPage.setTotal((int) total);
		resultPage.setList(list);
		return resultPage;
	}

}