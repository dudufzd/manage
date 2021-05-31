package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Member;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import com.generate.util.SeData;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IMemberService extends IService<Member> {
    PageResult listPage(QueryPageBean queryPageBean, SeData seData);

    Map<String, Object> memberEcharts();

    Integer todayNewMember() throws Exception;

    Integer totalMember();

    Integer thisWeekNewMember() throws Exception;

    Integer thisMonthNewMember() throws Exception;

    Map<String, Object> memberClass();
}
