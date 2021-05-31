package com.generate.mapper;

import com.generate.pojo.Checkgroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 检查组 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface CheckgroupMapper extends BaseMapper<Checkgroup> {

    Checkgroup getById(Integer id);
}
