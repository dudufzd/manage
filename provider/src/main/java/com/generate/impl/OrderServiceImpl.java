package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.MemberMapper;
import com.generate.mapper.OrderMapper;
import com.generate.mapper.OrdersettingMapper;
import com.generate.pojo.Member;
import com.generate.pojo.Order;
import com.generate.pojo.Ordersetting;
import com.generate.service.IOrderService;
import com.generate.util.DateUtils;
import com.generate.util.MessageConstant;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Resource
    private MemberMapper memberMapper;

    @Override
    //map数据结构：
    //orderInfo:{
    //    setmealId:12,
    //    name:"桓拘伸",
    //    sex:"男",
    //    telephone:"18098232594",
    //    idCard:"530624196904034233",
    //    orderDate:"2021-5-30"
    //}
    //用户预约检查
    public Result submit(Map<String, String> map) throws ParseException {
//        1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行 预约
//        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
//        3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约 则无法完成再次预约
//        4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注 册并进行预约
//        5、预约成功，更新当日的已预约人数

        System.out.println("请求体map为:" + map);
        //1.查询ordersetting数据表
        QueryWrapper<Ordersetting> orsWrapper = new QueryWrapper<>();
        Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("orderDate"));
        orsWrapper.eq("orderDate", orderDate);
        Ordersetting ordersetting = ordersettingMapper.selectOne(orsWrapper);
        //查询结果为空，预约未设置，返回结果
        if(ordersetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.判断预约是否已满，满则返回结果
        if(ordersetting.getNumber() <= ordersetting.getReservations()){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3.查询用户是否为会员，如果不是会员，则自动注册
        QueryWrapper<Member> memWrapper = new QueryWrapper<>();
        memWrapper.eq("phoneNumber", map.get("telephone"));
        Member member = memberMapper.selectOne(memWrapper);
        //不是会员，则自动注册
        if(member == null){
            member = new Member();
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdcard(map.get("idCard"));
            member.setPhonenumber(map.get("telephone"));
            member.setRegtime(LocalDate.now());
            member.setPassword("123456");
            memberMapper.insert(member);
        }
        //4.查询是否已经预约
        QueryWrapper<Order> orWrapper = new QueryWrapper<>();
        orWrapper.eq("member_id",member.getId())
                .eq("orderDate",map.get("orderDate"))
                .eq("setmeal_id",map.get("setmealId"));
        Order order = orderMapper.selectOne(orWrapper);
        //有数据说明已预约
        if(order != null){
            return new Result(false, MessageConstant.HAS_ORDERED);
        }
        //5.预约成功，创建对象添加数据库数据
        Order nOrder = new Order();
        nOrder.setMemberId(member.getId());
        nOrder.setOrdertype("微信公众号预约");
        nOrder.setOrderdate(LocalDate.parse(map.get("orderDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        nOrder.setSetmealId(Integer.valueOf(map.get("setmealId")));
        //打印norder
        System.out.println(">>>>>>>>>>>>>" + nOrder);

        orderMapper.insert(nOrder);
        ordersetting.setReservations(ordersetting.getReservations()+1);
        ordersettingMapper.updateById(ordersetting);

        return new Result(true, MessageConstant.ORDER_SUCCESS);
    }

    //统计数据查询
    @Override
    public Integer todayOrderNumber() throws Exception {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        String orderDate = DateUtils.parseDate2String(DateUtils.getToday());
        System.out.println(">>>>>>>>>>今日日期:" + orderDate);
        queryWrapper.eq("orderDate", orderDate);
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer todayVisitsNumber() throws Exception {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        String orderDate = DateUtils.parseDate2String(DateUtils.getToday());
        System.out.println(">>>>>>>>>>今日日期:" + orderDate);
        queryWrapper.eq("orderDate", orderDate)
                .eq("orderStatus", "已到诊");
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer thisWeekOrderNumber() throws Exception {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Date datemonday = DateUtils.getThisWeekMonday(DateUtils.getToday());
        String monday = DateUtils.parseDate2String(datemonday);
        System.out.println(">>>>>>>>>>本周周一日期:" + monday);
        queryWrapper.between("orderDate", monday, DateUtils.getToday());
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer thisWeekVisitsNumber() throws Exception {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Date datemonday = DateUtils.getThisWeekMonday(DateUtils.getToday());
        String monday = DateUtils.parseDate2String(datemonday);
        System.out.println(">>>>>>>>>>本周周一日期:" + monday);
        queryWrapper.between("orderDate", monday, DateUtils.getToday())
                .eq("orderStatus", "已到诊");
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer thisMonthOrderNumber() throws Exception {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Date dateoneday = DateUtils.getFirstDay4ThisMonth();
        String oneday = DateUtils.parseDate2String(dateoneday);
        System.out.println(">>>>>>>>>>本月第一天日期:" + oneday);
        queryWrapper.between("orderDate", oneday, DateUtils.getToday());
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer thisMonthVisitsNumber() throws Exception {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Date dateoneday = DateUtils.getFirstDay4ThisMonth();
        String oneday = DateUtils.parseDate2String(dateoneday);
        System.out.println(">>>>>>>>>>本月第一天日期:" + oneday);
        queryWrapper.between("orderDate", oneday, DateUtils.getToday())
                .eq("orderStatus", "已到诊");
        return orderMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> hotSetmeal() {
        return orderMapper.hotSetmeal();
    }
}
