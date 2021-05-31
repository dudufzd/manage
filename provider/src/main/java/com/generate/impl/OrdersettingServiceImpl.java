package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.OrdersettingMapper;
import com.generate.pojo.Ordersetting;
import com.generate.service.IOrdersettingService;
import com.generate.util.MyCalendar;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class OrdersettingServiceImpl extends ServiceImpl<OrdersettingMapper, Ordersetting> implements IOrdersettingService {

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void saveList(List<Ordersetting> list) {
        for(Ordersetting ordersetting : list){
            QueryWrapper<Ordersetting> queryWrapper = new QueryWrapper<Ordersetting>().eq("orderDate", ordersetting.getOrderdate());
            Ordersetting selectOne = ordersettingMapper.selectOne(queryWrapper);
            if (selectOne == null){//数据库中不存在该数据
                ordersettingMapper.insert(ordersetting);
            }else {//数据库中存在该数据,则更新可预约人数
                selectOne.setNumber(ordersetting.getNumber());
                ordersettingMapper.updateById(selectOne);
            }
        }
    }

    @Override
    public List<MyCalendar> listOrdersetting(String date) {
        //查询数据库数据
        String beginDate = date + "-01";
        String endDate = date + "-31";
        QueryWrapper<Ordersetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("orderDate", beginDate, endDate);
        List<Ordersetting> ordersettings = ordersettingMapper.selectList(queryWrapper);

        //将数据写入集合中并返回
        List<MyCalendar> list = new ArrayList<>();
        if(ordersettings != null && ordersettings.size() > 0){
            for(Ordersetting ordersetting : ordersettings){
                int day = ordersetting.getOrderdate().getDayOfMonth();
                Integer number = ordersetting.getNumber();
                Integer reservations = ordersetting.getReservations();
                list.add(new MyCalendar(day, number, reservations));
            }
        }
        return list;
    }

    @Override
    public void update(String date, Integer number) {
        //查询数据库数据
        QueryWrapper<Ordersetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderDate", date);
        Ordersetting selectOne = ordersettingMapper.selectOne(queryWrapper);

        //将数据写入
        if (selectOne == null){//数据库中不存在该数据
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, fmt);
            Ordersetting ordersetting = new Ordersetting(0, newDate, number, 0);
            ordersettingMapper.insert(ordersetting);
        }else {//数据库中存在该数据,则更新可预约人数
            selectOne.setNumber(number);
            ordersettingMapper.updateById(selectOne);
        }
    }
}
