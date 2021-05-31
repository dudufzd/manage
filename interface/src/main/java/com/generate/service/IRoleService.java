package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Role;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import com.generate.util.SeData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IRoleService extends IService<Role> {
    PageResult listPage(QueryPageBean queryPageBean);
}
