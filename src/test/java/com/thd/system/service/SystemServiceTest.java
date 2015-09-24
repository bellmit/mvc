package com.thd.system.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.base.util.BaseTxWebTests;
import com.thd.base.util.LoggerUtil;
import com.thd.system.model.Function;
import com.thd.system.model.Operator;
import com.thd.system.model.Organization;
import com.thd.system.model.Role;
import com.thd.system.model.Operator.GenderType;
import com.thd.system.model.Operator.OperatorType;

public class SystemServiceTest extends BaseTxWebTests {
	Logger logger = LoggerUtil.getLogger();
	@Resource
	protected SystemService systemService;

	private Function testFun;
	
	@Before
	public void beforeEveryTests() {
//		super.deleteFromTables("OPER_ORG_ASS", "ROLE_FUN_ASS", "OPER_ROLE_ASS", "OPERATOR", "ORGANIZATION", "ROLE",
//				"FUNCTION");
		super.setCurrentUserAsCaseHandler("admin");
		// 新增功能
		addFunction();
	}

	private void addFunction() {
		Function function = new Function();
		function.setFunName("测试功能001");
		function.setFunMemo("测试功能001！");
		function.setFunUrl("system/testUrl1");
		function.setFunOrder(1L);
		baseDao.save(function);
		testFun = function;
	}

	@Test
	public void test新增AND查询功能() {
		Function fun1 = new Function();
		fun1.setFunName("测试功能002");
		fun1.setFunMemo("测试功能002！");
		fun1.setFunUrl("system/testUrl2");
		fun1.setFunOrder(2L);
		logger.debug("保存到数据库前，FunctionId========" + fun1.getId());
		Function function1 = systemService.saveFunction(fun1);
		logger.debug("保存到数据库后，FunctionId========" + function1.getId());
		Assert.assertNotNull("新增无父功能的功能，主键不为空", function1.getId());

		Function fun2 = new Function();
		fun2.setFunName("测试功能003");
		fun2.setFunMemo("测试功能003！");
		fun2.setFunUrl("system/testUrl3");
		fun2.setFunOrder(3L);
		Function parentFun = new Function();
		parentFun.setId(testFun.getId());
		fun2.setParentFunction(parentFun);
		Function function2 = systemService.saveFunction(fun2);
		Assert.assertNotNull("新增有父功能的功能，主键不为空", function2.getId());
		Assert.assertNotNull("新增有父功能的功能，父功能不为空", function2.getParentFunction());
		Assert.assertEquals("新增有父功能的功能，父功能主键相等", testFun.getId(), function2.getParentFunction().getId());

		Function fun3 = new Function();
		fun3.setFunName("测试功能004");
		fun3.setFunMemo("测试功能004！");
		fun3.setFunUrl("system/testUrl4");
		fun3.setFunOrder(4L);
		Function parentFun2 = new Function();
		parentFun2.setId(function2.getId());
		fun3.setParentFunction(parentFun2);
		Function function3 = systemService.saveFunction(fun3);

		Function function = systemService.getFunctionById(function2.getId());
		baseDao.flush();
		Assert.assertNotNull("功能本身不为空", function);
		Assert.assertNotNull("父功能不为空", function.getParentFunction());
		Assert.assertNotNull("子功能不为空", function.getChildFuns());
		Assert.assertEquals("父功能ID相等判断", testFun.getId(), function.getParentFunction().getId());
		Assert.assertEquals("只有一个子功能", 1, function.getChildFuns().size());
		Assert.assertEquals("只功能ID相等判断", function3.getId(), function.getChildFuns().get(0).getId());

		function = systemService.getFunctionById(function1.getId());
		Assert.assertNotNull("功能本身不为空", function);
		Assert.assertNull("父功能为空", function.getParentFunction());
		Assert.assertNotNull("子功能不为空", function.getChildFuns());
		Assert.assertEquals("没有子功能", 0, function.getChildFuns().size());
	}

	@Test
	public void test修改AND查询功能() {
		Function fun1 = new Function();
		fun1.setFunName("测试功能002");
		fun1.setFunMemo("测试功能002！");
		fun1.setFunUrl("system/testUrl2");
		fun1.setFunOrder(2L);
		Function function1 = systemService.saveFunction(fun1);

		Function fun3 = new Function();
		fun3.setFunName("测试功能004");
		fun3.setFunMemo("测试功能004！");
		fun3.setFunUrl("system/testUrl4");
		fun3.setFunOrder(4L);
		Function parentFun2 = new Function();
		parentFun2.setId(function1.getId());
		fun3.setParentFunction(parentFun2);
		Function function3 = systemService.saveFunction(fun3);

		Function function = systemService.getFunctionById(function3.getId());
		Assert.assertEquals("功能名称为【测试功能004】", "测试功能004", function.getFunName());
		Assert.assertEquals("父功能ID", function1.getId(), function.getParentFunction().getId());

		function1.setFunName("名字已经修改");
		function1.setParentFunction(testFun);
		systemService.updateFunction(function1);
		function = systemService.getFunctionById(function1.getId());
		Assert.assertEquals("功能名称为【名字已经修改】", "名字已经修改", function.getFunName());
		Assert.assertEquals("父功能ID", testFun.getId(), function.getParentFunction().getId());
	}

	@Test
	public void test删除AND查询功能() {
		Function fun2 = new Function();
		fun2.setFunName("测试功能003");
		fun2.setFunMemo("测试功能003！");
		fun2.setFunUrl("system/testUrl3");
		fun2.setFunOrder(3L);
		Function parentFun = new Function();
		parentFun.setId(testFun.getId());
		fun2.setParentFunction(parentFun);
		Function function2 = systemService.saveFunction(fun2);

		Function fun3 = new Function();
		fun3.setFunName("测试功能004");
		fun3.setFunMemo("测试功能004！");
		fun3.setFunUrl("system/testUrl4");
		fun3.setFunOrder(4L);
		Function parentFun2 = new Function();
		parentFun2.setId(function2.getId());
		fun3.setParentFunction(parentFun2);
		Function function3 = systemService.saveFunction(fun3);

		Function function = systemService.getFunctionById(testFun.getId());
		Assert.assertNotNull("父功能不为空", function);
		Assert.assertEquals("有一个子功能", 1, function.getChildFuns().size());
		Assert.assertEquals("子功能ID校验", function2.getId(), function.getChildFuns().get(0).getId());
		function = systemService.getFunctionById(function3.getId());
		Assert.assertEquals("父功能ID校验", function2.getId(), function.getParentFunction().getId());

		systemService.deleteFunction(function2.getId());
		function = systemService.getFunctionById(testFun.getId());
		Assert.assertNotNull("父功能不为空", function);
		Assert.assertEquals("子功能已经被删除", 0, function.getChildFuns().size());
		function = systemService.getFunctionById(function3.getId());
		Assert.assertNull("父功能为空", function.getParentFunction());

	}

	@Test
	public void test获得所有有效功能() {
		Function fun1 = new Function();
		fun1.setFunName("测试功能002");
		fun1.setFunMemo("测试功能002！");
		fun1.setFunUrl("system/testUrl2");
		fun1.setFunOrder(2L);
		Function function1 = systemService.saveFunction(fun1);

		List<Function> funList = systemService.getFunctionList();
		Assert.assertEquals("增加2个功能，功能长度为2", 2, funList.size());

		systemService.deleteFunction(function1.getId());
		funList = systemService.getFunctionList();
		Assert.assertEquals("删除一个功能，功能长度为1", 1, funList.size());
	}

	@Test
	public void test增加AND查询角色() {
		Role roleVo = new Role();
		roleVo.setRoleName("角色名称1");
		roleVo.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role = systemService.saveRole(roleVo, funVoList);

		Role queryRole = systemService.getRoleById(role.getId());
		Assert.assertNotNull("角色不为空", queryRole);
		Assert.assertNotNull("功能列表不为空", queryRole.getFunctions());
		Assert.assertEquals("功能列表长度为1", 1, queryRole.getFunctions().size());
		Assert.assertEquals("功能ID", testFun.getId(), queryRole.getFunctions().get(0).getId());
	}

	@Test
	public void test修改AND查询角色() {
		Role roleVo = new Role();
		roleVo.setRoleName("角色名称1");
		roleVo.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role = systemService.saveRole(roleVo, funVoList);

		Role queryRole = systemService.getRoleById(role.getId());
		Assert.assertNotNull("角色不为空", queryRole);
		Assert.assertNotNull("功能列表不为空", queryRole.getFunctions());
		Assert.assertEquals("功能列表长度为1", 1, queryRole.getFunctions().size());
		Assert.assertEquals("功能ID", testFun.getId(), queryRole.getFunctions().get(0).getId());

		Function fun = new Function();
		fun.setFunName("测试功能002");
		fun.setFunMemo("测试功能002！");
		fun.setFunUrl("system/testUrl2");
		fun.setFunOrder(2L);
		Function function1 = systemService.saveFunction(fun);
		Assert.assertNotNull("新增无父功能的功能，主键不为空", function1.getId());

		role.setRoleName("修改角色名称");
		funVoList.clear();
		funVoList.add(function1);
		Role role1 = systemService.updateRole(role, funVoList);

		Role queryRole1 = systemService.getRoleById(role1.getId());
		Assert.assertNotNull("角色不为空", queryRole1);
		Assert.assertNotNull("功能列表不为空", queryRole1.getFunctions());
		Assert.assertEquals("功能列表长度为1", 1, queryRole1.getFunctions().size());
		Assert.assertEquals("功能ID", function1.getId(), queryRole1.getFunctions().get(0).getId());
	}

	@Test
	public void test删除AND查询角色() {
		Role roleVo = new Role();
		roleVo.setRoleName("角色名称1");
		roleVo.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role = systemService.saveRole(roleVo, funVoList);

		Role queryRole = systemService.getRoleById(role.getId());
		Assert.assertNotNull("角色不为空", queryRole);
		Assert.assertNotNull("功能列表不为空", queryRole.getFunctions());
		Assert.assertEquals("功能列表长度为1", 1, queryRole.getFunctions().size());
		Assert.assertEquals("功能ID", testFun.getId(), queryRole.getFunctions().get(0).getId());
		Assert.assertEquals("角色列表长度为1", 1, queryRole.getFunctions().get(0).getRoles().size());
		Assert.assertEquals("角色ID", role.getId(), queryRole.getFunctions().get(0).getRoles().get(0).getId());

		systemService.deleteRole(role.getId());
		queryRole = systemService.getRoleById(role.getId());
		Assert.assertEquals("enableflg为false", Boolean.FALSE, queryRole.getEnableFlg());
		Assert.assertEquals("功能列表长度为0", 0, queryRole.getFunctions().size());
		Function queryFunction = systemService.getFunctionById(testFun.getId());
		Assert.assertNotNull("权限不为空", queryFunction);
		Assert.assertEquals("角色列表长度为0", 0, queryFunction.getRoles().size());

		List<Role> roleList = systemService.getRoleList();
		Assert.assertEquals("角色列表长度为0", 0, roleList.size());
	}

	@Test
	public void test获得所有有效角色() {
		Role roleVo = new Role();
		roleVo.setRoleName("角色名称1");
		roleVo.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role = systemService.saveRole(roleVo, funVoList);

		List<Role> roleList = systemService.getRoleList();
		Assert.assertEquals("角色列表长度为1", 1, roleList.size());

		systemService.deleteRole(role.getId());
		roleList = systemService.getRoleList();
		Assert.assertEquals("角色列表长度为0", 0, roleList.size());
	}

	@Test
	public void test增加AND查询机构() {
		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		Organization orgVo2 = new Organization();
		orgVo2.setOrgName("机构名称1");
		orgVo2.setOrgAddress("上海市浦东新区");
		orgVo2.setOrgMemo("机构描述1");
		orgVo2.setParentOrganization(org1);
		Organization org2 = systemService.saveOrganization(orgVo2);

		Organization queryOrg1 = systemService.getOrgById(org1.getId());
		Assert.assertNotNull("机构不为空", queryOrg1);
		Assert.assertNotNull("子机构不为空", queryOrg1.getChildOrgs());
		Assert.assertEquals("子机构列表长度为1", 1, queryOrg1.getChildOrgs().size());
		Assert.assertEquals("子机构ID", org2.getId(), queryOrg1.getChildOrgs().get(0).getId());

		Organization queryOrg2 = systemService.getOrgById(org2.getId());
		Assert.assertNotNull("机构不为空", queryOrg2);
		Assert.assertNotNull("父机构不为空", queryOrg2.getParentOrganization());
		Assert.assertEquals("父机构ID", org1.getId(), queryOrg2.getParentOrganization().getId());
	}

	@Test
	public void test修改AND查询机构() {
		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		Organization orgVo2 = new Organization();
		orgVo2.setOrgName("机构名称1");
		orgVo2.setOrgAddress("上海市浦东新区");
		orgVo2.setOrgMemo("机构描述1");
		orgVo2.setParentOrganization(org1);
		Organization org2 = systemService.saveOrganization(orgVo2);

		Organization queryOrg1 = systemService.getOrgById(org1.getId());
		Assert.assertNotNull("机构不为空", queryOrg1);
		Assert.assertNotNull("子机构不为空", queryOrg1.getChildOrgs());
		Assert.assertEquals("子机构列表长度为1", 1, queryOrg1.getChildOrgs().size());
		Assert.assertEquals("子机构ID", org2.getId(), queryOrg1.getChildOrgs().get(0).getId());

		Organization queryOrg2 = systemService.getOrgById(org2.getId());
		Assert.assertNotNull("机构不为空", queryOrg2);
		Assert.assertNotNull("父机构不为空", queryOrg2.getParentOrganization());
		Assert.assertEquals("父机构ID", org1.getId(), queryOrg2.getParentOrganization().getId());

		Organization uporg2 = new Organization();
		BeanUtils.copyProperties(org2, uporg2, Organization.class);
		uporg2.setParentOrganization(null);
		uporg2.setOrgName("修改机构名称");
		systemService.updateOrganization(uporg2);

		Organization uporg1 = new Organization();
		BeanUtils.copyProperties(org1, uporg1, Organization.class);
		uporg1.setParentOrganization(org2);
		systemService.updateOrganization(uporg1);

		queryOrg1 = systemService.getOrgById(org1.getId());
		Assert.assertNotNull("机构不为空", queryOrg1);
		Assert.assertNotNull("父机构不为空", queryOrg1.getParentOrganization());
		Assert.assertEquals("父机构ID", org2.getId(), queryOrg1.getParentOrganization().getId());
		Assert.assertEquals("子机构列表长度为0", 0, queryOrg1.getChildOrgs().size());

		queryOrg2 = systemService.getOrgById(org2.getId());
		Assert.assertNotNull("机构不为空", queryOrg2);
		Assert.assertNull("父机构为空", queryOrg2.getParentOrganization());
		Assert.assertNotNull("子机构不为空", queryOrg2.getChildOrgs());
		Assert.assertEquals("子机构列表长度为1", 1, queryOrg2.getChildOrgs().size());
		Assert.assertEquals("子机构ID", org1.getId(), queryOrg2.getChildOrgs().get(0).getId());
		Assert.assertEquals("修改机构名称", queryOrg2.getOrgName());
	}

	@Test
	public void test删除AND查询机构() {
		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		Organization orgVo2 = new Organization();
		orgVo2.setOrgName("机构名称1");
		orgVo2.setOrgAddress("上海市浦东新区");
		orgVo2.setOrgMemo("机构描述1");
		orgVo2.setParentOrganization(org1);
		Organization org2 = systemService.saveOrganization(orgVo2);

		Organization queryOrg1 = systemService.getOrgById(org1.getId());
		Assert.assertNotNull("机构不为空", queryOrg1);
		Assert.assertNotNull("子机构不为空", queryOrg1.getChildOrgs());
		Assert.assertEquals("子机构列表长度为1", 1, queryOrg1.getChildOrgs().size());
		Assert.assertEquals("子机构ID", org2.getId(), queryOrg1.getChildOrgs().get(0).getId());

		Organization queryOrg2 = systemService.getOrgById(org2.getId());
		Assert.assertNotNull("机构不为空", queryOrg2);
		Assert.assertNotNull("父机构不为空", queryOrg2.getParentOrganization());
		Assert.assertEquals("父机构ID", org1.getId(), queryOrg2.getParentOrganization().getId());

		systemService.deleteOrganization(org1.getId());

		queryOrg1 = systemService.getOrgById(org1.getId());
		Assert.assertEquals("enableflg为false", Boolean.FALSE, queryOrg1.getEnableFlg());
		Assert.assertEquals("子机构列表长度为0", 0, queryOrg1.getChildOrgs().size());
		Assert.assertEquals("人员构列表长度为0", 0, queryOrg1.getUsers().size());
		queryOrg2 = systemService.getOrgById(org2.getId());
		Assert.assertNull("父机构为空", queryOrg2.getParentOrganization());
	}

	@Test
	public void test获得所有有效机构() {
		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		List<Organization> resultList = systemService.getOrgList();
		Assert.assertEquals("机构列表长度为1", 1, resultList.size());

		systemService.deleteOrganization(org1.getId());
		resultList = systemService.getOrgList();
		Assert.assertEquals("机构列表长度为0", 0, resultList.size());
	}

	@Test
	public void test增加AND查询用户() {
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role1 = systemService.saveRole(roleVo1, funVoList);
		List<Role> roleVoList = new ArrayList<Role>();
		roleVoList.add(role1);

		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);
		List<Organization> orgVoList = new ArrayList<Organization>();
		orgVoList.add(org1);

		Operator oper1 = new Operator();
		oper1.setOperAccountNo("admin");
		oper1.setOperPassword("111111");
		oper1.setOperName("admin");
		oper1.setOperGender(GenderType.MAN);
		oper1.setOperMail("admin@123.com");
		oper1.setOperType(OperatorType.MANAGER);

		Operator addOper = systemService.saveUser(oper1, orgVoList, roleVoList);

		Operator queryOper = systemService.getOperById(addOper.getId());
		Assert.assertNotNull("用户不为空", queryOper);
		Assert.assertNotNull("机构列表不为空", queryOper.getOrganizations());
		Assert.assertNotNull("角色列表不为空", queryOper.getRoles());
		Assert.assertEquals("机构列表长度为1", 1, queryOper.getOrganizations().size());
		Assert.assertEquals("角色列表长度为1", 1, queryOper.getRoles().size());
		Assert.assertEquals("机构ID", org1.getId(), queryOper.getOrganizations().get(0).getId());
		Assert.assertEquals("角色ID", role1.getId(), queryOper.getRoles().get(0).getId());
	}

	@Test
	public void test修改AND查询用户() {
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role1 = systemService.saveRole(roleVo1, funVoList);

		Role roleVo2 = new Role();
		roleVo2.setRoleName("角色名称2");
		roleVo2.setRoleMemo("角色说明2");
		List<Function> funVoList2 = new ArrayList<Function>();
		funVoList2.add(testFun);
		Role role2 = systemService.saveRole(roleVo2, funVoList2);

		List<Role> roleVoList = new ArrayList<Role>();
		roleVoList.add(role1);

		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		Organization orgVo2 = new Organization();
		orgVo2.setOrgName("机构名称1");
		orgVo2.setOrgAddress("上海市浦东新区");
		orgVo2.setOrgMemo("机构描述1");
		Organization org2 = systemService.saveOrganization(orgVo2);

		List<Organization> orgVoList = new ArrayList<Organization>();
		orgVoList.add(org1);

		Operator oper1 = new Operator();
		oper1.setOperAccountNo("admin");
		oper1.setOperPassword("111111");
		oper1.setOperName("admin");
		oper1.setOperGender(GenderType.MAN);
		oper1.setOperMail("admin@123.com");
		oper1.setOperType(OperatorType.MANAGER);

		Operator addOper = systemService.saveUser(oper1, orgVoList, roleVoList);

		Operator queryOper = systemService.getOperById(addOper.getId());
		Assert.assertNotNull("用户不为空", queryOper);
		Assert.assertNotNull("机构列表不为空", queryOper.getOrganizations());
		Assert.assertNotNull("角色列表不为空", queryOper.getRoles());
		Assert.assertEquals("机构列表长度为1", 1, queryOper.getOrganizations().size());
		Assert.assertEquals("角色列表长度为1", 1, queryOper.getRoles().size());
		Assert.assertEquals("机构ID", org1.getId(), queryOper.getOrganizations().get(0).getId());
		Assert.assertEquals("角色ID", role1.getId(), queryOper.getRoles().get(0).getId());

		roleVoList.clear();
		roleVoList.add(role2);
		orgVoList.clear();
		orgVoList.add(org2);
		addOper.setOperName("update111");

		Operator updOper = systemService.updateUser(addOper, orgVoList, roleVoList);

		queryOper = systemService.getOperById(updOper.getId());
		Assert.assertNotNull("用户不为空", queryOper);
		Assert.assertNotNull("机构列表不为空", queryOper.getOrganizations());
		Assert.assertNotNull("角色列表不为空", queryOper.getRoles());
		Assert.assertEquals("update111", addOper.getOperName());
		Assert.assertEquals(updOper.getId(), addOper.getId());
		Assert.assertEquals("机构列表长度为1", 1, queryOper.getOrganizations().size());
		Assert.assertEquals("角色列表长度为1", 1, queryOper.getRoles().size());
		Assert.assertEquals("机构ID", org2.getId(), queryOper.getOrganizations().get(0).getId());
		Assert.assertEquals("角色ID", role2.getId(), queryOper.getRoles().get(0).getId());
	}

	@Test
	public void test删除AND查询用户() {
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role1 = systemService.saveRole(roleVo1, funVoList);

		List<Role> roleVoList = new ArrayList<Role>();
		roleVoList.add(role1);

		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		List<Organization> orgVoList = new ArrayList<Organization>();
		orgVoList.add(org1);

		Operator oper1 = new Operator();
		oper1.setOperAccountNo("admin");
		oper1.setOperPassword("111111");
		oper1.setOperName("admin");
		oper1.setOperGender(GenderType.MAN);
		oper1.setOperMail("admin@123.com");
		oper1.setOperType(OperatorType.MANAGER);

		Operator addOper = systemService.saveUser(oper1, orgVoList, roleVoList);

		Operator queryOper = systemService.getOperById(addOper.getId());
		Assert.assertNotNull("用户不为空", queryOper);
		Assert.assertNotNull("机构列表不为空", queryOper.getOrganizations());
		Assert.assertNotNull("角色列表不为空", queryOper.getRoles());
		Assert.assertEquals("机构列表长度为1", 1, queryOper.getOrganizations().size());
		Assert.assertEquals("角色列表长度为1", 1, queryOper.getRoles().size());
		Assert.assertEquals("机构ID", org1.getId(), queryOper.getOrganizations().get(0).getId());
		Assert.assertEquals("角色ID", role1.getId(), queryOper.getRoles().get(0).getId());

		Role queryRole = systemService.getRoleById(role1.getId());
		Assert.assertEquals(true, queryRole.getUsers().contains(queryOper));

		Organization queryOrg = systemService.getOrgById(org1.getId());
		Assert.assertEquals(true, queryOrg.getUsers().contains(queryOper));

		systemService.deleteUser(addOper.getId());

		queryOper = systemService.getOperById(addOper.getId());
		Assert.assertEquals(Boolean.FALSE, queryOper.getEnableFlg());
		Assert.assertEquals("机构列表长度为0", 0, queryOper.getOrganizations().size());
		Assert.assertEquals("角色列表长度为0", 0, queryOper.getRoles().size());

		queryRole = systemService.getRoleById(role1.getId());
		Assert.assertEquals(false, queryRole.getUsers().contains(queryOper));

		queryOrg = systemService.getOrgById(org1.getId());
		Assert.assertEquals(false, queryOrg.getUsers().contains(queryOper));
	}

	@Test
	public void test获得所有有效用户() {
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role1 = systemService.saveRole(roleVo1, funVoList);

		List<Role> roleVoList = new ArrayList<Role>();
		roleVoList.add(role1);

		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		List<Organization> orgVoList = new ArrayList<Organization>();
		orgVoList.add(org1);

		Operator oper1 = new Operator();
		oper1.setOperAccountNo("admin");
		oper1.setOperPassword("111111");
		oper1.setOperName("admin");
		oper1.setOperGender(GenderType.MAN);
		oper1.setOperMail("admin@123.com");
		oper1.setOperType(OperatorType.MANAGER);

		Operator addOper = systemService.saveUser(oper1, orgVoList, roleVoList);

		List<Operator> resultList = systemService.getOperList();
		Assert.assertEquals("用户列表长度为1", 1, resultList.size());

		systemService.deleteUser(addOper.getId());

		resultList = systemService.getOperList();
		Assert.assertEquals("用户列表长度为0", 0, resultList.size());
	}

	@Test
	public void test根据登录账号获得用户信息() {
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role1 = systemService.saveRole(roleVo1, funVoList);

		List<Role> roleVoList = new ArrayList<Role>();
		roleVoList.add(role1);

		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		List<Organization> orgVoList = new ArrayList<Organization>();
		orgVoList.add(org1);

		Operator oper1 = new Operator();
		oper1.setOperAccountNo("admin");
		oper1.setOperPassword("111111");
		oper1.setOperName("admin");
		oper1.setOperGender(GenderType.MAN);
		oper1.setOperMail("admin@123.com");
		oper1.setOperType(OperatorType.MANAGER);

		systemService.saveUser(oper1, orgVoList, roleVoList);

		List<Operator> resultList = systemService.getOperByAccount("admin");
		Assert.assertEquals("用户列表长度为1", 1, resultList.size());
		Assert.assertEquals("admin", resultList.get(0).getOperName());
		Assert.assertEquals("角色名称1", resultList.get(0).getRoles().get(0).getRoleName());
		Assert.assertEquals("机构名称1", resultList.get(0).getOrganizations().get(0).getOrgName());
		Assert.assertEquals("system/testUrl1", resultList.get(0).getRoles().get(0).getFunctions().get(0).getFunUrl());
	}

	@Test
	public void test角色分页查询() {
		Role roleVo = new Role();
		roleVo.setRoleName("角色名称1");
		roleVo.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		systemService.saveRole(roleVo, funVoList);

		PageParam pageParam = new PageParam();
		Role criteria = new Role();
		criteria.setRoleName("角色名称1");

		PageData<Role> result = systemService.queryPageRoleList(pageParam, criteria);
		assertEquals("匹配角色名称，查询结果", 1, result.getTotal());

		criteria = new Role();
		criteria.setRoleName("角色名称2");

		result = systemService.queryPageRoleList(pageParam, criteria);
		assertEquals("不匹配角色名称，查询结果", 0, result.getTotal());

		criteria = new Role();
		result = systemService.queryPageRoleList(pageParam, criteria);
		assertEquals("不传角色名称，查询结果", 1, result.getTotal());
	}

	@Test
	public void test根据名称查询角色信息() {
		Role roleVo = new Role();
		roleVo.setRoleName("角色名称1");
		roleVo.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		systemService.saveRole(roleVo, funVoList);

		List<Role> roleList = systemService.getRolesByName("角色名称1");
		Assert.assertNotNull("角色不为空", roleList);
		Assert.assertEquals("角色列表长度为1", 1, roleList.size());

		roleList = systemService.getRolesByName("角色名称2");
		Assert.assertEquals("角色列表长度为0", 0, roleList.size());
	}

	@Test
	public void test用户分页查询() {
		// 添加2个机构
		Organization orgVo1 = new Organization();
		orgVo1.setOrgName("机构名称1");
		orgVo1.setOrgAddress("上海市浦东新区1");
		orgVo1.setOrgMemo("机构描述1");
		Organization org1 = systemService.saveOrganization(orgVo1);

		Organization orgVo2 = new Organization();
		orgVo2.setOrgName("机构名称2");
		orgVo2.setOrgAddress("上海市浦东新区2");
		orgVo2.setOrgMemo("机构描述2");
		Organization org2 = systemService.saveOrganization(orgVo2);

		// 创建两个角色
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		Role role1 = systemService.saveRole(roleVo1, funVoList);

		Role roleVo2 = new Role();
		roleVo2.setRoleName("角色名称1");
		roleVo2.setRoleMemo("角色说明1");
		List<Function> funVoListTwo = new ArrayList<Function>();
		funVoListTwo.add(testFun);
		Role role2 = systemService.saveRole(roleVo2, funVoListTwo);

		// 创建第一个用户
		Operator roperVo1 = new Operator();
		roperVo1.setOperAccountNo("test1");
		roperVo1.setOperName("主管");
		roperVo1.setOperPassword("111111");
		roperVo1.setOperGender(GenderType.MAN);
		roperVo1.setOperType(OperatorType.MANAGER);
		roperVo1.setOperPhone("13012345678");
		roperVo1.setOperMail("123@123.com");
		List<Organization> orgList1 = new ArrayList<Organization>();
		orgList1.add(org1);
		orgList1.add(org2);
		List<Role> roleList1 = new ArrayList<Role>();
		roleList1.add(role1);
		roleList1.add(role2);
		Operator operator1 = systemService.saveUser(roperVo1, orgList1, roleList1);
		// 创建第二个用户
		Operator roperVo2 = new Operator();
		roperVo2.setOperAccountNo("test2");
		roperVo2.setOperName("员工22");
		roperVo2.setOperPassword("111111");
		roperVo2.setOperGender(GenderType.MAN);
		roperVo2.setOperType(OperatorType.NORMAL);
		roperVo2.setOperPhone("13112345678");
		roperVo2.setOperMail("456@123.com");
		List<Organization> orgList2 = new ArrayList<Organization>();
		orgList2.add(org1);
		List<Role> roleList2 = new ArrayList<Role>();
		roleList2.add(role1);
		Operator operator2 = systemService.saveUser(roperVo2, orgList2, roleList2);
		// 创建第三个用户
		Operator roperVo3 = new Operator();
		roperVo3.setOperAccountNo("test3");
		roperVo3.setOperName("员工33");
		roperVo3.setOperPassword("111111");
		roperVo3.setOperGender(GenderType.WOMEN);
		roperVo3.setOperType(OperatorType.NORMAL);
		roperVo3.setOperPhone("13212345678");
		roperVo3.setOperMail("789@123.com");
		List<Organization> orgList3 = new ArrayList<Organization>();
		orgList3.add(org2);
		List<Role> roleList3 = new ArrayList<Role>();
		roleList3.add(role2);
		Operator operator3 = systemService.saveUser(roperVo3, orgList3, roleList3);

		PageParam pageParam = new PageParam();
		Operator criteria = new Operator();

		criteria.setOperAccountNo("test1");
		PageData<Operator> result = systemService.queryPageOperatorList(pageParam, criteria);
		assertEquals("匹配用户登录名称，查询结果", 1, result.getTotal());

		criteria = new Operator();
		criteria.setOperName("员工");
		result = systemService.queryPageOperatorList(pageParam, criteria);
		assertEquals("匹配用户姓名，查询结果", 2, result.getTotal());

		criteria = new Operator();
		criteria.setOperType(OperatorType.NORMAL);
		result = systemService.queryPageOperatorList(pageParam, criteria);
		assertEquals("匹配用户类别，查询结果", 2, result.getTotal());

		criteria = new Operator();
		criteria.setOperMail("13112341234");
		result = systemService.queryPageOperatorList(pageParam, criteria);
		assertEquals("匹配联系电话，查询结果", 0, result.getTotal());

	}
}
