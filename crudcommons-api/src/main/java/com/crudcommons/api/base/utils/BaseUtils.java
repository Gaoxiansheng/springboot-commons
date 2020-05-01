package com.crudcommons.api.base.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.crudcommons.api.base.entity.BaseEntity;

/**
 * 
 * 提供项目基础功能的封装<br/>
 * 集中管理对第三方类的依赖、最大化的减少业务相关的类直接import第三方class<br/>
 * 如需更多方法 需import其他工具类
 * 
 * @author gaojinxin
 *
 */
public class BaseUtils {

    /**将JSON字符串转换成MAP
     * @param jsonString JSON字符串
     * @return Map<String, Object>
     */
	public static Map<String, Object> jsonToMap(String jsonString){
    	return JSONUtil.parseJsonToMap(jsonString);
    }
	
	/**将JSON字符串转换成对象
	 * @param jsonString JSON字符串
	 * @param c 对象的class
	 * @return 对象
	 */
	public static <T> T jsonToObject(String jsonString,Class<T> c){
		return JSONUtil.parseJsonToObject(jsonString,c);
	}
	
	/**将对象转换成JSON
	 * @param o 对象
	 * @return String
	 */
	public static String objectToJSON(Object o){
		return JSONUtil.toJSON(o);
	}
	
	/**将对象转换成JSON
	 * @param o对象
	 * @param dataFormat dataFormat日期格式化
	 * @return JSON字符串
	 */
	public static String objectToJSON(Object o,String dataFormat){
		return JSONUtil.toJSON(o,dataFormat);
	}
	
	/**将MAP转成JAVA对象
	 * @param map Map
	 * @param c JAVA对象的类
	 * @return JAVA对象
	 */
	public static <T> T mapToBean(Map<String,Object> map,Class<T> c){
		return JSONUtil.mapToBean(map,c);
	}
	
	/**将JAVA对象转换成MAP对象
	 * @param o JAVA对象
	 * @return map
	 */
	public static Map<String,Object> beanToMap(Object o){
		return JSONUtil.beanToMap(o);
	}
	
	/**判断对象是否为空、适用于集合、自定义Object、String
	 * @param o JAVA对象
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Collection<?>) {
			Collection<?> coll = (Collection<?>) obj;
			return coll.size() > 0;
		}
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) obj;
			return map.size() > 0;
		}
		return String.valueOf(obj + "").trim().length() > 0;
	}
	
	/**自动生成ID
	 * @return String
	 */
	public static String getUUID() {
		return UUIDUtil.getUUID();
	}
	
	/**将source对象的属性值赋值给target对象
	 * @param source JAVA对象
	 * @param target JAVA对象
	 */
	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}
	
	/**将sourceList集合中的对象属性值赋值给target对象，并返回一个新的target集合
	 * @param sourceList JAVA对象集合
	 * @param targetType JAVA对象
	 * @return List<K>
	 */
	public static <K> List<K> copyProperties(List<?> sourceList, Class<K> targetType) {
		List<K> targetList = new ArrayList<K>();
		for (Object source : sourceList) {
			try {
				K k = targetType.newInstance();
				copyProperties(source, k);
				targetList.add(k);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return targetList;
	}
	
	/**
	 * 初始化基础been（可根据业务进行拓展）
	 * @param baseEntity
	 * @param generatePK 是否自动生成主键
	 */
	public void initBaseEntity(BaseEntity baseEntity, boolean generatePK) {
		if (generatePK && baseEntity.getId() == null) {
			baseEntity.setId(getUUID());
		}
		Date currentDate = new Date();
		if (baseEntity.getCreateTime() == null) {
			baseEntity.setCreateTime(currentDate);
		}
		if (baseEntity.getUpdateTime() == null) {
			baseEntity.setUpdateTime(currentDate);
		}
	}
	
	/**
	 * 更新时初始化基础been（可根据业务进行拓展）
	 * @param baseEntity
	 */
	public void initBaseEntityForUpdate(BaseEntity baseEntity) {
		if (baseEntity.getUpdateTime() == null) {
			baseEntity.setUpdateTime(new Date());
		}
	}
}
