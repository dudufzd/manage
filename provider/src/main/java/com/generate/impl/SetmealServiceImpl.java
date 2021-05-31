package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.*;
import com.generate.pojo.*;
import com.generate.service.ISetmealService;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {

    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;
    @Resource
    private CheckgroupMapper checkgroupMapper;
    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    @Resource
    private CheckitemMapper checkitemMapper;

    @Override
    //查询套餐详情
    public Setmeal getInfo(Integer id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        //1.查询中间表，套餐包含哪些检查组
        QueryWrapper<SetmealCheckgroup> scWrapper = new QueryWrapper<>();
        scWrapper.eq("setmeal_id", id);
        //中间表信息: setmeal_id  checkgroup_id
        List<SetmealCheckgroup> setmealCheckgroupList = setmealCheckgroupMapper.selectList(scWrapper);
        //判断套餐中是否含有检查组，如果没有直接返回套餐信息
        if(setmealCheckgroupList == null || setmealCheckgroupList.size() == 0){
            return setmeal;
        }

        //2.查询对应的检查组详细信息
        List<Checkgroup> checkgroupList = new ArrayList<>();
        for(SetmealCheckgroup setmealCheckgroup : setmealCheckgroupList){
            Checkgroup checkgroup = checkgroupMapper.getById(setmealCheckgroup.getCheckgroupId());

            //3.查询中间表，检查组包含哪些检查项
            QueryWrapper<CheckgroupCheckitem> ccwrapper = new QueryWrapper<>();
            ccwrapper.eq("checkgroup_id", checkgroup.getId());
            //中间表信息: checkgroup_id  checkitem_id
            List<CheckgroupCheckitem> checkgroupCheckitemList = checkgroupCheckitemMapper.selectList(ccwrapper);
            //判断检查组中是否含有检查项
            if(checkgroupCheckitemList != null && checkgroupCheckitemList.size() > 0){
                List<Checkitem> checkitemList = new ArrayList<>();
                for(CheckgroupCheckitem checkgroupCheckitem : checkgroupCheckitemList){
                    Checkitem checkitem = checkitemMapper.getById(checkgroupCheckitem.getCheckitemId());
                    checkitemList.add(checkitem);
                }
                checkgroup.setCheckitemList(checkitemList);
            }
            checkgroupList.add(checkgroup);
        }
        setmeal.setCheckgroupList(checkgroupList);

        return setmeal;
    }

    @Override
    //套餐预约数据统计
    //数据格式：
    //"data":{
    //    "setmealNames":["套餐1","套餐2","套餐3"],
    //    "setmealCount":[{"name":"套餐1","value":10},
    //    {"name":"套餐2","value":30},
    //    {"name":"套餐3","value":25} ]
    //}
    public Map<String, Object> getCountSetmeal() {
        List<Map<String, Object>> countSetmeal = setmealMapper.getCountSetmeal();
        System.out.println(">>>>>>>>>>>>>>>>" + countSetmeal);

        List<String> names = new ArrayList<>();
        for(Map<String, Object> stringObjectMap : countSetmeal){
            names.add(stringObjectMap.get("name").toString());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("setmealCount", countSetmeal);
        map.put("setmealNames", names);
        return map;
    }

    @Override
    //分页查询
    public PageResult listPage(QueryPageBean queryPageBean) {
        Page<Setmeal> page = new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        if(queryString != null&&!queryString.equals("")){
            queryWrapper.like("code",queryString)
                    .or().like("name",queryString)
                    .or().like("helpCode",queryString);
        }
        Page<Setmeal> setmealPage = setmealMapper.selectPage(page, queryWrapper);
        return new PageResult(setmealPage.getTotal(),setmealPage.getRecords());
    }

    @Override
    //新增套餐
    public void save(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealMapper.insert(setmeal);
        for (Integer checkgroupId : checkgroupIds){
            SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
            setmealCheckgroup.setSetmealId(setmeal.getId());
            setmealCheckgroup.setCheckgroupId(checkgroupId);
            setmealCheckgroupMapper.insert(setmealCheckgroup);
        }
    }
}
