package com.security.mapper;

import com.security.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-27
 */
public interface RoleMapper extends BaseMapper<Role> {

    //根据用户ID查询角色信息
    List<Role> selectRoleCodesByUserId(Integer userId);
}
