package com.thd.base.page;

import java.util.ArrayList;
import java.util.List;

public class TreeData<T> {
	private int total;
	private List<TreeRow<T>> rows = new ArrayList<TreeRow<T>>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<TreeRow<T>> getRows() {
		return rows;
	}

	public void setRows(List<TreeRow<T>> rows) {
		this.rows = rows;
	}

}
