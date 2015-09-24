package com.thd.base.security.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thd.system.model.Operator;
import com.thd.system.model.Organization;
import com.thd.system.service.SystemService;

public class MyUserDetailsSetFilter implements Filter {

	private SystemService systemService;

	public void init(FilterConfig filterConfig) throws ServletException {
		String systemServiceName = filterConfig.getInitParameter("systemServiceName_1");// 用户Service名称

		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig
				.getServletContext());

		systemService = (SystemService) wac.getBean(systemServiceName);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		// 获得用户登录名称
		String username = AuthenticationUtil.getCurrentUser();
		if (username != null && !username.equals("") && !username.equals("anonymousUser")) {
			// 登录的场合
			MyUserDetails userDetails = null;
			Object obj = session.getAttribute(username);

			if (obj == null) {
				// 登录第一次访问的场合

				// 根据登录名称查询用户详细信息
				List<Operator> operList = systemService.getOperByAccount(username);
				if (operList != null && operList.size() == 1) {
					Operator operator = operList.get(0);
					userDetails = new MyUserDetails();

					userDetails.setOperAccountNo(operator.getOperAccountNo());
					userDetails.setOperName(operator.getOperName());
					userDetails.setOperGender(operator.getOperGender());
					userDetails.setOperMail(operator.getOperMail());
					userDetails.setOperPhone(operator.getOperPhone());
					userDetails.setOperType(operator.getOperType());
					userDetails.setPositionType(operator.getPositionType());
					userDetails.setUserIp(getIpAddr(httpRequest));

					// 机构设置
					if (operator.getOrganizations() != null && operator.getOrganizations().size() > 0) {
						List<Organization> orgList = new ArrayList<Organization>();
						for (Organization org : operator.getOrganizations()) {
							Organization organization = new Organization();
							organization.setId(org.getId());
							organization.setOrgName(org.getOrgName());
							organization.setOrgAddress(org.getOrgAddress());
							organization.setOrgMemo(org.getOrgMemo());
							orgList.add(organization);
						}
						userDetails.setOrganizations(orgList);
					}
					session.setAttribute(username, userDetails);
				}
			} else {
				userDetails = (MyUserDetails) obj;
			}

			AuthenticationUtil.setMyUserDetails(userDetails);
		}

		// 放行到目的地址
		chain.doFilter(request, response);

	}

	public void destroy() {
	}

	//	public void setEmployeeService(EmployeeService employeeService) {
	//		this.employeeService = employeeService;
	//	}

	/**
	 * 获得访问IP
	 * @return ip
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
