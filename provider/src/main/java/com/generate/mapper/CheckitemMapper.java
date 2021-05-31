package com.generate.mapper;

import com.generate.pojo.Checkgroup;
import com.generate.pojo.Checkitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 检查项 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface CheckitemMapper extends BaseMapper<Checkitem> {

    Checkitem getById(Integer id);
}
