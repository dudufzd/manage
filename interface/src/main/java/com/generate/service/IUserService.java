package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IUserService extends IService<User> {

    User getByName(String username);
}
