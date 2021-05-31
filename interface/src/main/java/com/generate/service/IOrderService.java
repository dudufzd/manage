package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Order;
import com.generate.util.Result;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IOrderService extends IService<Order> {

    Result submit(Map<String, String> map) throws ParseException;

    Integer todayOrderNumber() throws Exception;

    Integer todayVisitsNumber() throws Exception;

    Integer thisWeekOrderNumber() throws Exception;

    Integer thisWeekVisitsNumber() throws Exception;

    Integer thisMonthOrderNumber() throws Exception;

    Integer thisMonthVisitsNumber() throws Exception;

    List<Map<String ,Object>> hotSetmeal();
}
