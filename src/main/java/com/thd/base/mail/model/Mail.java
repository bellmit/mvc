package com.thd.base.mail.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.thd.base.model.BaseModel;

@Entity
public class Mail extends BaseModel {
	/**
	 * 案件编号
	 */
	@Column
	private String caseCode;
	/**
	 * 发送人（accountNo）
	 */
	@Column
	private String mailSender;
	/**
	 * 接收人邮箱（accountNo）
	 */
	@Column
	private String mailReceiver;
	/**
	 * 邮件主题
	 */
	@Column
	private String mailSubject;
	/**
	 * 邮件内容
	 */
	@Column(length = 3000)
	private String mailContents;
	/**
	 * 邮件发送结果（true：成功；false：失败）
	 */
	@Column
	private Boolean sendResultFlg;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String sender) {
		this.mailSender = sender;
	}

	public String getMailReceiver() {
		return mailReceiver;
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContents() {
		return mailContents;
	}

	public void setMailContents(String mailContents) {
		this.mailContents = mailContents;
	}

	public Boolean getSendResultFlg() {
		return sendResultFlg;
	}

	public void setSendResultFlg(Boolean sendResultFlg) {
		this.sendResultFlg = sendResultFlg;
	}

}
