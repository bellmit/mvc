package com.thd.base.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
	private int total;
	private List<Object> rows = new ArrayList<Object>();;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public void addRows(Object o) {
		this.rows.add(o);
	}

}
