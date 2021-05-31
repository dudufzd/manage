package com.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.security.mapper.MenuMapper;
import com.security.mapper.RoleMapper;
import com.security.mapper.UserMapper;
import com.security.pojo.Menu;
import com.security.pojo.Role;
import com.security.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        SysUser currentUser = userMapper.selectOne(wrapper);
        if (currentUser == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        System.out.println(currentUser);
        //获取用户角色和菜单权限
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<Role> roleList = roleMapper.selectRoleCodesByUserId(currentUser.getId());
        for (Role role : roleList) {
            //客户端使用的角色名称
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRoleCode());
            authorityList.add(authority);
        }
        List<Menu> permsList = menuMapper.selectMenuPermsByUserId(currentUser.getId());
        for (Menu perm : permsList) {
            //客户端使用的角色id
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(perm.getPerms());
            authorityList.add(authority);
        }

        System.out.println(">>>>authorityList:" + authorityList);
        return new User(currentUser.getUserName(), currentUser.getPassword(), authorityList);
    }
}
