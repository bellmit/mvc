/**
 * Copyright : E-MA Logistics System , 2007-2008 Project : PEPP Created By : <a
 * href="mailto:yqli@e-ma.net">vincent</a> $$Id: Resource.java 2177 2009-03-27
 * 10:31:24Z vincent $$ $$Revision: 2177 $$ Last Changed by $$Author: vincent $$
 * at $$Date: 2009-03-27 18:31:24 +0800 $$ $$URL:
 * http://192.168.0.203/eplatform/
 * dev/trunk/uaas/src/com/ema/uaas/springsecurity/resource/Resource.java $$
 * 
 * Change Log Author Change Date Comments
 */
package com.thd.base.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * ResourceDetails的实现类 
 * resString资源串， 如Url资源串 /admin/index.jsp, 
 * Method资源串 com.abc.service.userManager.save 等 
 * resType资源类型，如URL, METHOD 等 
 * authorities该资源所拥有的权限
 */
@SuppressWarnings("unchecked")
public class SecurityResource implements ResourceDetails, Comparable {

	private static final long serialVersionUID = 4640497640533200574L;

	public static final String RESOURCE_TYPE_URL = "URL";

	public static final String RESOURCE_TYPE_METHOD = "METHOD";

	public static final String RESOURCE_TYPE_TAG = "TAG";

	private String resString;

	private String resType;

	private GrantedAuthority authoritie;

	public SecurityResource(String resString, String resType, GrantedAuthority authoritie) {
		Assert.notNull(resString, "Cannot pass null or empty values to resource string");
		Assert.notNull(resType, "Cannot pass null or empty values to resource type");
		this.resString = resString;
		this.resType = resType;
		this.authoritie = authoritie;
	}

	@Override
	public boolean equals(Object rhs) {
		if (!(rhs instanceof SecurityResource)) {
			return false;
		}
		SecurityResource resauth = (SecurityResource) rhs;
		if (!getResString().equals(resauth.getResString())) {
			return false;
		}
		if (!getResType().equals(resauth.getResType())) {
			return false;
		}
		if (!getAuthoritie().equals(resauth.getAuthoritie())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int code = 168;
		if (getAuthoritie() != null) {
			code *= getAuthoritie().hashCode() % 7;
		}
		if (getResString() != null) {
			code *= getResString().hashCode() % 7;
		}
		return code;
	}

	public String getResString() {
		return resString;
	}

	public void setResString(String resString) {
		this.resString = resString;
	}

	public GrantedAuthority getAuthoritie() {
		return authoritie;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public void setAuthoritie(GrantedAuthority authoritie) {
		Assert.notNull(authoritie, "Granted authority is null");
		this.authoritie = authoritie;
	}

	public int compareTo(Object o) {
		return this.getResString().compareTo(((SecurityResource) o).getResString());
	}
}
