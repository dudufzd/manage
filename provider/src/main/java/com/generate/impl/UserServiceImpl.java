package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.UserMapper;
import com.generate.pojo.User;
import com.generate.service.IUserService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public User getByName(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }
}
