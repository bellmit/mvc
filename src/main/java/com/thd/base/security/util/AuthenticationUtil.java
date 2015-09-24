package com.thd.base.security.util;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.thd.base.util.LoggerUtil;
import com.thd.system.model.Organization;

/**
 * 此工具类可以方便获取当前用户信息、所属机构、权限等
 */
public class AuthenticationUtil {
	private static final ThreadLocal<MyUserDetails> MyUserDetailsHolder = new ThreadLocal<MyUserDetails>();

	Logger logger = LoggerUtil.getLogger();
	private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	private AccessDecisionManager accessDecisionManager;

	/**
	 * 清空ThreadLocal（单元测试用）
	 */
	public static void clearThreadLocal() {
		MyUserDetailsHolder.remove();
	}

	/**
	 * 取得当前用户标识（登录名称）
	 */
	public static String getCurrentUser() {
		Authentication currentUser = getCurrentUserAuthentication();
		if (currentUser != null) {
			return currentUser.getName();
		}

		return null;
	}

	/**
	 * 取得当前用户Model
	 */
	public static MyUserDetails getMyUserDetails() {
		return MyUserDetailsHolder.get();
	}

	/**
	 * 设置当前用户Model
	 * @param myUserDetails
	 */
	public static void setMyUserDetails(MyUserDetails myUserDetails) {
		MyUserDetailsHolder.set(myUserDetails);
	}

	/**
	 * 取得当前用户的所属机构列表
	 * @return List<Organization>
	 */
	public static List<Organization> getDepartments() {
		List<Organization> result = null;
		MyUserDetails myUserDetails = getMyUserDetails();
		if (myUserDetails != null) {
			result = myUserDetails.getOrganizations();
		}
		return result;
	}

	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static Authentication getCurrentUserAuthentication() {
		Authentication currentUser = null;

		SecurityContext context = SecurityContextHolder.getContext();

		if (null != context) {
			currentUser = context.getAuthentication();
		}
		return currentUser;
	}

	/**
	 * 获取登录用户的权限
	 * @return
	 */
	public static Collection<? extends GrantedAuthority> getGrantedAuthority() {
		Authentication currentUser = AuthenticationUtil.getCurrentUserAuthentication();
		if (currentUser != null) {
			return currentUser.getAuthorities();
		}
		return null;
	}

	/**
	 * 根据URL，判断当前用户是否拥有访问权限
	 * @return boolean
	 */
	public boolean accessibleTo(String accessPattern) {
		Authentication userAuthentication = getCurrentUserAuthentication();

		Collection<ConfigAttribute> configAttributeDefinition = filterInvocationSecurityMetadataSource
				.getAttributes(accessPattern);

		if (configAttributeDefinition == null) {
			return true;
		}

		try {
			accessDecisionManager.decide(userAuthentication, userAuthentication, configAttributeDefinition);
			return true;
		} catch (InsufficientAuthenticationException e) {
			logger.debug(e.getMessage(), e);
			return false;
		} catch (AccessDeniedException e) {
			logger.debug(e.getMessage());
			return false;
		}
	}

	public FilterInvocationSecurityMetadataSource getFilterInvocationSecurityMetadataSource() {
		return filterInvocationSecurityMetadataSource;
	}

	public void setFilterInvocationSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
		this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
	}

	public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
		this.accessDecisionManager = accessDecisionManager;
	}
}