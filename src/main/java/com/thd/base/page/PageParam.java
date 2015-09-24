package com.thd.base.page;

public class PageParam {
	public enum OrderDir {
		asc, desc
	}

	@SuppressWarnings("unused")
	private int start = 0;
	private int page = 0;
	private int rows;
	private String sortBy;
	private OrderDir sortDir;

	public int getStart() {
		return (this.page - 1) * this.rows;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public OrderDir getSortDir() {
		return sortDir;
	}

	public void setSortDir(OrderDir sortDir) {
		this.sortDir = sortDir;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		this.start = (this.page - 1) * this.rows;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}