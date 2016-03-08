package com.thd.base.security.deng.tag;

import com.thd.base.security.util.AuthenticationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HasNoRightToDoTag extends TagSupport {
    private static final long serialVersionUID = 8205863198180130610L;

    private static final String URL_DIVIDER = ",";

    private String url;

    Log log = LogFactory.getLog(HasNoRightToDoTag.class);

    private static WebApplicationContext wac;

    private static AuthenticationUtil authenticationUtil;

    @Override
    public int doStartTag() throws JspException {
        String userName = AuthenticationUtil.getCurrentUser();
        log.debug(userName);
        if (log.isDebugEnabled()) {
            log.debug("[userName=" + userName + "|url=" + url + "]");
        }

        if (url == null) {
            return TagSupport.EVAL_BODY_INCLUDE;
        }

        if (!accessibleToUrl()) {
            try {
                pageContext.getOut().println("<span style='display:none;'>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("User[" + userName + "]hasNoRightToDo with anyone of url(s)[" + url + "]");
        }

        return TagSupport.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        if (!accessibleToUrl()) {
            try {
                pageContext.getOut().println("</span>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return TagSupport.EVAL_PAGE;
    }

    /**
     * 判断用户是否有权限访问
     */
    protected boolean accessibleToUrl() {
        String[] dd = url.split(URL_DIVIDER);

        if (wac == null) {
            wac = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
        }

        if (authenticationUtil == null) {
            authenticationUtil = (AuthenticationUtil) wac.getBean("authenticationUtil");
        }

        for (String element : dd) {
            if (authenticationUtil.accessibleTo(element)) {
                return true;
            }
        }

        return false;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

