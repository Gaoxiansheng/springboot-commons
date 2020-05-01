package com.crudcommons.api.base.utils;

/**
 * 返回前台结果
 */
public class ApiResultModel {

	/**
	 * 返回数据集
	 */
	private Object result;
	/**
	 * 返回码
	 */
	private Integer code = 100;
	/**
	 * 返回信息
	 */
	private String msg;

	public ApiResultModel(Object result) {
		this.setCode(100);
		this.setMsg("处理成功");
		this.result = result;
	}

	public ApiResultModel(Object result, Integer code, String msg) {
		this.result = result;
		this.setCode(code);
		this.msg = msg;
	}

	public ApiResultModel() {
		this.setCode(100);
		this.setMsg("处理成功");
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
