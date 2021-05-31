package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Checkitem;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;

/**
 * <p>
 * 检查项 服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface ICheckitemService extends IService<Checkitem> {
    PageResult listPage(QueryPageBean queryPageBean);
}
