package com.thd.base.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.thd.base.security.model.ResourceDetails;
import com.thd.base.security.model.SecurityResource;
import com.thd.system.model.Function;
import com.thd.system.model.Operator;
import com.thd.system.model.Role;
import com.thd.system.service.SystemService;

public class ThdUserDetailService implements UserDetailsService {
    private final String msg = "<<<<<<<<<<<<<<<<<<------------------->>>>>>>>>>>>>>>>>>";
    private final String rolePrefix = "ROLE_";

    @Autowired
    private SystemService systemService;

    /**
     * 根据用户名，登录处理
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(msg + "登录名:" + username);
        // 根据用户名查询出用户基本信息、权限
        List<Operator> operList = systemService.getOperByAccount(username);
        if (operList == null || operList.size() != 1) {
            throw new UsernameNotFoundException("用户" + username + "不存在!");
        }
        Operator operator = operList.get(0);

        // 权限取得
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        if (operator.getRoles() != null && operator.getRoles().size() > 0) {
            for (Role r : operator.getRoles()) {
                if (r.getFunctions() != null && r.getFunctions().size() > 0) {
                    for (Function f : r.getFunctions()) {
                        //String roleName = rolePrefix + f.getId();
                        //我改的
                        String roleName = rolePrefix + f.getFunUrl();
                        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
                        auths.add(authority);
                    }
                }
            }
        }

        // 构造UserDetails
        /*boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;*/

        return new User(operator.getOperAccountNo(), operator.getOperPassword(), true, true, true, true, auths);
    }

    /**
     * 取得所有权限
     */
    public List<ResourceDetails> findAuthority() {
        // 去数据库中查询出所有权限
        List<Function> functions = systemService.getFunctionList();

        return getResourceByPrivResource(functions);
    }

    /**
     * 权限名称拼上ROLE_（ROLE_ + roleName）
     *
     * @param roleName
     */
    public String formatRoleName(String roleName) {
        return rolePrefix + roleName;
    }

    private List<ResourceDetails> getResourceByPrivResource(List<Function> funs) {
        List<ResourceDetails> result = new ArrayList<ResourceDetails>();
        for (Function fun : funs) {
            //String roleName = rolePrefix + String.valueOf(fun.getId());// .toUpperCase();
            //我改的
            String roleName = rolePrefix + String.valueOf(fun.getFunUrl());
            GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
            result.add(new SecurityResource(fun.getFunUrl(), SecurityResource.RESOURCE_TYPE_URL, authority));
        }

        return result;
    }

}
