package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IPermissionService extends IService<Permission> {

    List<Permission> listByUserId(Integer id);
}
