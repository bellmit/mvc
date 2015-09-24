package com.thd.param.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thd.base.util.LoggerUtil;
import com.thd.param.model.Param;
import com.thd.param.model.Param.ParamType;
import com.thd.param.service.ParamService;

public class ShowParamSelectorTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	Logger logger = LoggerUtil.getLogger();
	/**
	 * 控件ID
	 * */
	private String itemId;
	/**
	 * 控件NAME
	 * */
	private String itemName;
	/**
	 * 是否可编辑
	 * */
	private Boolean disabled = Boolean.FALSE;
	/**
	 * 是否为必输项
	 * */
	private Boolean required = Boolean.FALSE;
	/**
	 * 参数类型
	 * */
	private ParamType type;
	private int paramType;

	/**
	 * 选择值
	 * */
	private String selectedValue;

	/**
	 * 是否添加（--请选择--）选项
	 * */
	private Boolean isHaveHead = Boolean.TRUE;

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext
				.getServletContext());
		ParamService paramService = (ParamService) context.getBean("paramService");
		StringBuffer strBuf = new StringBuffer("<select");
		//selector头部设置
		if (!StringUtils.isEmpty(itemId)) {
			strBuf.append(" id=\"" + itemId + "\"");
		}
		if (!StringUtils.isEmpty(itemName)) {
			strBuf.append(" name=\"" + itemName + "\"");
		}
		strBuf.append(" class=\"easyui-validatebox\" editable=\"false\"");
		if (disabled) {
			strBuf.append(" disabled=disabled");
		}
		//		if (required && !StringUtils.isEmpty(itemId)) {
		//			strBuf.append(" required=\"true\" validType=\"selectValueRequired['#" + itemId + "']\"");
		//		}
		if (required && !StringUtils.isEmpty(itemId)) {
			strBuf.append(" required=\"true\" ");
		}
		strBuf.append(">");
		if (isHaveHead) {
			strBuf.append("<option value=\"\">---请选择---</option>");
		}
		//selector内容设置
		List<Param> paramList = paramService.getParamList(type, true);
		if (paramList != null && paramList.size() > 0) {
			for (Param param : paramList) {
				if (!StringUtils.isEmpty(selectedValue) && selectedValue.equals(param.getValue())) {
					strBuf.append("<option value=\"" + param.getValue() + "\" selected>" + param.getText()
							+ "</option>");
				} else {
					strBuf.append("<option value=\"" + param.getValue() + "\">" + param.getText() + "</option>");
				}
			}
		}
		strBuf.append("</select>");
		try {
			out.print(strBuf.toString());
		} catch (IOException e) {
			logger.error("TAG生成错误！ID=" + itemId + "  NAME=" + itemName);
			return SKIP_BODY;
		}
		return SKIP_BODY;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
		setType(paramType);
	}

	private void setType(int paramType) {
		ParamType[] paramTypes = ParamType.values();
		for (ParamType type : paramTypes) {
			if (type.ordinal() == paramType) {
				this.type = type;
				return;
			}
		}
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public Boolean getIsHaveHead() {
		return isHaveHead;
	}

	public void setIsHaveHead(Boolean isHaveHead) {
		this.isHaveHead = isHaveHead;
	}

}
