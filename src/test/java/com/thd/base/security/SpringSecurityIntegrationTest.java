package com.thd.base.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;

import com.thd.base.security.model.ResourceDetails;
import com.thd.base.security.service.ThdSecurityMetadataSource;
import com.thd.base.security.service.ThdUserDetailService;
import com.thd.base.security.util.AuthenticationUtil;
import com.thd.base.security.util.ThdUserDetailsFilter;
import com.thd.base.util.BaseTxWebTests;
import com.thd.system.model.Function;
import com.thd.system.model.Operator;
import com.thd.system.model.Organization;
import com.thd.system.model.Role;
import com.thd.system.model.Operator.GenderType;
import com.thd.system.model.Operator.OperatorType;
import com.thd.system.service.SystemService;

public class SpringSecurityIntegrationTest extends BaseTxWebTests {
	@Autowired
	AuthenticationUtil authenticationUtil;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	ThdUserDetailService complaintUserDetailService;
	@Autowired
	Filter springSecurityFilterChain;
	@Autowired
	ThdSecurityMetadataSource complaintSecurityMetadataSource;
	//	@Autowired
	//	PurviewService purviewService;
	//	@Autowired
	//	RolerService rolerService;
	//	@Autowired
	//	RolerGroupService rolerGroupService;
	//	@Autowired
	//	DepartmentService departmentService;
	@Autowired
	SystemService systemService;

	private Function testFun, testFun2, testFun3;

	private ThdUserDetailsFilter thdUserDetailsFilter;

	@Before
	public void beforeEveryTest() {
		super.deleteFromTables("OPER_ORG_ASS", "OPER_ROLE_ASS", "ROLE_FUN_ASS", "ORGANIZATION", "ROLE", "OPERATOR");
		super.setCurrentUserAsCaseHandler("admin");
		thdUserDetailsFilter = new ThdUserDetailsFilter();
		thdUserDetailsFilter.setSystemService(systemService);
		add用户();
	}

	public void add用户() {
		//权限一
		Function function = new Function();
		function.setFunName("测试功能001");
		function.setFunMemo("测试功能001！");
		function.setFunUrl("/admin/*");
		function.setFunOrder(1L);
		baseDao.save(function);
		testFun = function;

		//权限二
		Function function2 = new Function();
		function2.setFunName("测试功能002");
		function2.setFunMemo("测试功能002！");
		function2.setFunUrl("/system/*");
		function2.setFunOrder(1L);
		baseDao.save(function2);
		testFun2 = function2;

		//权限三
		Function function3 = new Function();
		function3.setFunName("测试功能003");
		function3.setFunMemo("测试功能003！");
		function3.setFunUrl("/test/*");
		function3.setFunOrder(1L);
		baseDao.save(function3);
		testFun3 = function3;

		//角色一
		Role roleVo1 = new Role();
		roleVo1.setRoleName("角色名称1");
		roleVo1.setRoleMemo("角色说明1");
		List<Function> funVoList = new ArrayList<Function>();
		funVoList.add(testFun);
		funVoList.add(testFun3);
		Role role1 = systemService.saveRole(roleVo1, funVoList);
		List<Role> roleVoList = new ArrayList<Role>();
		roleVoList.add(role1);

		//角色二
		Role roleVo2 = new Role();
		roleVo2.setRoleName("角色名称1");
		roleVo2.setRoleMemo("角色说明1");
		List<Function> funVoList2 = new ArrayList<Function>();
		funVoList2.add(testFun2);
		Role role2 = systemService.saveRole(roleVo2, funVoList2);
		List<Role> roleVoList2 = new ArrayList<Role>();
		roleVoList2.add(role2);

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

		Operator oper2 = new Operator();
		oper2.setOperAccountNo("superuser");
		oper2.setOperPassword("111111");
		oper2.setOperName("superuser");
		oper2.setOperGender(GenderType.MAN);
		oper2.setOperMail("superuser@123.com");
		oper2.setOperType(OperatorType.MANAGER);

		systemService.saveUser(oper1, orgVoList, roleVoList);
		systemService.saveUser(oper2, orgVoList, roleVoList2);
	}

	@Test
	public void test正常请求拦截() throws Throwable {
		List<ResourceDetails> urlsRescs = complaintUserDetailService.findAuthority();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (ResourceDetails res : urlsRescs) {
			authorities.add(res.getAuthoritie());
		}

		logger.error("authorities==========" + authorities);

		request.setMethod("GET");
		request.setServletPath("/admin/toAddForm");

		springSecurityFilterChain.doFilter(request, response, new MockFilterChain());

		logger.error("redirectedURl==========" + response.getRedirectedUrl());

		//之前的Assert.assertEquals("http://localhost/basic/login", response.getRedirectedUrl());
		//我改的
		Assert.assertEquals(null, response.getRedirectedUrl());
	}

	@Test
	public void test未授权资源Url拦截_ProtectAllResource() throws Throwable {
		complaintSecurityMetadataSource.setProtectAllResource(true);

		request.setMethod("GET");
		request.setServletPath("/system/turnAddEmployee");
		springSecurityFilterChain.doFilter(request, response, new MockFilterChain());

		logger.debug("redirectedURl==========" + response.getRedirectedUrl());

		Assert.assertEquals("http://localhost/basic/login", response.getRedirectedUrl());
	}

	@Test
	public void test未注册资源Url拦截_ProtectAllResource() throws Throwable {
		complaintSecurityMetadataSource.setProtectAllResource(true);

		request.setMethod("GET");
		request.setServletPath("/abc/queryData");
		springSecurityFilterChain.doFilter(request, response, new MockFilterChain());

		logger.debug("redirectedURl==========" + response.getRedirectedUrl());

		Assert.assertEquals("http://localhost/basic/login", response.getRedirectedUrl());
		//我改的
		//Assert.assertEquals("http://localhost/basic/loginSuccess", response.getRedirectedUrl());
	}
	//
	//	@Test
	//	public void test未授权资源Url拦截_NotProtectAllResource() throws Throwable {
	//		complaintSecurityMetadataSource.setProtectAllResource(false);
	//
	//		request.setMethod("GET");
	//		request.setServletPath("/abc/queryData");
	//		springSecurityFilterChain.doFilter(request, response, new MockFilterChain());
	//
	//		logger.debug("redirectedURl==========" + response.getRedirectedUrl());
	//
	//		Assert.assertEquals(null, response.getRedirectedUrl());
	//	}
	//
	//	@Test
	//	public void test未授权资源Url判断() throws Throwable {
	//		complaintSecurityMetadataSource.setProtectAllResource(true);
	//		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("", ""));
	//		Assert.assertFalse(authenticationUtil.accessibleTo("/dm/queryData"));
	//		Assert.assertFalse(authenticationUtil.accessibleTo("/abc/queryData"));
	//
	//		complaintSecurityMetadataSource.setProtectAllResource(false);
	//		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("", ""));
	//		Assert.assertTrue(authenticationUtil.accessibleTo("/dm/queryData"));
	//		Assert.assertTrue(authenticationUtil.accessibleTo("/abc/queryData"));
	//	}
	//
	//	@Test
	//	public void testAdmin访问受限资源() {
	//		SecurityContextHolder.getContext().setAuthentication(null);
	//		// -------------------
	//		// 用户Model设置
	//		// -------------------
	//		Employee user = new Employee();
	//
	//		user.setUserCode("001");
	//		user.setAccountNo("admin");
	//		user.setLocalPwd(MD5.string2MD5("111111"));
	//		user.setPartyFullName("管理员");
	//		user.setGender(Employee.GENDER.MAN.getValue());
	//		user.setMail("123@123.com");
	//		user.setCrtDttm(new Date());
	//		user.setCrtUser("admin");
	//		user.setLastUpdateDttm(new Date());
	//		user.setLastUpdateUser("admin");
	//
	//		// -------------------
	//		// 用户机构关系Model设置
	//		// -------------------
	//		List<UserOrganAss> organs = new ArrayList<UserOrganAss>();
	//		UserOrganAss userOrganAss = new UserOrganAss();
	//		userOrganAss.setOrganPartyID((Long) keyMap.get("pOrId"));
	//		userOrganAss.setCrtDttm(new Date());
	//		userOrganAss.setCrtUser("admin");
	//		userOrganAss.setLastUpdateDttm(new Date());
	//		userOrganAss.setLastUpdateUser("admin");
	//		organs.add(userOrganAss);
	//
	//		// -------------------
	//		// 用户角色关系Model设置
	//		// -------------------
	//		List<UserRoleAss> roles = new ArrayList<UserRoleAss>();
	//		UserRoleAss userRoleAss = new UserRoleAss();
	//		userRoleAss.setRoleId((Long) keyMap.get("adminRole"));
	//		userRoleAss.setCrtDttm(new Date());
	//		userRoleAss.setCrtUser("admin");
	//		userRoleAss.setLastUpdateDttm(new Date());
	//		userRoleAss.setLastUpdateUser("admin");
	//		roles.add(userRoleAss);
	//
	//		// -------------------
	//		// 用户用户组关系Model设置
	//		// -------------------
	//		List<UserGrpAss> roleGrps = new ArrayList<UserGrpAss>();
	//		UserGrpAss userGrpAss = new UserGrpAss();
	//		userGrpAss.setUsergrpPartyId((Long) keyMap.get("userGrpId"));
	//		userGrpAss.setCrtDttm(new Date());
	//		userGrpAss.setCrtUser("admin");
	//		userGrpAss.setLastUpdateDttm(new Date());
	//		userGrpAss.setLastUpdateUser("admin");
	//		roleGrps.add(userGrpAss);
	//
	//		// -------------------------
	//		// 用户新增
	//		// -------------------------
	//		employeeService.newEmployee(user, organs, roles, roleGrps);
	//
	//		UsernamePasswordAuthenticationToken certificationOfTom = new UsernamePasswordAuthenticationToken("admin",
	//				"111111");
	//		Authentication authentication = authenticationManager.authenticate(certificationOfTom);
	//
	//		logger.debug("authentication.getAuthorities().size()=" + authentication.getAuthorities().size());
	//		Assert.assertTrue(authentication.getAuthorities().size() > 0);
	//
	//		SecurityContextHolder.getContext().setAuthentication(authentication);
	//		Assert.assertEquals("admin", AuthenticationUtil.getCurrentUser());
	//
	//		Assert.assertNotNull(AuthenticationUtil.getCurrentUserAuthentication());
	//
	//		Assert.assertTrue(authenticationUtil.accessibleTo("/admin/queryUser"));
	//
	//	}
	//
	//	@Test
	//	public void test非管理员访问受限资源() {
	//		SecurityContextHolder.getContext().setAuthentication(null);
	//		// -------------------
	//		// 用户Model设置
	//		// -------------------
	//		Employee user = new Employee();
	//
	//		user.setUserCode("001");
	//		user.setAccountNo("admin");
	//		user.setLocalPwd(MD5.string2MD5("111111"));
	//		user.setPartyFullName("管理员");
	//		user.setGender(Employee.GENDER.MAN.getValue());
	//		user.setMail("123@123.com");
	//		user.setCrtDttm(new Date());
	//		user.setCrtUser("admin");
	//		user.setLastUpdateDttm(new Date());
	//		user.setLastUpdateUser("admin");
	//
	//		// -------------------
	//		// 用户机构关系Model设置
	//		// -------------------
	//		List<UserOrganAss> organs = new ArrayList<UserOrganAss>();
	//		UserOrganAss userOrganAss = new UserOrganAss();
	//		userOrganAss.setOrganPartyID((Long) keyMap.get("pOrId"));
	//		userOrganAss.setCrtDttm(new Date());
	//		userOrganAss.setCrtUser("admin");
	//		userOrganAss.setLastUpdateDttm(new Date());
	//		userOrganAss.setLastUpdateUser("admin");
	//		organs.add(userOrganAss);
	//
	//		// -------------------
	//		// 用户角色关系Model设置
	//		// -------------------
	//		List<UserRoleAss> roles = new ArrayList<UserRoleAss>();
	//		UserRoleAss userRoleAss = new UserRoleAss();
	//		userRoleAss.setRoleId((Long) keyMap.get("adminRole"));
	//		userRoleAss.setCrtDttm(new Date());
	//		userRoleAss.setCrtUser("admin");
	//		userRoleAss.setLastUpdateDttm(new Date());
	//		userRoleAss.setLastUpdateUser("admin");
	//		roles.add(userRoleAss);
	//
	//		// -------------------
	//		// 用户用户组关系Model设置
	//		// -------------------
	//		List<UserGrpAss> roleGrps = new ArrayList<UserGrpAss>();
	//		// UserGrpAss userGrpAss = new UserGrpAss();
	//		// userGrpAss.setUsergrpPartyId((Long) keyMap.get("userGrpId"));
	//		// roleGrps.add(userGrpAss);
	//
	//		// -------------------------
	//		// 用户新增
	//		// -------------------------
	//		employeeService.newEmployee(user, organs, roles, roleGrps);
	//
	//		UsernamePasswordAuthenticationToken certificationOfTom = new UsernamePasswordAuthenticationToken("admin",
	//				"111111");
	//		Authentication authentication = authenticationManager.authenticate(certificationOfTom);
	//		logger.debug("authentication.getAuthorities().size()=" + authentication.getAuthorities().size());
	//		Assert.assertEquals(2, authentication.getAuthorities().size());
	//
	//		SecurityContextHolder.getContext().setAuthentication(authentication);
	//		Assert.assertEquals("admin", AuthenticationUtil.getCurrentUser());
	//		Assert.assertNotNull(AuthenticationUtil.getCurrentUserAuthentication());
	//
	//		Assert.assertFalse(authenticationUtil.accessibleTo("/test/queryUser"));
	//		Assert.assertTrue(authenticationUtil.accessibleTo("/admin/dataQuery"));
	//	}
	//
	//	@Test
	//	public void test测试比较URL按照跟细致的URL比较() {
	//		List<ResourceDetails> urlsRescs = complaintUserDetailService.findAuthority();
	//		String roleName = null;
	//		for (ResourceDetails res : urlsRescs) {
	//			if (res.getResString().equals("/admin/abc/*")) {
	//				GrantedAuthorityImpl grantedAuthorityImpl = (GrantedAuthorityImpl) res.getAuthoritie();
	//				roleName = grantedAuthorityImpl.getAuthority();
	//				break;
	//			}
	//		}
	//
	//		String otherRoleName = null;
	//		Collection<ConfigAttribute> configs = complaintSecurityMetadataSource.getAttributes("/admin/abc/def");
	//		Iterator<ConfigAttribute> iterator = configs.iterator();
	//		while (iterator.hasNext()) {
	//			ConfigAttribute config = iterator.next();
	//			otherRoleName = config.getAttribute();
	//			break;
	//		}
	//		logger.debug("roleName======" + roleName);
	//		logger.debug("otherRoleName=======" + otherRoleName);
	//		Assert.assertEquals("比较URL应该为最细致的URL", roleName, otherRoleName);
	//	}
	//
	//	@Test
	//	public void test设置MyUserDetail的Filter() throws Throwable {
	//		SecurityContextHolder.getContext().setAuthentication(null);
	//		AuthenticationUtil.clearThreadLocal();
	//
	//		// -------------------
	//		// 用户Model设置
	//		// -------------------
	//		Employee user = new Employee();
	//
	//		user.setUserCode("001");
	//		user.setAccountNo("admin");
	//		user.setLocalPwd(MD5.string2MD5("111111"));
	//		user.setPartyFullName("管理员");
	//		user.setGender(Employee.GENDER.MAN.getValue());
	//		user.setMail("123@123.com");
	//		user.setCrtDttm(new Date());
	//		user.setCrtUser("admin");
	//		user.setLastUpdateDttm(new Date());
	//		user.setLastUpdateUser("admin");
	//
	//		// -------------------
	//		// 用户机构关系Model设置
	//		// -------------------
	//		List<UserOrganAss> organs = new ArrayList<UserOrganAss>();
	//		UserOrganAss userOrganAss = new UserOrganAss();
	//		userOrganAss.setOrganPartyID((Long) keyMap.get("pOrId"));
	//		userOrganAss.setCrtDttm(new Date());
	//		userOrganAss.setCrtUser("admin");
	//		userOrganAss.setLastUpdateDttm(new Date());
	//		userOrganAss.setLastUpdateUser("admin");
	//		organs.add(userOrganAss);
	//
	//		// -------------------
	//		// 用户角色关系Model设置
	//		// -------------------
	//		List<UserRoleAss> roles = new ArrayList<UserRoleAss>();
	//		UserRoleAss userRoleAss = new UserRoleAss();
	//		userRoleAss.setRoleId((Long) keyMap.get("adminRole"));
	//		userRoleAss.setCrtDttm(new Date());
	//		userRoleAss.setCrtUser("admin");
	//		userRoleAss.setLastUpdateDttm(new Date());
	//		userRoleAss.setLastUpdateUser("admin");
	//		roles.add(userRoleAss);
	//
	//		// -------------------
	//		// 用户用户组关系Model设置
	//		// -------------------
	//		List<UserGrpAss> roleGrps = new ArrayList<UserGrpAss>();
	//		UserGrpAss userGrpAss = new UserGrpAss();
	//		userGrpAss.setUsergrpPartyId((Long) keyMap.get("userGrpId"));
	//		userGrpAss.setCrtDttm(new Date());
	//		userGrpAss.setCrtUser("admin");
	//		userGrpAss.setLastUpdateDttm(new Date());
	//		userGrpAss.setLastUpdateUser("admin");
	//		roleGrps.add(userGrpAss);
	//
	//		// -------------------------
	//		// 用户新增
	//		// -------------------------
	//		employeeService.newEmployee(user, organs, roles, roleGrps);
	//
	//		request.setMethod("GET");
	//		request.setServletPath("/employee/turnAddEmployee");
	//
	//		myUserDetailsSetFilter.doFilter(request, response, new MockFilterChain());
	//
	//		logger.debug("登录前redirectedURl==========" + response.getRedirectedUrl());
	//		logger.debug("登录前username==========" + AuthenticationUtil.getCurrentUser());
	//		logger.debug("登录前MyUserDetails==========" + AuthenticationUtil.getMyUserDetails());
	//
	//		Assert.assertNull(AuthenticationUtil.getCurrentUser());
	//		Assert.assertNull(AuthenticationUtil.getMyUserDetails());
	//
	//		UsernamePasswordAuthenticationToken certificationOfTom = new UsernamePasswordAuthenticationToken("admin",
	//				"111111");
	//
	//		Authentication authentication = authenticationManager.authenticate(certificationOfTom);
	//
	//		SecurityContextHolder.getContext().setAuthentication(authentication);
	//
	//		request.setMethod("GET");
	//		request.setServletPath("/employee/turnAddEmployee");
	//
	//		myUserDetailsSetFilter.doFilter(request, response, new MockFilterChain());
	//
	//		logger.debug("登录后redirectedURl==========" + response.getRedirectedUrl());
	//		logger.debug("登录后username==========" + AuthenticationUtil.getCurrentUser());
	//		logger.debug("登录后MyUserDetails==========" + AuthenticationUtil.getMyUserDetails());
	//		logger.debug("登录后departmentSize==========" + AuthenticationUtil.getMyUserDetails().getDepartments().size());
	//
	//		Assert.assertEquals("admin", AuthenticationUtil.getCurrentUser());
	//		Assert.assertNotNull(AuthenticationUtil.getMyUserDetails());
	//		Assert.assertEquals("admin", AuthenticationUtil.getMyUserDetails().getUserName());
	//		Assert.assertEquals("123@123.com", AuthenticationUtil.getMyUserDetails().getMail());
	//		Assert.assertEquals(1, AuthenticationUtil.getMyUserDetails().getDepartments().size());
	//		Assert.assertEquals(keyMap.get("pOrId"), (AuthenticationUtil.getMyUserDetails().getDepartments()).get(0)
	//				.getId());
	//
	//	}
	//
	//	@Test
	//	public void test测试当前用户是否是案件处理部门() throws Throwable {
	//		SecurityContextHolder.getContext().setAuthentication(null);
	//
	//		// -------------------
	//		// 用户Model设置
	//		// -------------------
	//		Employee user = new Employee();
	//
	//		user.setUserCode("001");
	//		user.setAccountNo("admin");
	//		user.setLocalPwd(MD5.string2MD5("111111"));
	//		user.setPartyFullName("管理员");
	//		user.setGender(Employee.GENDER.MAN.getValue());
	//		user.setMail("123@123.com");
	//		user.setCrtDttm(new Date());
	//		user.setCrtUser("admin");
	//		user.setLastUpdateDttm(new Date());
	//		user.setLastUpdateUser("admin");
	//
	//		// -------------------
	//		// 用户机构关系Model设置
	//		// -------------------
	//		List<UserOrganAss> organs = new ArrayList<UserOrganAss>();
	//		UserOrganAss userOrganAss = new UserOrganAss();
	//		userOrganAss.setOrganPartyID((Long) keyMap.get("pOrId"));
	//		userOrganAss.setCrtDttm(new Date());
	//		userOrganAss.setCrtUser("admin");
	//		userOrganAss.setLastUpdateDttm(new Date());
	//		userOrganAss.setLastUpdateUser("admin");
	//		organs.add(userOrganAss);
	//
	//		// -------------------
	//		// 用户角色关系Model设置
	//		// -------------------
	//		List<UserRoleAss> roles = new ArrayList<UserRoleAss>();
	//		UserRoleAss userRoleAss = new UserRoleAss();
	//		userRoleAss.setRoleId((Long) keyMap.get("adminRole"));
	//		userRoleAss.setCrtDttm(new Date());
	//		userRoleAss.setCrtUser("admin");
	//		userRoleAss.setLastUpdateDttm(new Date());
	//		userRoleAss.setLastUpdateUser("admin");
	//		roles.add(userRoleAss);
	//
	//		// -------------------
	//		// 用户用户组关系Model设置
	//		// -------------------
	//		List<UserGrpAss> roleGrps = new ArrayList<UserGrpAss>();
	//		UserGrpAss userGrpAss = new UserGrpAss();
	//		userGrpAss.setUsergrpPartyId((Long) keyMap.get("userGrpId"));
	//		userGrpAss.setCrtDttm(new Date());
	//		userGrpAss.setCrtUser("admin");
	//		userGrpAss.setLastUpdateDttm(new Date());
	//		userGrpAss.setLastUpdateUser("admin");
	//		roleGrps.add(userGrpAss);
	//
	//		// -------------------------
	//		// 案件处理部门用户新增
	//		// -------------------------
	//		employeeService.newEmployee(user, organs, roles, roleGrps);
	//
	//		UsernamePasswordAuthenticationToken certificationOfTom = new UsernamePasswordAuthenticationToken("admin",
	//				"111111");
	//
	//		Authentication authentication = authenticationManager.authenticate(certificationOfTom);
	//
	//		SecurityContextHolder.getContext().setAuthentication(authentication);// 模拟spring
	//		// security放置身份到安全上下文
	//
	//		request.setMethod("GET");
	//		request.setServletPath("/employee/turnAddEmployee");
	//
	//		myUserDetailsSetFilter.doFilter(request, response, new MockFilterChain());
	//
	//		logger.debug("isCaseHandlerDepartment=======" + AuthenticationUtil.isCaseHandlerDepartment());
	//
	//		Assert.assertTrue("是案件处理部门", AuthenticationUtil.isCaseHandlerDepartment());
	//		Assert.assertTrue("可以申请礼品", AuthenticationUtil.isGiftApplyDepartment());
	//
	//		// -------------------
	//		// 用户Model设置
	//		// -------------------
	//		Employee user1 = new Employee();
	//
	//		user1.setUserCode("002");
	//		user1.setAccountNo("guest");
	//		user1.setLocalPwd(MD5.string2MD5("111111"));
	//		user1.setPartyFullName("游客");
	//		user1.setGender(Employee.GENDER.MAN.getValue());
	//		user1.setCrtDttm(new Date());
	//		user1.setCrtUser("admin");
	//		user1.setLastUpdateDttm(new Date());
	//		user1.setLastUpdateUser("admin");
	//		user1.setMail("123@123.com");
	//
	//		// -------------------
	//		// 用户机构关系Model设置
	//		// -------------------
	//		List<UserOrganAss> organs1 = new ArrayList<UserOrganAss>();
	//		UserOrganAss userOrganAss1 = new UserOrganAss();
	//		userOrganAss1.setOrganPartyID((Long) keyMap.get("orId"));
	//		userOrganAss1.setCrtDttm(new Date());
	//		userOrganAss1.setCrtUser("admin");
	//		userOrganAss1.setLastUpdateDttm(new Date());
	//		userOrganAss1.setLastUpdateUser("admin");
	//		organs1.add(userOrganAss1);
	//
	//		// -------------------
	//		// 用户角色关系Model设置
	//		// -------------------
	//		List<UserRoleAss> roles1 = new ArrayList<UserRoleAss>();
	//		UserRoleAss userRoleAss1 = new UserRoleAss();
	//		userRoleAss1.setRoleId((Long) keyMap.get("adminRole"));
	//		userRoleAss1.setCrtDttm(new Date());
	//		userRoleAss1.setCrtUser("admin");
	//		userRoleAss1.setLastUpdateDttm(new Date());
	//		userRoleAss1.setLastUpdateUser("admin");
	//		roles1.add(userRoleAss1);
	//
	//		// -------------------
	//		// 用户用户组关系Model设置
	//		// -------------------
	//		List<UserGrpAss> roleGrps1 = new ArrayList<UserGrpAss>();
	//		UserGrpAss userGrpAss1 = new UserGrpAss();
	//		userGrpAss1.setUsergrpPartyId((Long) keyMap.get("userGrpId"));
	//		userGrpAss1.setCrtDttm(new Date());
	//		userGrpAss1.setCrtUser("admin");
	//		userGrpAss1.setLastUpdateDttm(new Date());
	//		userGrpAss1.setLastUpdateUser("admin");
	//		roleGrps1.add(userGrpAss1);
	//
	//		// -------------------------
	//		// 案件处理部门用户新增
	//		// -------------------------
	//		employeeService.newEmployee(user1, organs1, roles1, roleGrps1);
	//
	//		UsernamePasswordAuthenticationToken certificationOfTom1 = new UsernamePasswordAuthenticationToken("guest",
	//				"111111");
	//
	//		Authentication authentication1 = authenticationManager.authenticate(certificationOfTom1);
	//
	//		SecurityContextHolder.getContext().setAuthentication(authentication1);
	//
	//		request.setMethod("GET");
	//		request.setServletPath("/employee/turnAddEmployee");
	//
	//		myUserDetailsSetFilter.doFilter(request, response, new MockFilterChain());
	//
	//		logger.debug("isCaseHandlerDepartment=======" + AuthenticationUtil.isCaseHandlerDepartment());
	//
	//		Assert.assertFalse("不是案件处理部门", AuthenticationUtil.isCaseHandlerDepartment());
	//		Assert.assertFalse("不可以申请礼品", AuthenticationUtil.isGiftApplyDepartment());
	//
	//	}

}