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

import com.thd.base.page.TreeData;
import com.thd.base.page.TreeRow;
import com.thd.system.model.Organization;
import com.thd.system.service.SystemService;
import com.thd.system.vo.TreeVo;

@Controller
@RequestMapping("/system/organization/*")
public class OrganizationController {

	@Autowired
	private SystemService systemService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 跳到机构管理初始页面
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/organizationListForm")
	public ModelAndView organizationListForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 返回机构管理初始页面ModelAndView
		return new ModelAndView("/system/organization/organizationListForm");
	}

	/**
	 * 获得机构列表
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/organizationList")
	@ResponseBody
	public TreeData<Organization> organizationList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TreeData<Organization> treeData = new TreeData<Organization>();
		List<Organization> orgList = systemService.getOrgList();
		if (orgList != null && orgList.size() > 0) {
			List<TreeRow<Organization>> rows = new ArrayList<TreeRow<Organization>>();
			for (Organization org : orgList) {
				TreeRow<Organization> treeRow = new TreeRow<Organization>();
				treeRow.setId(org.getId());
				if (org.getParentOrganization() != null && org.getParentOrganization().getId() != null) {
					treeRow.set_parentId(org.getParentOrganization().getId());
				}
				Organization organization = new Organization();
				organization.setOrgName(org.getOrgName());
				organization.setOrgAddress(org.getOrgAddress());
				organization.setOrgMemo(org.getOrgMemo());
				treeRow.setObj(organization);
				rows.add(treeRow);
			}
			treeData.setRows(rows);
			treeData.setTotal(orgList.size());
		}
		// 返回机构管理初始页面ModelAndView
		return treeData;
	}

	/**
	 * 新增机构
	 * @param orgVo
	 * @return ModelMap
	 * @throws Exception
	 */
	@RequestMapping("/saveOrganization")
	@ResponseBody
	public ModelMap saveOrganization(Organization orgVo) throws Exception {
		systemService.saveOrganization(orgVo);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "保存成功！");
		return modelMap;
	}

	/**
	 * 修改机构
	 * @param orgVo
	 * @return ModelMap
	 * @throws Exception
	 */
	@RequestMapping("/updateOrganization")
	@ResponseBody
	public ModelMap updateOrganization(Organization orgVo) throws Exception {
		systemService.updateOrganization(orgVo);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "保存成功！");
		return modelMap;
	}

	/**
	 * 删除机构
	 * @param id
	 * @return ModelMap
	 * @throws Exception
	 */
	@RequestMapping("/deleteOrganization")
	@ResponseBody
	public ModelMap deleteOrganization(String id) throws Exception {
		systemService.deleteOrganization(id);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "删除成功！");
		return modelMap;
	}

	/**
	 * 转到机构树页面
	 */
	@RequestMapping("/toOrgTreeForm")
	public ModelAndView toOrgTreeForm() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/system/tree/orgTreeForm");
		return modelAndView;
	}

	/**
	 * 返回机构树父亲json数据
	 */
	@RequestMapping("/orgTreeJson")
	@ResponseBody
	public List<TreeVo> orgTreeJson() {
		List<TreeVo> list = new ArrayList<TreeVo>();
		for (Organization org : systemService.findOrgList()) {
			if (null == org.getParentOrganization()) {
				TreeVo treeVo = new TreeVo();
				treeVo.setId(org.getId());
				treeVo.setText(org.getOrgName());
				treeVo.setChildren(this.orgTreeChildrenJson(org.getId()));
				list.add(treeVo);
			}
		}
		return list;
	}

	/**
	 * 返回机构树孩子json数据
	 */
	@RequestMapping("/orgTreeChildrenJson")
	@ResponseBody
	public List<TreeVo> orgTreeChildrenJson(String id) {
		List<TreeVo> list = new ArrayList<TreeVo>();
		for (Organization org : systemService.findOrgList(id)) {
			TreeVo treeVo = new TreeVo();
			treeVo.setId(org.getId());
			treeVo.setText(org.getOrgName());
			treeVo.setChildren(this.orgTreeChildrenJson(org.getId()));
			list.add(treeVo);
		}
		return list;
	}

	@RequestMapping("/toTestTreeForm")
	public ModelAndView toTestTreeForm() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/system/tree/treeTestInit");
		return modelAndView;
	}

	/**
	 * 查询组织机构
	 */
	@RequestMapping("/getTree")
	@ResponseBody
	public List<TreeVo> getChildren(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Organization> list = new ArrayList<Organization>();
		list = systemService.queryTree(id);
		//		List<Map<String,String>> treeJSon =new ArrayList<Map<String,String>>();
		List<TreeVo> treeJSon = new ArrayList<TreeVo>();
		if (list != null && list.size() > 0) {
			for (Organization organ : list) {
				TreeVo combo = new TreeVo();

				//				Map<String,String> map=new HashMap<String, String>();
				//				map.put("id", Long.toString(organ.getId()));
				//				map.put("text",organ.getOrgName());
				//				map.put("state", "closed");
				combo.setId(organ.getId());
				combo.setText(organ.getOrgName());
				if (organ.getChildOrgs() != null && organ.getChildOrgs().size() > 0) {
					combo.setState("closed");
					//					map.put("state", "closed");
				} else {
					combo.setChildren(null);
					combo.setState("open");
					//					map.put("state", "open");
				}
				treeJSon.add(combo);
				//				treeJSon.add(map);
			}
		}
		return treeJSon;
	}
}
