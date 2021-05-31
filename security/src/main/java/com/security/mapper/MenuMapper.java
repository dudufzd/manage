package com.security.mapper;

import com.security.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-27
 */
public interface MenuMapper extends BaseMapper<Menu> {

    //根据用户ID查询菜单权限
    List<Menu> selectMenuPermsByUserId(Integer userId);
}
