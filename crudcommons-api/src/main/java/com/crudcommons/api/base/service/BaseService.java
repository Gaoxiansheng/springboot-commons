package com.crudcommons.api.base.service;

import java.util.List;
import java.util.Map;

import com.crudcommons.api.base.entity.BaseEntity;
import com.crudcommons.api.base.utils.Page;


/**
 * 平台通用Service
 * 
 * @author gaojinxin
 *
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {

	/**
	 * 保存一个实体，null属性也会保存<br/>
	 * --如果基础been属性为空，会帮你初始化基础been
	 */
	public int save(T paramT);
	
	/**
	 * 保存一个实体，null属性也会保存<br/>
	 * --如果基础been属性为空，会帮你初始化基础been
	 */
	public int save(Map<String, Object> paramMap);

	/**
	 * 批量保存实体，null属性也会保存<br/>
	 * --如果基础been属性为空，会帮你初始化基础been
	 */
	public int saveBatch(List<T> paramList);

	/**
	 * 根据主键更新属性不为null的值<br/>
	 * --如果修改时间为空，会帮你新建修改时间
	 */
	public int update(T paramT);
	
	/**
	 * 根据主键更新属性不为null的值<br/>
	 * --如果修改时间为空，会帮你新建修改时间
	 */
	public int update(Map<String, Object> paramMap);

	/**
	 * 批量更新<br/>
	 * 根据主键更新属性不为null的值<br/>
	 * --如果修改时间为空，会帮你新建修改时间
	 */
	public int updateBatch(List<T> paramList);

	/**
	 * 根据主键删除数据<br/>
	 * --此删除为硬删除
	 */
	public int deleteObjectById(String paramK);

	/**
	 * 批量删除<br/>
	 * 根据主键删除数据<br/>
	 * --此删除为硬删除
	 */
	public int deleteAllObjectByIds(List<String> paramList);

	/**
	 * 根据主键获取对象
	 */
	public T getObjectById(String paramK);

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
	public T getObjectByExample(Map<String, Object> map);

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
	public Page<T> getPage(Map<String, Object> paramMap);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> queryList(T param);

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
    public List<T> queryListByExample(Map<String, Object> paramMap);

	/**
	 * 查询全部结果
	 */
    public List<T> queryAll();

	/**
	 * 根据主键删除数据，实则改变数据的状态<br/>
	 * --此删除为软删除
	 */
	public int deletePseudoObjectById(String paramK);

	/**
	 * 批量删除<br/>
	 * 根据主键删除数据，实则改变数据的状态<br/>
	 * --此删除为软删除
	 */
	public int deletePseudoAllObjectByIds(List<String> paramList);

	/**
	 * 获取主键
	 */
	public String generatePk();

}
