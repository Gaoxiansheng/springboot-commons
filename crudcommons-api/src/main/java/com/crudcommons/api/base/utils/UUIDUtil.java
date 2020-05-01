package com.crudcommons.api.base.utils;

import java.util.UUID;

/**
 * @author macx
 * 产生UUID的工具类
 */
public class UUIDUtil {
	/*
	 * 生成一个long型的UUID。
	 */
	public static long getUUIDLong() {
		//返回此 UUID 的 128 位值中的最高有效 64 位
		//return UUID.randomUUID().getMostSignificantBits();
		//返回此 UUID 的 128 位值中的最低有效 64 位
		return Math.abs(UUID.randomUUID().getLeastSignificantBits());
	}

	/*
	 * 生成一个long型的UUID的绝对值。
	 */
	public static long getAbsUUIDLong() {
		return Math.abs(getUUIDLong());
	}
	
	/*
	 * 生成一个String型的UUID。
	 */
	public static String getUUIDString() {
		return UUID.randomUUID().toString();
	}

	/*
	 * 生成一个long型的UUID的绝对值。
	 */
	public static String getAbsUUIDString() {
		return Math.abs(getUUIDLong())+"";
	}
	
	/** 实例化UUID */
	private UUID uuID = UUID.randomUUID();
	
	/**
	 * 实例化UUID String
	 * @return String
	 */
	public String getInstanceUUIDString(){
	    return uuID.toString();
	}
	
	/**
	 * 实例化UUID的long型
	 * @return long
	 */
	public long getInstanceUUIDLong(){
        return uuID.getMostSignificantBits();
    }
	
    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
