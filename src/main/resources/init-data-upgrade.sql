--初始化权限
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid1', NOW(), 'superuser',NOW(),'superuser' ,TRUE,  '总菜单', '拉登考试管理系统', 1, '/basic/loginSuccess', null);
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid2',NOW(), 'superuser',NOW(),'superuser', TRUE, '投诉管理', '投诉管理', 2, 'defaultUrl', 'funid1');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid3', NOW(),'superuser',NOW(),'superuser', TRUE, '投诉受理', '投诉受理', 3, '/workflow/complaintCase/*', 'funid2');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid4', NOW(), 'superuser',NOW(),'superuser', TRUE, '投诉质检', '投诉质检', 4, '/workflow/Qc/*', 'funid2');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid5', NOW(), 'superuser',NOW(),'superuser', TRUE, '投诉调度', '投诉调度', 5, '/workflow/scheduling/*', 'funid2');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid6', NOW(), 'superuser',NOW(),'superuser', TRUE, '投诉处理', '投诉处理', 6, '/workflow/handle/*', 'funid2');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid7', NOW(), 'superuser',NOW(),'superuser', TRUE, '投诉审核', '投诉审核', 7, '/workflow/audits/*', 'funid2');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid8', NOW(), 'superuser',NOW(),'superuser', TRUE, '投诉结案', '投诉结案', 8, '/workflow/closeCase/*', 'funid2');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid9', NOW(), 'superuser',NOW(),'superuser', TRUE, '系统管理', '系统管理', 9, 'defaultUrl', 'funid1');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid10', NOW(), 'superuser',NOW(),'superuser', TRUE,'机构管理', '机构管理', 10, '/system/organization/*', 'funid9');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid11', NOW(), 'superuser',NOW(),'superuser', TRUE,'角色管理', '角色管理', 11, '/system/role/*', 'funid9');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid12', NOW(), 'superuser',NOW(),'superuser', TRUE,'权限管理', '权限管理', 12, '/system/function/*', 'funid9');
insert into FUNCTION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,FUN_MEMO, FUN_NAME, FUN_ORDER, FUN_URL,parent_function)
values ('funid13', NOW(), 'superuser',NOW(),'superuser', TRUE,'用户管理', '用户管理', 13, '/system/operator/*', 'funid9');

--初始化机构
insert into ORGANIZATION (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG,  ORG_ADDRESS, ORG_MEMO, ORG_NAME, parent_organization)
values ('orgid1', NOW(), 'superuser',NOW(),'superuser', TRUE, '上海', '拉登恐怖集团', '拉登恐怖集团', null);

--初始化系统管理员
insert into OPERATOR (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG, OPER_ACCOUNT_NO, OPER_GENDER, OPER_MAIL, OPER_NAME, OPER_PASSWORD, OPER_PHONE, OPER_TYPE, POSITION_TYPE)
values ('operid1', NOW(), 'superuser', NOW(), 'superuser',TRUE, 'superuser', 0, '642217323@qq.com', '超级用户', '96e79218965eb72c92a549dd5a330112', '15855182461', 1, 3);

--初始化人员机构关联
insert into operator_organizations (users, organizations)
values ('operid1', 'orgid1');

--初始化角色
insert into ROLE (ID, CRT_DATE, CRT_USER,LAST_UPDATE_DATE,LAST_UPDATE_USER, ENABLE_FLG, ROLE_MEMO, ROLE_NAME)
values ('roleid1', NOW(), 'superuser', NOW(), 'superuser', TRUE,  '包含所有功能权限！', '超级角色');

--初始化角色权限关联表
insert into role_functions (roles, functions)
values ('roleid1', 'funid1');
insert into role_functions (roles, functions)
values ('roleid1', 'funid2');
insert into role_functions (roles, functions)
values ('roleid1', 'funid3');
insert into role_functions (roles, functions)
values ('roleid1', 'funid4');
insert into role_functions (roles, functions)
values ('roleid1', 'funid5');
insert into role_functions (roles, functions)
values ('roleid1', 'funid6');
insert into role_functions (roles, functions)
values ('roleid1', 'funid7');
insert into role_functions (roles, functions)
values ('roleid1', 'funid8');
insert into role_functions (roles, functions)
values ('roleid1', 'funid9');
insert into role_functions (roles, functions)
values ('roleid1', 'funid10');
insert into role_functions (roles, functions)
values ('roleid1', 'funid11');
insert into role_functions (roles, functions)
values ('roleid1', 'funid12');
insert into role_functions (roles, functions)
values ('roleid1', 'funid13');

--初始化用户角色表
insert into operator_roles (users, roles)
values ('operid1', 'roleid1');