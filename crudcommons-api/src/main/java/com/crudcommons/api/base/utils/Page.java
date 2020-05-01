package com.crudcommons.api.base.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 平台公共分页
 * 
 * @author gaojinxin
 *
 * @param <T>
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -1714598584877571421L;

	private Integer total; // 总记录数

	private Integer pageTotal; // 总页数

	private Integer pageCount = 10; // 分页记录大小

	private Integer pageNum = 1; // 当前页码

	private List<T> list; // 当前记录实体对象列表

	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Page [total=" + total + ", pageTotal=" + pageTotal + ", pageCount=" + pageCount + ", pageNum=" + pageNum
				+ ", list=" + list + "]";
	}
	
	
}
