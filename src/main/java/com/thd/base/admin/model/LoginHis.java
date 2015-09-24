package com.thd.base.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.thd.base.model.BaseModel;

@Entity
public class LoginHis extends BaseModel {

	// 操作类型
	public enum LoginType {
		LOGIN("登入"), LOGOUT("登出");
		private final String text;

		private LoginType(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	// 登录账号
	@Column(nullable = false, updatable = false)
	private String accountNo;

	// 操作类型（登入、登出）
	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.ORDINAL)
	private LoginType loginType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public String getLoginTypeText() {
		return loginType != null ? loginType.getText() : "";
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

}
