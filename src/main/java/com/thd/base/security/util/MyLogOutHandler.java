package com.thd.base.security.util;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.thd.base.admin.model.LoginHis.LoginType;
import com.thd.base.admin.service.LoginService;

public class MyLogOutHandler implements LogoutHandler {
	Logger logger = Logger.getLogger(MyLogOutHandler.class);
	@Resource
	private LoginService loginService;

	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		// TODO Auto-generated method stub
		if (authentication != null && authentication.getName() != null
				&& request.getSession().getAttribute(authentication.getName()) != null) {
			loginService.saveLoginHis(LoginType.LOGOUT);
		}
		request.getSession().invalidate();
		logger.info("用户注销!");
	}

}
