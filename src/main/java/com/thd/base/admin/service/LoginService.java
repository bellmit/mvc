package com.thd.base.admin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thd.base.admin.model.LoginHis;
import com.thd.base.admin.model.LoginHis.LoginType;
import com.thd.base.dao.BaseDao;
import com.thd.base.security.util.AuthenticationUtil;

/**
 * 首页的显示
 */
@Service
public class LoginService {

	@Resource
	private BaseDao baseDao;

	/**
	 * 登录/登出日志记录
	 */
	public LoginHis saveLoginHis(LoginType loginType) {
		LoginHis loginHis = new LoginHis();
		loginHis.setAccountNo(AuthenticationUtil.getCurrentUser());
		loginHis.setLoginType(loginType);
		baseDao.saveOrUpdate(loginHis);
		baseDao.flush();
		return loginHis;
	}
}