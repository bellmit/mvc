package com.thd.base.mail.vo;

import java.util.ArrayList;
import java.util.List;

public class MailVo {
	private String caseCode;
	private String mailReceiver;
	private Long nodeTaskId;
	private String mailSender;
	private String mailSubject;
	private String mailContents;
	private Boolean sendResultFlg;
	private String mailHref;
	private List<String> mailReceiverAddresses = new ArrayList<String>();
	private List<String> mailReceivers = new ArrayList<String>();

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
		this.mailSubject = "案件【" + caseCode + "】";
	}

	public String getMailReceiver() {
		return mailReceiver;
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public Long getNodeTaskId() {
		return nodeTaskId;
	}

	public void setNodeTaskId(Long nodeTaskId) {
		this.nodeTaskId = nodeTaskId;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String userNameFrom) {
		this.mailSender = userNameFrom;
	}

	public List<String> getMailReceivers() {
		return mailReceivers;
	}

	public void setMailReceivers(List<String> userNameToArray) {
		this.mailReceivers = userNameToArray;
	}

	public void addMailReceiver(String userName) {
		mailReceivers.add(userName);
	}

	public String getMailContents() {
		return mailContents;
	}

	public void setMailContents(String memo) {
		this.mailContents = memo;
	}

	public String getMailHref() {
		return mailHref;
	}

	public void setMailHref(String mailHref) {
		this.mailHref = mailHref;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String subject) {
		this.mailSubject = subject;
	}

	public String[] getMailReceiverAddresses() {
		return mailReceiverAddresses.toArray(new String[mailReceiverAddresses.size()]);
	}

	public void setMailReceiverAddresses(List<String> mailAddressToArray) {
		this.mailReceiverAddresses = mailAddressToArray;
	}

	public void addMailAddressTo(String mailAddress) {
		mailReceiverAddresses.add(mailAddress);
	}

	public Boolean getSendResultFlg() {
		return sendResultFlg;
	}

	public void setSendResultFlg(Boolean sendResultFlg) {
		this.sendResultFlg = sendResultFlg;
	}

}
