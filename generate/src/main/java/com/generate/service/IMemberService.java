package com.generate.service;

import com.generate.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IMemberService extends IService<Member> {
    PageResult listPage(QueryPageBean queryPageBean);
}
