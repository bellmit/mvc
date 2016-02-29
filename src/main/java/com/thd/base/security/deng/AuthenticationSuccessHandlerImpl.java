package com.thd.base.security.deng;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的处理类
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private Log LOG = LogFactory.getLog(AuthenticationFailureHandlerImpl.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
            throws IOException, ServletException {
        LOG.info("********** AuthenticationSuccessHandler **********");
        response.sendRedirect(request.getContextPath() + "/loginSuccessHandler.jsp");
    }
}
