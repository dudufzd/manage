package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Setmeal;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface ISetmealService extends IService<Setmeal> {
    PageResult listPage(QueryPageBean queryPageBean);

    void save(Setmeal setmeal, Integer[] checkgroupIds);

    Setmeal getInfo(Integer id);

    Map<String, Object> getCountSetmeal();
}
