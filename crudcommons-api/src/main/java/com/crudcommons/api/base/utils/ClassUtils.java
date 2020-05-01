package com.crudcommons.api.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射相关的工具类
 * @author gaojinxin
 *
 */
public class ClassUtils {

	/**
	 * 获取指定class的泛型的Class
	 * @param clazz
	 * @param index  泛型的第几个参数, 从0开始
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> clazz, int index) {
		if (clazz == null) {
			return null;
		}
		// 接口
		if (clazz.isInterface()) {
			Type[] generic = clazz.getGenericInterfaces();
			if (generic.length <= index) {
				return null;
			} else {
				if (generic[index] instanceof ParameterizedType) {
					try {
						Type[] tmp = ((ParameterizedType) generic[index]).getActualTypeArguments();
						return Class.forName(tmp[0].getTypeName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return null;
				}
			}
			return generic[index].getClass();
		} else {// 实现类
			Type genType = clazz.getGenericSuperclass();// 得到泛型父类
			if (genType instanceof ParameterizedType == false) {
				return getGenericClass(clazz.getSuperclass(), index);
			}
			Type[] generic = ((ParameterizedType) genType).getActualTypeArguments(); // 获取泛型参数
			try {
				return Class.forName(generic[index].getTypeName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 获取字段的type
	 * 
	 * @param field
	 * @param index
	 * @return
	 */
	public static Class<?> getGenericClass(Field field, int index) {
		if (field == null) {
			return null;
		}
		Type genType = field.getGenericType();// 得到泛型父类
		try {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); // 获取泛型参数
			return Class.forName(params[index].getTypeName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取对象的字段值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Object value = null;
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			value = field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 获取对象的字段值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static Field getField(final Class<?> cls, String fieldName) {
		for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
			try {
				Field field = acls.getDeclaredField(fieldName);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
					return field;
				}
			} catch (NoSuchFieldException ex) {
				// ignore
			}
		}
		return null;
	}
	public static void writeDeclaredField(Object target, String fieldName, Object value)
			throws IllegalAccessException {
		if (target == null) {
			throw new IllegalArgumentException("target object must not be null");
		}
		Class<?> cls = target.getClass();
		Field field = getField(cls, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
		}
		field.set(target, value);
	}

}
