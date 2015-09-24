package com.thd.spring.start.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thd.spring.start.model.User;

@Controller
@RequestMapping("/start/*")
public class StratController {

	@RequestMapping("/toStart")
	public String toStart(HttpServletRequest request) {
		System.out.println("=======toStart 运行=======");
		return "/start";
	}

	@RequestMapping("/addUser")
	public String addUser(User user, HttpServletRequest request) {
		System.out.println("=======addUser 运行=======");
		request.setAttribute("user", user);
		return "/user";
	}

	@RequestMapping("/toJson")
	public String toJson(HttpServletRequest request) {
		System.out.println("=======toJson 运行=======");
		return "/success/addUserJson";
	}

	@RequestMapping("/addUserJson")
	public ModelMap addUserJson(User user, HttpServletRequest request) {
		System.out.println("=======addUserJson 运行=======");
		ModelMap modelMap = new ModelMap();
		modelMap.put("name", user.getName());
		return modelMap;
	}

	@RequestMapping("/toUpload")
	public String toUpload(HttpServletRequest request) {
		System.out.println("=======toUpload 运行=======");
		return "/upload";
	}

	@RequestMapping("/toMain")
	public String toMain(HttpServletRequest request) {
		System.out.println("=======toMain 运行=======");
		return "/base/main";
	}

}
