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

public class ThdUserDetailsFilter implements Filter {

	private SystemService systemService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String userServiceName = filterConfig.getInitParameter("userService");// 用户Service名称
		System.out.println(userServiceName);
		String systemServiceName = filterConfig.getInitParameter("systemServiceName_1");// 用户Service名称
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig
				.getServletContext());
		systemService = (SystemService) wac.getBean(systemServiceName);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
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
					//userDetails.setUserIp(getIpAddr(httpRequest));

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
		filterChain.doFilter(request, response);

	}

	/**
	 * 单元测试要用
	 * @param systemService
	 */
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
