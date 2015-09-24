--初始化权限
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (1, NOW(), 'SYSTEM', TRUE,  '总菜单', '太平洋保险在线投诉管理系统', 1, '/basic/loginSuccess', null);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (2,NOW(), 'SYSTEM', TRUE, '投诉管理', '投诉管理', 2, 'defaultUrl', 1);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (3, NOW(),'SYSTEM', TRUE, '投诉受理', '投诉受理', 3, '/workflow/complaintCase/*', 2);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (4, NOW(), 'SYSTEM', TRUE, '投诉质检', '投诉质检', 4, '/workflow/Qc/*', 2);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (5, NOW(), 'SYSTEM', TRUE, '投诉调度', '投诉调度', 5, '/workflow/scheduling/*', 2);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (6, NOW(), 'SYSTEM', TRUE, '投诉处理', '投诉处理', 6, '/workflow/handle/*', 2);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (7, NOW(), 'SYSTEM', TRUE, '投诉审核', '投诉审核', 7, '/workflow/audits/*', 2);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (8, NOW(), 'SYSTEM', TRUE, '投诉结案', '投诉结案', 8, '/workflow/closeCase/*', 2);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (9, NOW(), 'SYSTEM', TRUE, '系统管理', '系统管理', 9, 'defaultUrl', 1);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (10, NOW(), 'SYSTEM', TRUE,'机构管理', '机构管理', 10, '/system/organization/*', 9);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (11, NOW(), 'SYSTEM', TRUE,'角色管理', '角色管理', 11, '/system/role/*', 9);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (12, NOW(), 'SYSTEM', TRUE,'权限管理', '权限管理', 12, '/system/function/*', 9);
insert into FUNCTION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values (13, NOW(), 'SYSTEM', TRUE,'用户管理', '用户管理', 13, '/system/operator/*', 9);

--初始化机构
insert into ORGANIZATION (ID, CRT_DATE, CRT_USER, ENABLE_FLG,  ORG_ADDRESS, ORG_MEMO, ORG_NAME, parent_organization)
values (1, NOW(), 'SYSTEM', TRUE, '上海', '在线服务公司', '在线服务公司', null);

--初始化系统管理员
insert into OPERATOR (ID, CRT_DATE, CRT_USER, ENABLE_FLG, OPER_ACCOUNT_NO, OPER_GENDER, OPER_MAIL, OPER_NAME, OPER_PASSWORD, OPER_PHONE, OPER_TYPE, POSITION_TYPE)
values (1, NOW(), 'superuser', TRUE, 'superuser', 0, '642217323@qq.com', '超级用户', '96e79218965eb72c92a549dd5a330112', '15855182461', 1, 3);

--初始化人员机构关联
insert into operator_organizations (users, organizations)
values (1, 1);

--初始化角色
insert into ROLE (ID, CRT_DATE, CRT_USER, ENABLE_FLG, ROLE_MEMO, ROLE_NAME)
values (1, NOW(), 'superuser', TRUE,  '包含所有功能权限！', '超级角色');

--初始化角色权限关联表
insert into role_functions (roles, functions)
values (1, 1);
insert into role_functions (roles, functions)
values (1, 2);
insert into role_functions (roles, functions)
values (1, 3);
insert into role_functions (roles, functions)
values (1, 4);
insert into role_functions (roles, functions)
values (1, 5);
insert into role_functions (roles, functions)
values (1, 6);
insert into role_functions (roles, functions)
values (1, 7);
insert into role_functions (roles, functions)
values (1, 8);
insert into role_functions (roles, functions)
values (1, 9);
insert into role_functions (roles, functions)
values (1, 10);
insert into role_functions (roles, functions)
values (1, 11);
insert into role_functions (roles, functions)
values (1, 12);
insert into role_functions (roles, functions)
values (1, 13);

--初始化用户角色表
insert into operator_roles (users, roles)
values (1, 1);