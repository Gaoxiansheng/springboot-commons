package com.crudcommons.api.base.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;


public class JSONUtil {
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
    /**将JSON字符串转换成MAP
     * @param jsonString JSON字符串
     * @return Map<String, Object>
     */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJsonToMap(String jsonString){
    	return parseJsonToObject(jsonString,Map.class);
    }
    
	/**将JSON字符串转换成对象
	 * @param jsonString JSON字符串
	 * @param c 对象的class
	 * @return 对象
	 */
	public static <T> T parseJsonToObject(String jsonString,Class<T> c){
    	T result = null;
		if (jsonString!=null&&jsonString.length()!=0) {
			result = JSON.parseObject(jsonString,c);
        }
    	return result;
    }
	
	/**将JSON字符串转换成对象
	 * @param jsonString JSON字符串
	 * @param c 对象的class
	 * @param process 自定义属性处理器
	 * @return 对象
	 */
	public static <T> T parseJsonToObject(String jsonString,Class<T> c,ParseProcess process){
    	T result = null;
		if (jsonString!=null&&jsonString.length()!=0) {
			result = JSON.parseObject(jsonString,c,process);
        }
    	return result;
    }
	
	/**将JSON字符串转换成数组对象 
	 * @param jsonString JSON字符串
	 * @param c 对象的class
	 * @return List<T>
	 */
	public static <T> List<T> parseJsonToListBean(String jsonString){
		List<T> result = null;
		if (jsonString!=null&&jsonString.length()!=0) {
			result = JSON.parseObject(jsonString,new TypeReference<List<T>>(){});
        }
    	return result;
    }
	
	/**将JSON字符串转换成List<String>对象
	 * @param jsonString JSON字符串
	 * @return List<String>对象
	 */
	public static List<String> parseJsonToListString(String jsonString){
		List<String> result = null;
		if (jsonString!=null&&jsonString.length()!=0) {
			result = JSON.parseObject(jsonString,new TypeReference<List<String>>(){}); 
        }
    	return result;
    }
	
	/**将JSON字符串转换成List<Map<String,Object>>对象
	 * @param jsonString JSON字符串
	 * @return List<Map<String,Object>>对象
	 */
	public static List<Map<String,Object>> parseJsonToListMap(String jsonString){
		List<Map<String,Object>> result = null;
		if (jsonString!=null&&jsonString.length()!=0) {
			result = JSON.parseObject(jsonString,new TypeReference<List<Map<String,Object>>>(){}); 
        }
    	return result;
    }
	
	/**将JSON字符串转换成字符串数组
	 * @param jsonString JSON字符串
	 * @return 字符串数组
	 * @
	 */
	public static String[] parseJsonToArray(String jsonString){
		String[] result = null;
		if (jsonString!=null&&jsonString.length()!=0) {
			result = JSON.parseObject(jsonString,new TypeReference<String[]>(){}); 
        }
    	return result;
    }
	
	/**将对象转换成JSON
	 * @param o 对象
	 * @return String
	 */
	public static String toJSON(Object o) {
		return JSON.toJSONString(o, new OursValueFilter(null,null,true));
	}
	
	/**将对象转换成JSON
	 * @param o对象
	 * @param dataFormat dataFormat日期格式化
	 * @return JSON字符串
	 * @
	 */
	public static String toJSON(Object o,String dataFormat) {
		return JSON.toJSONString(o, new OursValueFilter(dataFormat,dataFormat,true));
	}

	/**将对象转换成JSON
	 * @param o 对象
	 * @param dateFormat 日期格式化
	 * @param dateTimeFormat 日期时间格式化
	 * @returnJSON字符串
	 * @
	 */
	public static String toJSON(Object o,String dataFormat,String dateTimeFormat) {
		return JSON.toJSONString(o, new OursValueFilter(dataFormat,dateTimeFormat,true));
	}
	
	/**将对象转换成JSON 可以自定义日期格式，以及不需要转换的字段
	 * @param o 需要转换的对象
	 * @param dataFormat 日期格式
	 * @param dateTimeFormat 日期时间格式
	 * @param isIncludeOrExclude 包含或不包含
	 * @param fields 包含或不包含的字段
	 * @return JSON字符串
	 */
	public static String toJSON(Object o,String dataFormat,String dateTimeFormat,boolean isIncludeOrExclude,List<String> fields) {
		OursPropertyFilter filter1 = new OursPropertyFilter(o.getClass(), isIncludeOrExclude,fields);  
		OursValueFilter filter2 = new OursValueFilter(dataFormat,dateTimeFormat,true);
		return JSON.toJSONString(o,new SerializeFilter[]{filter1,filter2});
	}
	
	/**将对象转换成JSON 可以自定义日期格式，以及不需要转换的字段
	 * @param o 需要转换的对象
	 * @param isIncludeOrExclude 包含或不包含
	 * @param fields 包含或不包含的字段
	 * @return JSON字符串
	 */
	public static String toJSON(Object o,boolean isIncludeOrExclude,List<String> fields) {
		OursPropertyFilter filter1 = new OursPropertyFilter(o.getClass(), isIncludeOrExclude,fields);  
		return JSON.toJSONString(o,new SerializeFilter[]{filter1});
	}
	
	/**自定义过滤器的FILTER
	 * @param o 对象
	 * @param filter 过滤器数组
	 * @return String
	 * @
	 */
	public static String toJSON(Object o,SerializeFilter[] filter) {
		return JSON.toJSONString(o, filter);
	}

	/**自定义过滤器的FILTER
	 * @param o 对象
	 * @param filter  过滤器数组
	 * @param features 常见配置常量
	 * @return String
	 */
	public static String toJSON(Object o,SerializeFilter[] filter,SerializerFeature[] features) {
		return JSON.toJSONString(o, filter,features);
	}
	
	/**将MAP转成JAVA对象
	 * @param map Map
	 * @param c JAVA对象的类
	 * @return JAVA对象
	 * @
	 */
	public static <T> T mapToBean(Map<String,Object> map,Class<T> c) {
		if(map==null||c==null){
			return null;
		}
		T t = null;
		JSONObject obj = new JSONObject(map);
		t = JSON.toJavaObject(obj, c);
		return t;
	}
	
	/**将JAVA对象转换成MAP对象
	 * @param o JAVA对象
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> beanToMap(Object o) {
		Map<String,Object> map = ((Map<String,Object>)JSON4FastJSON.toJSON(o));
		return map;
	}
	
	/**将JSONArray转换为 String[]
	 * @param obj 对象
	 * @return String[]
	 */
	public static String[] convert2StringArray(Object obj){
		if(obj instanceof JSONArray){
			JSONArray jArray = (JSONArray)obj;
			return convert2JavaArray(jArray,new String[jArray.size()]);
		}
		return null;
	}
	
	/**将JSONArray转换为 JAVA数组
	 * @param obj JSONArray对象
	 * @param t JAVA数组
	 * @return JAVA数组
	 */
	public static <T> T[] convert2JavaArray(Object obj,T[] t){
		T[] ret = null;
		if(obj instanceof JSONArray){
			JSONArray jArray = (JSONArray)obj;
			ret = jArray.toArray(t);
		}
		return ret;
	}
}

/**对象转JSON字符串的时候，对是否包含某些字段做处理的过滤器
 * @author wangfeng
 *
 */
class OursPropertyFilter implements PropertyPreFilter{
    private final Class<?>    clazz;
    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();

    public OursPropertyFilter(boolean isIncludeOrExclude,List<String> properties){
        this(null, isIncludeOrExclude,properties);
    }

    public OursPropertyFilter(Class<?> clazz,boolean isIncludeOrExclude, List<String> properties){
        super();
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
            	if(isIncludeOrExclude){
            		this.includes.add(item);
            	}else{
            		this.excludes.add(item);
            	}
            }
        }
    }

    /**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @return the includes
	 */
	public Set<String> getIncludes() {
		return includes;
	}

	/**
	 * @return the excludes
	 */
	public Set<String> getExcludes() {
		return excludes;
	}

	/* (non-Javadoc)
     * @see com.alibaba.fastjson.serializer.PropertyPreFilter#apply(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.String)
     */
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }
        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }
        if (this.excludes.contains(name)) {
            return false;
        }
        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }
        return false;
    }
}
/**对象转JSON字符串的时候，对值做处理的过滤器
 * @author wangfeng
 *
 */
class OursValueFilter implements ValueFilter{
	private boolean transLong2String = true;//是否需要将LONG转换成字符串，用于JS，不然JS会有精度丢失
	private String dateFormat = JSONUtil.FORMAT_DATE;//默认的日期时间格式
	private String dateTimeFormat = JSONUtil.FORMAT_DATETIME;//默认的日期格式
	
	/**构造传入1个常用的配置参数
	 * @param needChangeLong 是否需要将LONG转换成字符串，用于JS，不然JS会有精度丢失
	 */
	public OursValueFilter(boolean transLong2String){
		initParam(dateFormat,dateTimeFormat,transLong2String);
	}
	
	/**构造传入三个常用的配置参数
	 * @param dateFormat 日期格式化
	 * @param dateTimeFormat 日期时间格式化
	 * @param transLong2String 是否需要将LONG转换成字符串，用于JS，不然JS会有精度丢失
	 */
	public OursValueFilter(String dateFormat,String dateTimeFormat,boolean transLong2String){
		initParam(dateFormat,dateTimeFormat,transLong2String);
	}
	
	/**初始化参数
	 * @param dateFormat 日期格式化
	 * @param dateTimeFormat 日期时间格式化
	 * @param transLong2String 是否需要将LONG转换成字符串，用于JS，不然JS会有精度丢失
	 */
	private void initParam(String dateFormat,String dateTimeFormat,boolean transLong2String){
		if(dateFormat!=null){
			this.dateFormat = dateFormat;
		}
		if(dateTimeFormat!=null){
			this.dateTimeFormat = dateTimeFormat;
		}
		this.transLong2String = transLong2String;	
	}
	
	/* (non-Javadoc)
	 * @see com.alibaba.fastjson.serializer.ValueFilter#process(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public Object process(Object object, String name, Object value) {
		if(value instanceof java.sql.Timestamp){
			java.sql.Timestamp d = (java.sql.Timestamp)value;
			value = DateUtil.getDate(d, dateTimeFormat);
		}else if(value instanceof java.util.Date){
			java.util.Date d = (java.util.Date)value;
			value = DateUtil.getDate(d,dateFormat);
		}else if(value instanceof Long){//JS中数字位超过16就会出错，所以使用字符串型
			if(transLong2String){
				value = value.toString();
			}
		}
		return value;
	}
}
