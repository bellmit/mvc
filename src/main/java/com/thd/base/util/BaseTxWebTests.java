package com.thd.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.thd.base.dao.BaseDao;
import com.thd.base.security.util.AuthenticationUtil;
import com.thd.base.security.util.MyUserDetails;

@ContextConfiguration(locations = { "classpath:/spring-xml/spring-servlet.xml",
		"classpath:/spring-xml/spring-context.xml", "classpath:/spring-xml/spring-security.xml" })
public class BaseTxWebTests extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	protected BaseDao baseDao;
	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;
	protected MockHttpSession session;
	protected MockServletContext servletContext;
	protected MockServletConfig servletConfig;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public BaseTxWebTests() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		servletContext = new MockServletContext();
		servletConfig = new MockServletConfig();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	/**
	 * 设置当前用户身份及其是否属于案件处理部门
	 */
	protected void setCurrentUserAsCaseHandler(String userName) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName, ""));

		MyUserDetails myUserDetails = new MyUserDetails();
		myUserDetails.setOperAccountNo(userName);
		AuthenticationUtil.setMyUserDetails(myUserDetails);

	}

}