package com.thd.system.controller;

import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.base.util.LoggerUtil;
import com.thd.system.model.Operator;
import com.thd.system.model.Operator.PositionType;
import com.thd.system.model.Organization;
import com.thd.system.model.Role;
import com.thd.system.service.SystemService;
import com.thd.system.vo.OperatorVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/system/operator/*")
public class OperatorController {

	@Autowired
	SystemService systemService;

	Logger logger = LoggerUtil.getLogger();

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 跳到用户管理初始页面
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/operatorListForm")
	public ModelAndView operatorListForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String[]> positionTypeList = new ArrayList<String[]>();
		for (PositionType type : PositionType.values()) {
			positionTypeList.add(new String[] { type.toString(), type.getText() });
		}
		// 返回用户管理初始页面ModelAndView
		return new ModelAndView("/system/operator/operatorListForm");
	}

	/**
	 * 分页查询用户
	 * @param pageParam
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/operatorList")
	@ResponseBody
	public PageData<Operator> operatorList(PageParam pageParam, Operator operator) {
		PageData<Operator> result = systemService.queryPageOperatorList(pageParam, operator);
		return result;
	}

	/**
	 * 跳转到用户新增页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/operatorAddForm")
	public ModelAndView operatorAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/operator/operatorAddForm");// 返回用户修改页面
		List<String[]> positionTypeList = new ArrayList<String[]>();
		for (PositionType type : PositionType.values()) {
			positionTypeList.add(new String[] { type.toString(), type.getText() });
		}
		modelAndView.addObject("positionTypeList", positionTypeList);

		return modelAndView;
	}

	/**
	 * 跳转到用户修改页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/operatorUpdForm")
	public ModelAndView operatorUpdForm(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/operator/operatorUpdForm");// 返回用户修改页面
		Operator operator = getOperatorDetail(id);
		List<String[]> positionTypeList = new ArrayList<String[]>();
		for (PositionType type : PositionType.values()) {
			positionTypeList.add(new String[] { type.toString(), type.getText() });
		}
		modelAndView.addObject("positionTypeList", positionTypeList);
		modelAndView.addObject("id", id);
		modelAndView.addObject("operator", operator);
		// 返回用户修改页面ModelAndView
		return modelAndView;
	}

	/**
	 * 新增用户
	 * @param operatorVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveOperator")
	@ResponseBody
	public ModelMap saveOperator(OperatorVo operatorVo) throws Exception {
		ModelMap modelMap = new ModelMap();
		Operator operator = new Operator();
		operator.setOperAccountNo(operatorVo.getOperAccountNo());
		operator.setOperGender(operatorVo.getOperGender());
		operator.setOperName(operatorVo.getOperName());
		operator.setOperPassword(operatorVo.getOperPassword());
		operator.setOperMail(operatorVo.getOperMail());
		operator.setOperType(operatorVo.getOperType());
		operator.setOperPhone(operatorVo.getOperPhone());
		List<Operator> operList = systemService.getOperByAccount(operator.getOperAccountNo());
		// 判断用户是否存在
		if (operList != null && operList.size() > 0) {
			modelMap.put("accountNoExit", "1");
			return modelMap;
		} else {
			systemService.saveUser(operator, operatorVo.getOrgList(), operatorVo.getRoleList());

			modelMap.put("successFlg", "0");
			modelMap.put("successMsg", "保存成功！");
			return modelMap;
		}
	}

	/**
	 * 修改用户
	 * @param operatorVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upadteOperator")
	@ResponseBody
	public ModelMap upadteOperator(OperatorVo operatorVo) throws Exception {
		ModelMap modelMap = new ModelMap();
		Operator operator = new Operator();
		operator.setId(operatorVo.getId());
		operator.setOperAccountNo(operatorVo.getOperAccountNo());
		operator.setOperGender(operatorVo.getOperGender());
		operator.setOperName(operatorVo.getOperName());
		operator.setOperPassword(operatorVo.getOperPassword());
		operator.setOperPhone(operatorVo.getOperPhone());
		operator.setOperMail(operatorVo.getOperMail());
		operator.setOperType(operatorVo.getOperType());
		systemService.updateUser(operator, operatorVo.getOrgList(), operatorVo.getRoleList());
		modelMap.put("successFlg", "0");
		modelMap.put("successMsg", "保存成功！");
		return modelMap;
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteOperator")
	@ResponseBody
	public ModelMap deleteOperator(String id) throws Exception {
		systemService.deleteUser(id);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "删除成功！");
		return modelMap;
	}

	/**
	 * 获取用户的详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOperatorDetail")
	@ResponseBody
	public Operator getOperatorDetail(String id) throws Exception {
		Operator operator = systemService.getOperById(id);
		Operator operDetail = new Operator();
		operDetail.setId(operator.getId());
		operDetail.setOperAccountNo(operator.getOperAccountNo());
		operDetail.setOperPassword(operator.getOperPassword());
		operDetail.setOperName(operator.getOperName());
		operDetail.setOperGender(operator.getOperGender());
		operDetail.setOperType(operator.getOperType());
		operDetail.setOperPhone(operator.getOperPhone());
		operDetail.setOperMail(operator.getOperMail());

		if (operator.getOrganizations() != null && operator.getOrganizations().size() > 0) {
			List<Organization> orgList = new ArrayList<Organization>();
			for (Organization org : operator.getOrganizations()) {
				Organization orgDetail = new Organization();
				orgDetail.setId(org.getId());
				orgDetail.setOrgName(org.getOrgName());
				orgDetail.setOrgAddress(org.getOrgAddress());
				orgDetail.setOrgMemo(org.getOrgMemo());
				orgList.add(orgDetail);
			}
			operDetail.setOrganizations(orgList);
		}
		if (operator.getRoles() != null && operator.getRoles().size() > 0) {
			List<Role> roleList = new ArrayList<Role>();
			for (Role role : operator.getRoles()) {
				Role roleDetail = new Role();
				roleDetail.setId(role.getId());
				roleDetail.setRoleName(role.getRoleName());
				roleDetail.setRoleMemo(role.getRoleMemo());
				roleList.add(roleDetail);
			}
			operDetail.setRoles(roleList);
		}
		return operDetail;
	}

}
