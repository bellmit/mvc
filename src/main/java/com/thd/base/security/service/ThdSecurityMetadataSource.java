package com.thd.base.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.Jsr250SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.thd.base.security.model.ResourceDetails;

public class ThdSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private boolean convertUrlToLowercaseBeforeComparison = false;// 是否需要把Url转为小写后再进行比较

	private boolean useAntPath = true;// 是否选用ant path的匹配模式

	private boolean protectAllResource = false;// 是否默认情况下所有的资源都需要受保护

	private final PathMatcher pathMatcher = new AntPathMatcher();

	private final PatternMatcher matcher = new Perl5Matcher();

	private ThdUserDetailService userDetailsService;

	public Collection<ConfigAttribute> getAttributes(Object obj) {
		if (obj instanceof String) {
			return lookupAttributes((String) obj);
		} else {
			FilterInvocation filterInvocation = (FilterInvocation) obj;
			String requestUrl = filterInvocation.getRequestUrl();
			return lookupAttributes(requestUrl);
		}
	}

	public void setUserDetailsService(ThdUserDetailService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * 返回url对应的权限(Roles)
	 */
	public Collection<ConfigAttribute> lookupAttributes(String url) {
		url = preDealUrl(url, isUseAntPath(), isConvertUrlToLowercaseBeforeComparison());

		// 受保护URL权限信息取得
		List<ResourceDetails> urlsRescs = userDetailsService.findAuthority();
		if (urlsRescs == null) {
			return null;
		}
		orderUrls(urlsRescs);
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();//SpringSecurity的角色集合

		// 判断所要访问的URL是否在受保护URL中
		for (ResourceDetails res : urlsRescs) {
			if (isResourceMatch(isUseAntPath(), url, res.getResString())) {
				authorities.add(res.getAuthoritie());
				break;
			}
		}

		// 如果在受保护URL中，则返回权限信息，否则，通过
		return getCadByAuthorities(authorities, isProtectAllResource());
	}

	/**
	 * 把url资源按倒序排序
	 * 
	 * @param urls
	 */
	@SuppressWarnings("unchecked")
	private void orderUrls(List urls) {
		Collections.sort(urls);
		Collections.reverse(urls);
	}

	/**
	 * 根据是否使用UseAntPath和是否字符小写化来预先格式化url
	 * 
	 * @param url
	 * @param isUseAntPath
	 * @param isToLowercase
	 * @return
	 */
	private String preDealUrl(String url, boolean isUseAntPath, boolean isToLowercase) {
		if (isUseAntPath) {
			int firstQuestionMarkIndex = url.lastIndexOf("?");
			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}
		if (isToLowercase) {
			url = url.toLowerCase();
		}
		return url;
	}

	/**
	 * 查看当前url和资源中的url是否匹配
	 * @param isUseAntPath
	 * @param runningUrl
	 * @param rescUrl
	 * @return
	 */
	private boolean isResourceMatch(Boolean isUseAntPath, String runningUrl, String rescUrl) {
		if (isUseAntPath.booleanValue()) {
			return pathMatcher.match(rescUrl, runningUrl);
		} else {
			Pattern compiledPattern;
			Perl5Compiler compiler = new Perl5Compiler();
			try {
				compiledPattern = compiler.compile(rescUrl, Perl5Compiler.READ_ONLY_MASK);
			} catch (Exception mpe) {
				throw new IllegalArgumentException("Malformed regular expression: " + rescUrl);
			}
			return matcher.matches(runningUrl, compiledPattern);
		}
	}

	public Collection<ConfigAttribute> getConfigAttributeDefinitions() {
		return null;
	}

	public void setConvertUrlToLowercaseBeforeComparison(boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return convertUrlToLowercaseBeforeComparison;
	}

	public boolean isUseAntPath() {
		return useAntPath;
	}

	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	/**
	 * 是否默认情况下所有的资源都需要受保护
	 * @param protectAllResource
	 */
	public void setProtectAllResource(boolean protectAllResource) {
		this.protectAllResource = protectAllResource;
	}

	/**
	 * 是否默认情况下所有的资源都需要受保护
	 * @return
	 */
	public boolean isProtectAllResource() {
		return protectAllResource;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	/**
	 * 把权限组转为 Collection<ConfigAttribute>
	 * @param authorities   权限
	 * @param isProtectAllResource 是否保护所有资源，true，则所有资源默认为受保护， false则只有声明了并且与权限挂钩了的资源才会受保护
	 * @return
	 */
	public static Collection<ConfigAttribute> getCadByAuthorities(Collection<GrantedAuthority> authorities,
			boolean isProtectAllResource) {
		Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		if (authorities == null || authorities.size() == 0) {
			if (isProtectAllResource) {
				ConfigAttribute configAttribute = Jsr250SecurityConfig.DENY_ALL_ATTRIBUTE;
				configAttributes.add(configAttribute);
				return configAttributes;
			} else {
				ConfigAttribute configAttribute = Jsr250SecurityConfig.PERMIT_ALL_ATTRIBUTE;
				configAttributes.add(configAttribute);
				return configAttributes;
			}
		}
		for (GrantedAuthority authority : authorities) {
			ConfigAttribute configAttribute = new SecurityConfig(authority.getAuthority());
			configAttributes.add(configAttribute);
		}
		return configAttributes;
	}

}
