package com.generate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.PermissionMapper;
import com.generate.pojo.Permission;
import com.generate.service.IPermissionService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> listByUserId(Integer id) {
        return permissionMapper.listByUserId(id);
    }
}
