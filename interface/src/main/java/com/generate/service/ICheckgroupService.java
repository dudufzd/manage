package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Checkgroup;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;

/**
 * <p>
 * 检查组 服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface ICheckgroupService extends IService<Checkgroup> {
    void save(Checkgroup checkgroup,Integer[] checkitemids);

    void update(Checkgroup checkgroup,Integer[] checkitemids);

    PageResult listPage(QueryPageBean queryPageBean);
}
