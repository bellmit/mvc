package com.thd.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.thd.base.dao.BaseDao;
import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.base.page.PageServiceTemplate;
import com.thd.base.util.LoggerUtil;
import com.thd.base.util.MD5;
import com.thd.system.model.Function;
import com.thd.system.model.Operator;
import com.thd.system.model.Organization;
import com.thd.system.model.Role;

@Service
@SuppressWarnings("unchecked")
public class SystemService {

	Logger logger = LoggerUtil.getLogger();

	@Resource
	private BaseDao baseDao;

	/**
	 * 新增功能
	 */
	public Function saveFunction(Function fun) {
		Function function = new Function();
		function.setFunName(fun.getFunName());
		function.setFunMemo(fun.getFunName());
		function.setFunUrl(fun.getFunUrl());
		function.setFunOrder(fun.getFunOrder());
		if (fun.getParentFunction() != null && fun.getParentFunction().getId() != null) {
			Function parentFunction = baseDao.load(Function.class, fun.getParentFunction().getId());
			parentFunction.addChildFun(function);
		}
		baseDao.save(function);
		return function;
	}

	/**
	 * 修改功能
	 * @param fun
	 */
	public Function updateFunction(Function fun) {
		Function function = baseDao.load(Function.class, fun.getId());
		function.setFunName(fun.getFunName());
		function.setFunMemo(fun.getFunName());
		function.setFunUrl(fun.getFunUrl());
		function.setFunOrder(fun.getFunOrder());
		if (fun.getParentFunction() != null && fun.getParentFunction().getId() != null) {
			Function parentFunction = baseDao.load(Function.class, fun.getParentFunction().getId());
			if (function.getParentFunction() != null) {
				function.getParentFunction().getChildFuns().remove(function);
			}
			parentFunction.addChildFun(function);
			function.setParentFunction(parentFunction);
		} else {
			if (function.getParentFunction() != null) {
				function.getParentFunction().getChildFuns().remove(function);
				function.setParentFunction(null);
			}
		}
		baseDao.saveOrUpdate(function);
		return function;
	}

	/**
	 * 删除功能
	 * @param id
	 */
	public void deleteFunction(String id) {
		Function function = baseDao.load(Function.class, id);
		function.setEnableFlg(false);
		function.clear();
		baseDao.saveOrUpdate(function);
	}

	/**
	 * 根据功能ID，查询出功能信息
	 * @param id
	 */
	public Function getFunctionById(String id) {
		Function function = baseDao.load(Function.class, id);
		baseDao.getHibernateTemplate().initialize(function);
		baseDao.getHibernateTemplate().initialize(function.getParentFunction());
		baseDao.getHibernateTemplate().initialize(function.getChildFuns());
		return function;
	}

	/**
	 * 获得所有有效功能
	 */
	public List<Function> getFunctionList() {
		String hql = "from Function where enableFlg = ?";
		List<Function> resultList = baseDao.find(hql, new Object[] { Boolean.TRUE });
		return resultList;
	}

	/**
	 * 增加角色
	 */
	public Role saveRole(Role roleVo, List<Function> funVoList) {
		Role role = new Role();
		role.setRoleName(roleVo.getRoleName());
		role.setRoleMemo(roleVo.getRoleMemo());
		if (funVoList != null && funVoList.size() > 0) {
			for (Function fun : funVoList) {
				fun.getId();
				System.out.println(fun.getId());
				Function function = baseDao.load(Function.class, fun.getId());
				role.addFunction(function);
			}
		}
		baseDao.save(role);
		return role;
	}

	/**
	 * 修改角色
	 * @param roleVo
	 * @param funVoList
	 */
	public Role updateRole(Role roleVo, List<Function> funVoList) {
		Role role = baseDao.load(Role.class, roleVo.getId());
		role.setRoleName(roleVo.getRoleName());
		role.setRoleMemo(roleVo.getRoleMemo());
		role.clearFunctions();
		baseDao.saveOrUpdate(role);
		baseDao.flush();
		if (funVoList != null && funVoList.size() > 0) {
			for (Function fun : funVoList) {
				Function function = baseDao.load(Function.class, fun.getId());
				role.addFunction(function);
			}
		}
		baseDao.saveOrUpdate(role);
		baseDao.flush();
		return role;
	}

	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteRole(String id) {
		Role role = baseDao.load(Role.class, id);
		role.setEnableFlg(false);
		role.clear();
		baseDao.saveOrUpdate(role);
	}

	/**
	 * 根据角色ID，查询出角色信息
	 * @param id
	 */
	public Role getRoleById(String id) {
		Role role = baseDao.load(Role.class, id);
		baseDao.getHibernateTemplate().initialize(role);
		baseDao.getHibernateTemplate().initialize(role.getUsers());
		baseDao.getHibernateTemplate().initialize(role.getFunctions());
		return role;
	}

	/**
	 * 根据角色名称，查询出角色信息
	 * @param roleName
	 */
	public List<Role> getRolesByName(String roleName) {
		String hql = "from Role where roleName = ? and enableFlg = ?";
		List<Role> resultList = baseDao.find(hql, new Object[] { roleName, Boolean.TRUE });
		return resultList;
	}

	/**
	 * 获得所有有效角色
	 */
	public List<Role> getRoleList() {
		String hql = "from Role where enableFlg = ?";
		List<Role> resultList = baseDao.find(hql, new Object[] { Boolean.TRUE });
		return resultList;
	}

	/**
	 * 分页查询角色列表
	 * @param pageParam
	 * @param criteria Role查询条件：角色名称ilike匹配
	 */
	public PageData<Role> queryPageRoleList(PageParam pageParam, Role criteria) {
		PageServiceTemplate<Role, Role> template = new PageServiceTemplate<Role, Role>(baseDao) {
			@Override
			protected DetachedCriteria constructCriteria(Role criteria) {
				DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
				if (StringUtils.isNotEmpty(criteria.getRoleName())) {
					dc.add(Restrictions.ilike("roleName", "%" + criteria.getRoleName() + "%"));
				}
				return dc;
			}
		};

		PageData<Role> queryResult = template.doPagingQuery(pageParam, criteria);
		List<Role> roleList = new ArrayList<Role>();
		for (Role rolePo : queryResult.getRows()) {
			Role role = new Role();
			role.setId(rolePo.getId());
			role.setRoleName(rolePo.getRoleName());
			role.setRoleMemo(rolePo.getRoleMemo());
			roleList.add(role);
		}
		queryResult.setRows(roleList);
		return queryResult;
	}

	/**
	 * 新增机构
	 * @param orgVo
	 */
	public Organization saveOrganization(Organization orgVo) {
		Organization org = new Organization();
		org.setOrgName(orgVo.getOrgName());
		org.setOrgAddress(orgVo.getOrgAddress());
		org.setOrgMemo(orgVo.getOrgMemo());
		if (orgVo.getParentOrganization() != null && orgVo.getParentOrganization().getId() != null) {
			Organization parentOrg = baseDao.load(Organization.class, orgVo.getParentOrganization().getId());
			parentOrg.addChildOrg(org);
		}
		baseDao.save(org);
		return org;
	}

	/**
	 * 修改机构
	 * @param orgVo
	 */
	public Organization updateOrganization(Organization orgVo) {
		Organization org = baseDao.load(Organization.class, orgVo.getId());
		baseDao.getHibernateTemplate().initialize(org);
		baseDao.getHibernateTemplate().initialize(org.getUsers());
		baseDao.getHibernateTemplate().initialize(org.getChildOrgs());
		baseDao.getHibernateTemplate().initialize(org.getParentOrganization());
		org.setOrgName(orgVo.getOrgName());
		org.setOrgAddress(orgVo.getOrgAddress());
		org.setOrgMemo(orgVo.getOrgMemo());
		if (orgVo.getParentOrganization() != null && orgVo.getParentOrganization().getId() != null) {
			Organization parentOrg = baseDao.load(Organization.class, orgVo.getParentOrganization().getId());
			if (org.getParentOrganization() != null) {
				org.getParentOrganization().getChildOrgs().remove(org);
			}
			parentOrg.addChildOrg(org);
			org.setParentOrganization(parentOrg);
		} else {
			if (org.getParentOrganization() != null) {
				org.getParentOrganization().getChildOrgs().remove(org);
				org.setParentOrganization(null);
			}
		}
		baseDao.saveOrUpdate(org);
		return org;
	}

	/**
	 * 删除机构
	 * @param id
	 */
	public void deleteOrganization(String id) {
		Organization org = baseDao.load(Organization.class, id);
		org.setEnableFlg(false);
		org.clear();
		baseDao.saveOrUpdate(org);
	}

	/**
	 * 根据角色ID，查询出机构信息
	 * @param id
	 */
	public Organization getOrgById(String id) {
		Organization org = baseDao.load(Organization.class, id);
		baseDao.getHibernateTemplate().initialize(org);
		baseDao.getHibernateTemplate().initialize(org.getUsers());
		baseDao.getHibernateTemplate().initialize(org.getChildOrgs());
		baseDao.getHibernateTemplate().initialize(org.getParentOrganization());
		return org;
	}

	/**
	 * 获得所有有效机构
	 */
	public List<Organization> getOrgList() {
		String hql = "from Organization where enableFlg = ?";
		List<Organization> resultList = baseDao.find(hql, new Object[] { Boolean.TRUE });
		return resultList;
	}

	/**
	 * 增加用户
	 * @param userVo
	 * @param orgVoList
	 * @param roleVoList
	 */
	public Operator saveUser(Operator userVo, List<Organization> orgVoList, List<Role> roleVoList) {
		Operator oper = new Operator();
		oper.setOperAccountNo(userVo.getOperAccountNo());
		oper.setOperPassword(MD5.string2MD5(userVo.getOperPassword()));
		oper.setOperName(userVo.getOperName());
		oper.setOperGender(userVo.getOperGender());
		oper.setOperMail(userVo.getOperMail());
		oper.setOperType(userVo.getOperType());
		oper.setOperPhone(userVo.getOperPhone());
		// 添加机构
		if (orgVoList != null && orgVoList.size() > 0) {
			for (Organization org : orgVoList) {
				Organization organization = baseDao.load(Organization.class, org.getId());
				oper.addOrganization(organization);
			}
		}
		// 添加角色
		if (roleVoList != null && roleVoList.size() > 0) {
			for (Role r : roleVoList) {
				Role role = baseDao.load(Role.class, r.getId());
				oper.addRole(role);
			}
		}
		baseDao.save(oper);
		return oper;
	}

	/**
	 * 修改用户
	 * @param userVo
	 * @param orgVoList
	 * @param roleVoList
	 */
	public Operator updateUser(Operator userVo, List<Organization> orgVoList, List<Role> roleVoList) {
		Operator oper = baseDao.load(Operator.class, userVo.getId());
		oper.setOperName(userVo.getOperName());
		oper.setOperGender(userVo.getOperGender());
		oper.setOperPhone(userVo.getOperPhone());
		oper.setOperMail(userVo.getOperMail());
		oper.setOperType(userVo.getOperType());
		oper.clearOrganizations();
		oper.clearRoles();
		baseDao.saveOrUpdate(oper);
		baseDao.flush();
		// 添加机构
		if (orgVoList != null && orgVoList.size() > 0) {
			for (Organization org : orgVoList) {
				Organization organization = baseDao.load(Organization.class, org.getId());
				oper.addOrganization(organization);
			}
		}
		// 添加角色
		if (roleVoList != null && roleVoList.size() > 0) {
			for (Role r : roleVoList) {
				Role role = baseDao.load(Role.class, r.getId());
				oper.addRole(role);
			}
		}
		baseDao.saveOrUpdate(oper);
		baseDao.flush();
		return oper;
	}

	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteUser(String id) {
		Operator oper = baseDao.load(Operator.class, id);
		oper.setEnableFlg(false);
		oper.clear();
		baseDao.saveOrUpdate(oper);
	}

	/**
	 * 根据角色ID，查询出用户信息
	 * @param id
	 */
	public Operator getOperById(String id) {
		Operator oper = baseDao.load(Operator.class, id);
		baseDao.getHibernateTemplate().initialize(oper);
		baseDao.getHibernateTemplate().initialize(oper.getRoles());
		baseDao.getHibernateTemplate().initialize(oper.getOrganizations());
		return oper;
	}

	/**
	 * 根据用户的登录名（accountNo），查询出用户信息
	 * @param accountNo
	 */
	public List<Operator> getOperByAccount(String accountNo) {
		String hql = "from Operator where operAccountNo = ? and enableFlg = ?";
		List<Operator> resultList = baseDao.find(hql, new Object[] { accountNo, Boolean.TRUE });
		//		if (resultList != null && resultList.size() > 0) {
		//			for (Operator oper : resultList) {
		//				baseDao.getHibernateTemplate().initialize(oper);
		//				baseDao.getHibernateTemplate().initialize(oper.getRoles());
		//				baseDao.getHibernateTemplate().initialize(oper.getOrganizations());
		//			}
		//		}
		return resultList;
	}

	/**
	 * 获得所有有效用户
	 */
	public List<Operator> getOperList() {
		String hql = "from Operator where enableFlg = ?";
		List<Operator> resultList = baseDao.find(hql, new Object[] { Boolean.TRUE });
		return resultList;
	}

	/**
	 * 分页查询用户列表
	 * @param pageParam
	 * @param criteria
	 * @return
	 */
	public PageData<Operator> queryPageOperatorList(PageParam pageParam, Operator criteria) {
		PageServiceTemplate<Operator, Operator> template = new PageServiceTemplate<Operator, Operator>(baseDao) {
			@Override
			protected DetachedCriteria constructCriteria(Operator criteria) {
				DetachedCriteria dc = DetachedCriteria.forClass(Operator.class);
				if (StringUtils.isNotEmpty(criteria.getOperAccountNo())) {
					dc.add(Restrictions.ilike("operAccountNo", "%" + criteria.getOperAccountNo() + "%"));// 根据登陆账号模糊查询
				}
				if (StringUtils.isNotEmpty(criteria.getOperName())) {
					dc.add(Restrictions.ilike("operName", "%" + criteria.getOperName() + "%"));// 根据姓名模糊查询
				}
				if (criteria.getOperType() != null) {// 用户类型查询
					dc.add(Restrictions.eq("operType", criteria.getOperType()));
				}
				if (StringUtils.isNotEmpty(criteria.getOperMail())) {// 用户邮箱查询
					dc.add(Restrictions.ilike("operMail", "%" + criteria.getOperMail() + "%"));
				}
				return dc;
			}
		};

		PageData<Operator> queryResult = template.doPagingQuery(pageParam, criteria);
		List<Operator> operatorList = new ArrayList<Operator>();
		for (Operator operatorPo : queryResult.getRows()) {
			Operator operator = new Operator();
			operator.setId(operatorPo.getId());
			operator.setOperAccountNo(operatorPo.getOperAccountNo());// 登录名
			operator.setOperGender(operatorPo.getOperGender());// 性别
			operator.setOperMail(operatorPo.getOperMail());
			operator.setOperName(operatorPo.getOperName());
			operator.setOperPhone(operatorPo.getOperPhone());
			operator.setOperPassword(operatorPo.getOperPassword());
			operator.setOperType(operatorPo.getOperType());

			operatorList.add(operator);
		}
		queryResult.setRows(operatorList);
		return queryResult;

	}

	/**
	 * 查询父ID为空的机构List
	 */
	public List<Organization> findOrgList() {
		DetachedCriteria dc = DetachedCriteria.forClass(Organization.class);
		return baseDao.findByCriteria(dc);
	}

	/**
	 * 查询父ID为id的机构List
	 */
	public List<Organization> findOrgList(String id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Organization.class);
		dc.add(Restrictions.eq("parentOrganization.id", id));
		return baseDao.findByCriteria(dc);
	}

	//	@SuppressWarnings("unchecked")
	//	public List<Organization> queryOrganTree() {
	//		String hql = " from Organization where fk_parent_org_id is null";
	//		List<Organization> resultList = baseDao.find(hql);
	//		return resultList;
	//	}

	public List<Organization> queryTree(String id) {
		String hql = " from Organization where fk_parent_org_id=" + id;
		if (StringUtils.isEmpty(id)) {
			hql = " from Organization where fk_parent_org_id is null";
		}
		List<Organization> resultList = baseDao.find(hql);
		return resultList;
	}

}
