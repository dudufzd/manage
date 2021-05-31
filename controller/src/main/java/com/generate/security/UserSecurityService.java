package com.generate.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.generate.pojo.Permission;
import com.generate.pojo.User;
import com.generate.service.IPermissionService;
import com.generate.service.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    @Reference
    private IUserService iUserService;
    @Reference
    private IPermissionService iPermissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.获取用户信息
        User user = iUserService.getByName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不正确");
        }
        //2.查询该用户有什么权限
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<Permission> permissionList = iPermissionService.listByUserId(user.getId());

        for(Permission permission : permissionList){
            String keyword = permission.getKeyword();
            authorityList.add(new SimpleGrantedAuthority(keyword));
        }

        System.out.println(">>>>>>>>authorityList" + authorityList);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorityList);
    }
}
