package com.thd.base.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.thd.base.model.BaseModel;

@Entity
public class Attachment extends BaseModel {
	/**
	 * 服务器存放的真实文件名称，形式：【文件夹/文件名称（例如：20121108/2012-11-08_160228_263）】
	 */
	@Column(length = 200)
	private String attachmentPath;
	/**
	 * 上传文件的真实文件名称，形式：【abcd.doc】
	 */
	@Column(length = 200)
	private String originalFileName;

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

}
