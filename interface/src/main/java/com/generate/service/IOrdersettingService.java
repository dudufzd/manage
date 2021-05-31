package com.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generate.pojo.Ordersetting;
import com.generate.util.MyCalendar;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
public interface IOrdersettingService extends IService<Ordersetting> {

    void saveList(List<Ordersetting> list);

    List<MyCalendar> listOrdersetting(String date);

    void update(String date, Integer number);
}
