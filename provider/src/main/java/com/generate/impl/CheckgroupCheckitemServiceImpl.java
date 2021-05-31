package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.CheckgroupCheckitemMapper;
import com.generate.pojo.CheckgroupCheckitem;
import com.generate.service.ICheckgroupCheckitemService;
import com.generate.util.MessageConstant;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 检查组与检查项的中间表 服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class CheckgroupCheckitemServiceImpl extends ServiceImpl<CheckgroupCheckitemMapper, CheckgroupCheckitem> implements ICheckgroupCheckitemService {

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public Result listByCheckgroupId(Integer checkgroupId) {
        QueryWrapper<CheckgroupCheckitem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("checkgroup_id",checkgroupId);
        List<CheckgroupCheckitem> selectList = checkgroupCheckitemMapper.selectList(queryWrapper);
        //处理数据
        List<Integer> checkitemIds = new ArrayList<>();
        for(CheckgroupCheckitem checkgroupCheckitem : selectList){
            checkitemIds.add(checkgroupCheckitem.getCheckitemId());
        }
        return new Result(true, MessageConstant.QUERY_CHECKGROUPCHECKITEM_SUCCESS,checkitemIds);
    }
}
