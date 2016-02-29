package com.thd.base.security.deng;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 人员详细信息过滤器
 *
 * @author ELF@TEAM
 * @since 2016年2月24日16:52:39
 */
public class ELFUserDetailsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        // 获得用户登录名称
        Authentication authentication = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (null != context) {
            authentication = context.getAuthentication();
        }

        String loginName = null;
        if (authentication != null) {
            loginName = authentication.getName();
        }

        if (loginName != null && !loginName.equals("") && !loginName.equals("anonymousUser")) {
            System.out.println(loginName);
            String corpCode = (String) session.getAttribute("corpCode");
            System.out.println(corpCode);
        }

        // 放行到目的地址
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
