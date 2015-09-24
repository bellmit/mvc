package com.thd.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.system.model.Function;
import com.thd.system.model.Role;
import com.thd.system.service.SystemService;
import com.thd.system.vo.RoleVo;

@Controller
@RequestMapping("/system/role/*")
public class RoleController {

	@Autowired
	private SystemService systemService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 跳到角色管理初始页面
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/roleListForm")
	public ModelAndView roleListForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 返回角色管理初始页面ModelAndView
		return new ModelAndView("/system/role/roleListForm");
	}

	/**
	 * 分页查询角色列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/roleList")
	@ResponseBody
	public PageData<Role> roleList(PageParam pageParam, Role roleVo) {
		PageData<Role> result = systemService.queryPageRoleList(pageParam, roleVo);
		return result;
	}

	/**
	 * 跳到角色新增页面
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/roleAddForm")
	public ModelAndView roleAddForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 返回角色新增页面ModelAndView
		return new ModelAndView("/system/role/roleAddForm");
	}

	/**
	 * 跳到角色修改页面
	 * @param id
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/roleUpdForm")
	public ModelAndView roleUpdForm(String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/role/roleUpdForm");
		modelAndView.addObject("id", id);
		// 返回角色新增页面ModelAndView
		return modelAndView;
	}

	/**
	 * 根据ID取得角色详细
	 * @param id
	 * @return Role
	 * @throws Exception
	 */
	@RequestMapping("/getRoleDetail")
	@ResponseBody
	public Role getRoleDetail(String id) throws Exception {
		Role role = systemService.getRoleById(id);
		Role roleDetail = new Role();
		roleDetail.setId(role.getId());
		roleDetail.setRoleName(role.getRoleName());
		roleDetail.setRoleMemo(role.getRoleMemo());
		if (role.getFunctions() != null && role.getFunctions().size() > 0) {
			List<Function> funList = new ArrayList<Function>();
			for (Function fun : role.getFunctions()) {
				Function function = new Function();
				function.setId(fun.getId());
				function.setFunName(fun.getFunName());
				function.setFunOrder(fun.getFunOrder());
				function.setFunMemo(fun.getFunMemo());
				function.setFunUrl(fun.getFunUrl());
				funList.add(function);
			}
			roleDetail.setFunctions(funList);
		}
		return roleDetail;
	}

	/**
	 * 新增角色
	 * @return ModelMap
	 * @throws Exception
	 */
	@RequestMapping("/saveRole")
	@ResponseBody
	public ModelMap saveRole(RoleVo roleVo) throws Exception {
		ModelMap modelMap = new ModelMap();
		Role role = new Role();
		role.setRoleName(roleVo.getRoleName());
		role.setRoleMemo(roleVo.getRoleMemo());
		systemService.saveRole(role, roleVo.getFunctionList());
		modelMap.put("successFlg", "0");
		modelMap.put("successMsg", "保存成功！");
		return modelMap;
	}

	/**
	 * 修改角色
	 * @return ModelMap
	 * @throws Exception
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public ModelMap updateRole(RoleVo roleVo) throws Exception {
		ModelMap modelMap = new ModelMap();
		Role role = new Role();
		role.setId(roleVo.getId());
		role.setRoleName(roleVo.getRoleName());
		role.setRoleMemo(roleVo.getRoleMemo());
		systemService.updateRole(role, roleVo.getFunctionList());
		modelMap.put("successFlg", "0");
		modelMap.put("successMsg", "保存成功！");
		return modelMap;
	}

	/**
	 * 删除角色
	 * @param id
	 * @return ModelMap
	 * @throws Exception
	 */
	@RequestMapping("/deleteRole")
	@ResponseBody
	public ModelMap deleteRole(String id) throws Exception {
		systemService.deleteRole(id);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "删除成功！");
		return modelMap;
	}
}
