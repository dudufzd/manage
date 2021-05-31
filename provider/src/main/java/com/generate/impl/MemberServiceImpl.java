package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.MemberMapper;
import com.generate.pojo.Member;
import com.generate.service.IMemberService;
import com.generate.util.*;
import org.apache.dubbo.config.annotation.Service;
import org.apache.poi.ss.usermodel.DateUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    //条件查询，分页查询
    public PageResult listPage(QueryPageBean queryPageBean, SeData seData) {
        //创建分页对象
        Page<Member> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        System.out.println("当前页：" + queryPageBean.getCurrentPage());
        System.out.println("页面大小：" + queryPageBean.getPageSize());
        //设置查询条件
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        //注间这里写表的列名称，不是写实全类的属性名称
        //1.1.sex为字符串
        if(seData.getSex() != null && !seData.getSex().equals("")){
            queryWrapper.eq("sex", seData.getSex());
        }
        //1.2.age为Integer数组
        if(seData.getAge().length > 0){
            Integer[] age = seData.getAge();
            String suff = new SimpleDateFormat("-MM-dd").format(new Date());
            String year = new SimpleDateFormat("yyyy").format(new Date());
            int preYear = Integer.valueOf(year) - age[0];
            String preBirthday = preYear + suff;
            //打印生日
            System.out.println("preBirthday" + preBirthday);
            queryWrapper.le("birthday",preBirthday);
            if(age.length > 1){
                int suffYear = Integer.valueOf(year) - age[1];
                String suffBirthday = suffYear + suff;
                //打印生日
                System.out.println("suffBirthday" + suffBirthday);
                queryWrapper.ge("birthday",suffBirthday);
            }
        }
        //1.3.Date为字符串
        if(seData.getDate() != null && !seData.getDate().equals("")){
            String preDate = seData.getDate() + "-01-01";
//            System.out.println(preDate);
            String suffDate = seData.getDate() + "-12-31";
//            System.out.println(suffDate);
            queryWrapper.between("regTime", preDate,suffDate);
        }

        //2.拼接姓名或电话号码
        if(queryPageBean.getQueryString()!=null && !queryPageBean.getQueryString().equals("")){//有查条件
//            queryWrapper.like("name",queryPageBean.getQueryString())
//                    .or().like("phoneNumber",queryPageBean.getQueryString());
            queryWrapper.nested(i -> i.like("name", queryPageBean.getQueryString()).or().like("phoneNumber", queryPageBean.getQueryString()));
        }
        //没有查询条件不是查全部
        Page<Member> memberPage = memberMapper.selectPage(page, queryWrapper);
        System.out.println("数据总条数" + memberPage.getTotal());
        return new PageResult(memberPage.getTotal(), memberPage.getRecords());
    }

    @Override
    //会员数据统计
    //数据格式：
    //"data":{
    //    "months":["2019.01","2019.02","2019.03","2019.04"],
    //    "memberCount":[3,4,8,10]
    //}
    public Map<String, Object> memberEcharts() {
        //数据
        List<String> months = new ArrayList<>();
        List<Integer> memberCount = new ArrayList<>();

        //1.对时间数据进行处理
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        //把时间往前12个月
        calendar.add(Calendar.MONTH, -12);

        for(int i = 0; i < 12; i++){
            calendar.add(Calendar.MONTH,1);
            Date time = calendar.getTime();

            String format = new SimpleDateFormat("yyyy-MM").format(time);
            months.add(format);
        }

        //2.对会员总数数据进行处理
        for(String month : months){
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.le("regTime", month + "-31");
            System.out.println(">>>>>>" + month + "-31");

            Integer count = memberMapper.selectCount(queryWrapper);
            memberCount.add(count);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);

        return map;
    }

    //统计数据查询
    @Override
    public Integer todayNewMember() throws Exception {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        String regTime = DateUtils.parseDate2String(DateUtils.getToday());
        System.out.println(">>>>>>>>>>今日日期:" + regTime);
        queryWrapper.eq("regTime", regTime);
        return memberMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer totalMember() {
        return memberMapper.selectCount(null);
    }

    @Override
    public Integer thisWeekNewMember() throws Exception {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        Date datemonday = DateUtils.getThisWeekMonday(DateUtils.getToday());
        String monday = DateUtils.parseDate2String(datemonday);
        System.out.println(">>>>>>>>>>本周周一日期:" + monday);
        queryWrapper.between("regTime", monday, DateUtils.getToday());
        return memberMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer thisMonthNewMember() throws Exception {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        Date dateoneday = DateUtils.getFirstDay4ThisMonth();
        String oneday = DateUtils.parseDate2String(dateoneday);
        System.out.println(">>>>>>>>>>本月第一天日期:" + oneday);
        queryWrapper.between("regTime", oneday, DateUtils.getToday());
        return memberMapper.selectCount(queryWrapper);
    }

    @Override
    //会员分类数据统计
    //数据格式:ages:["0-18","19-30","30-40","40-50","50-65","65-"]
    //"data":{
    //    "men":[5, 20, 36, 10, 10, 20],
    //    "women":[5, 20, 36, 10, 10, 20],
    //    "total":[5, 20, 36, 10, 10, 20],
    //}
    public Map<String, Object> memberClass() {
        List<Integer> men = new ArrayList<>();
        List<Integer> women = new ArrayList<>();
        List<Integer> total = new ArrayList<>();

        List<Integer[]> ageArr = new ArrayList<>();
        ageArr.add(new Integer[]{0, 18});
        ageArr.add(new Integer[]{19, 30});
        ageArr.add(new Integer[]{31, 40});
        ageArr.add(new Integer[]{41, 50});
        ageArr.add(new Integer[]{51, 64});
        System.out.println(ageArr);

        for(Integer[] age : ageArr){
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            String[] strings = age2Date(age);
            queryWrapper.between("birthday", strings[0], strings[1]);
            //查总数
            Integer countTotal = memberMapper.selectCount(queryWrapper);
            total.add(countTotal);
            //分性别查
            queryWrapper.eq("sex", "男");
            Integer countMen = memberMapper.selectCount(queryWrapper);
            men.add(countMen);
            women.add(countTotal-countMen);
        }
        //查65岁以上
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        String[] strings = age2Date(new Integer[]{65});
        queryWrapper.le("birthday", strings[0]);
        //查总数
        Integer countTotal = memberMapper.selectCount(queryWrapper);
        total.add(countTotal);
        //分性别查
        queryWrapper.eq("sex", "男");
        Integer countMen = memberMapper.selectCount(queryWrapper);
        men.add(countMen);
        women.add(countTotal-countMen);

        Map<String, Object> map = new HashMap<>();
        map.put("men", men);
        map.put("women", women);
        map.put("total", total);

        return map;
    }

    private String[] age2Date(Integer[] ageArr){
        if(ageArr.length > 0){
            String suff = new SimpleDateFormat("-MM-dd").format(new Date());
            String year = new SimpleDateFormat("yyyy").format(new Date());
            int preYear = Integer.valueOf(year) - ageArr[0];
            String leBirthday = preYear + suff;
            //打印生日
            System.out.println("leBirthday" + leBirthday);
            if(ageArr.length > 1){
                int suffYear = Integer.valueOf(year) - ageArr[1];
                String geBirthday = suffYear + suff;
                //打印生日
                System.out.println("geBirthday" + geBirthday);
                return new String[]{geBirthday, leBirthday};
            }
            return new String[]{leBirthday};
        }
        return new String[]{};
    }
}
