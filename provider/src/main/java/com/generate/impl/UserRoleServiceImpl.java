package com.generate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.UserRoleMapper;
import com.generate.pojo.UserRole;
import com.generate.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
