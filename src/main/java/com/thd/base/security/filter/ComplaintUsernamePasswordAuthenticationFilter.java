package com.thd.base.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security的登陆验证,重写attemptAuthentication方法校验用户登陆
 * 
 * @author dell
 * 
 */
public class ComplaintUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		 Object objUname = request.getParameter("username");
		 String uname="";
		 if(objUname!=null){
			 uname = objUname.toString();
		 }
 
		 Object objPwd = request.getParameter("password");
		 String pwd="";
		 if(objUname!=null){
			 pwd = objPwd.toString();
		 }
		 
		 uname = uname.trim();//去掉空格
		 
		 //根据用户名查询用户信息
		 //TODO 这里写死用户名为admin
		 
		 if(uname.equals("admin") && pwd.equals("admin")){
			 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(uname, pwd);
			// 允许子类设置详细属性 
			 setDetails(request, authToken);
			// 运行UserDetailsService的loadUserByUsername 再次封装Authentication  
			 return this.getAuthenticationManager().authenticate(authToken);
		 }else{
			 logger.warn("用户名或者密码错误！");
			 throw new AuthenticationServiceException("用户名或者密码错误！");   
		 }

	}
}
