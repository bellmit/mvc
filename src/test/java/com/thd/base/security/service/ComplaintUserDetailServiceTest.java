package com.thd.base.security.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.thd.base.util.BaseTxWebTests;
import com.thd.system.model.Operator;
import com.thd.system.model.Operator.GenderType;
import com.thd.system.model.Operator.OperatorType;
import com.thd.system.service.SystemService;

public class ComplaintUserDetailServiceTest extends BaseTxWebTests {
	@Resource
	ThdUserDetailService thdUserDetailService;

	@Resource
	SystemService systemService;

	@Before
	public void beforeEveryTest() {
		super.setCurrentUserAsCaseHandler("admin");

		Operator oper1 = new Operator();
		oper1.setOperAccountNo("admin");
		oper1.setOperPassword("111111");
		oper1.setOperName("admin");
		oper1.setOperGender(GenderType.MAN);
		oper1.setOperMail("admin@123.com");
		oper1.setOperType(OperatorType.MANAGER);

		systemService.saveUser(oper1, null, null);
	}

	@Test
	public void testLoadUserByUsername() {
		UserDetails result = thdUserDetailService.loadUserByUsername("admin");
		logger.debug(result.getUsername());
		assertEquals("admin", result.getUsername());
	}

}
