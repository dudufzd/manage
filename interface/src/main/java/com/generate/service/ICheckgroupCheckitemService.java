package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.CheckgroupCheckitem;
import com.generate.util.Result;

/**
 * <p>
 * 检查组与检查项的中间表 服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface ICheckgroupCheckitemService extends IService<CheckgroupCheckitem> {
    Result listByCheckgroupId(Integer checkgroupId);
}
