/**
 * Copyright : E-MA Logistics System , 2007-2008 Project : PEPP Created By : <a
 * href="mailto:yqli@e-ma.net">vincent</a> $$Id: ResourceDetails.java 2177
 * 2009-03-27 10:31:24Z vincent $$ $$Revision: 2177 $$ Last Changed by $$Author:
 * vincent $$ at $$Date: 2009-03-27 18:31:24 +0800 $$ $$URL:
 * http://192.168.0.203
 * /eplatform/dev/trunk/uaas/src/com/ema/uaas/springsecurity/
 * resource/ResourceDetails.java $$
 * 
 * Change Log Author Change Date Comments
 */
package com.thd.base.security.model;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

/**
 * 提供资源信息
 */
public interface ResourceDetails extends Serializable {

	/**
	 * 资源串
	 */
	public String getResString();

	/**
	 * 资源类型,如URL,FUNCTION
	 */
	public String getResType();

	/**
	 * 返回属于该resource的authorities
	 */
	public GrantedAuthority getAuthoritie();

	public void setAuthoritie(GrantedAuthority authoritie);
}
