package com.thd.system.vo;

import java.util.ArrayList;
import java.util.List;

public class TreeVo {
	private String id;

	private String text;

	private String value;

	private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    List<TreeVo> children = new ArrayList<TreeVo>();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<TreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
