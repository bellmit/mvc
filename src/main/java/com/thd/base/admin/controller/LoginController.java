package com.thd.base.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thd.base.admin.model.Login;
import com.thd.base.admin.model.LoginHis;
import com.thd.base.admin.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thd.base.security.util.AuthenticationUtil;
import com.thd.base.util.LoggerUtil;

@Controller
@RequestMapping("/basic/*")
public class LoginController {
    Logger logger = LoggerUtil.getLogger();
    @Resource
    private LoginService loginService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public ModelMap checkLogin(Login login) throws Exception {
        String corpCode = login.getCorpCode();

        ModelMap map = new ModelMap();
        map.put("success", false);
        if (!"default".equals(corpCode)) {
            map.put("msg", "公司不存在!");
            return map;
        }

        /*String username = login.getJ_username();
        if (!"admin".equals(username)) {
            map.put("msg", "用户不存在!");
            return map;
        }*/

        //String password = request.getParameter("j_password");
        map.put("success", true);
        return map;
    }

    /**
     * 未登录，跳转登录页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("进入【浦发银行投诉管理】登录页面！");
        }
        logger.info("进入【浦发银行投诉管理】登录页面！");
        logger.debug("进入【浦发银行投诉管理】登录页面！");
        return new ModelAndView("/login");
    }

    /**
     * 登出，跳转登录页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
//    @RequestMapping("/loginOut")
//    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        loginService.saveLoginHis(LoginHis.LoginType.LOGOUT);
//        if (logger.isDebugEnabled()) {
//            logger.debug("登出系统！");
//        }
//        return new ModelAndView("/login");
//    }

    /**
     * 登录成功，跳转到菜单页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/loginSuccess")
    public ModelAndView loginSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute("uname", AuthenticationUtil.getMyUserDetails().getOperName());
        ModelAndView modelAndView = new ModelAndView("/base/login/main");
        loginService.saveLoginHis(LoginHis.LoginType.LOGIN);
        return modelAndView;
    }

    @RequestMapping("/mainHead")
    public ModelAndView mainHead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/base/login/main-head");
        return modelAndView;
    }

    @RequestMapping("/mainLeft")
    public ModelAndView mainLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/base/login/main-left");
        return modelAndView;
    }

    /**
     * 登录成功，跳转到菜单页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/loginFailed")
    public ModelAndView loginFailed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/base/exception/exceptionLogin");
    }

    /**
     * 登录成功，跳转到菜单页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/error")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/base/exception/exceptionDenied");
    }

    /**
     * session会话超时
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/sessionOut")
    public ModelAndView sessionOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/base/exception/exceptionSessionTimeOut");
    }

}
