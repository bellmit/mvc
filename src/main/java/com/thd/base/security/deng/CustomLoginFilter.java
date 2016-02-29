package com.thd.base.security.deng;


import com.thd.base.security.deng.exception.CustomAuthenticationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private Log LOG = LogFactory.getLog(CustomLoginFilter.class);

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        HttpSession session = request.getSession();

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        //String username = obtainUsername(request).toUpperCase().trim();
        String username = obtainUsername(request).trim();
        String password = obtainPassword(request);
        String corpCode = obtainCorpCode(request);

        if (!"default".equals(corpCode)) {
            try {
                request.setAttribute("msg", "公司不存在!");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return (Authentication) new CustomAuthenticationException("公司不存在!");
            } catch (IOException e) {
                LOG.error("To login error!");
            }
        }

        session.setAttribute("corpCode", corpCode);
        UsernamePasswordAuthenticationToken authRequest = new CustomAuthenticationToken(username, password, corpCode);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainCorpCode(HttpServletRequest request) {
        String corpCodeParameter = "corpCode";
        return request.getParameter(corpCodeParameter);
    }
}
