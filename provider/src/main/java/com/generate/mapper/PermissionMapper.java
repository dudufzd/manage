package com.generate.mapper;

import com.generate.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> listByUserId(Integer id);
}
