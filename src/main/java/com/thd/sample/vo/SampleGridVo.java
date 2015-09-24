package com.thd.sample.vo;

import java.util.ArrayList;
import java.util.List;

import com.thd.sample.model.SampleTest1;

public class SampleGridVo {
	private int total;
	private List<SampleTest1> rows = new ArrayList<SampleTest1>();;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<SampleTest1> getRows() {
		return rows;
	}

	public void setRows(List<SampleTest1> rows) {
		this.rows = rows;
	}

	public void addRows(SampleTest1 sampleTest1) {
		this.rows.add(sampleTest1);
	}

}
