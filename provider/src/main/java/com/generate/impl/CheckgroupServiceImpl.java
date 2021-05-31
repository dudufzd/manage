package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.CheckgroupCheckitemMapper;
import com.generate.mapper.CheckgroupMapper;
import com.generate.pojo.Checkgroup;
import com.generate.pojo.CheckgroupCheckitem;
import com.generate.service.ICheckgroupService;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 检查组 服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup> implements ICheckgroupService {

    @Resource
    private CheckgroupMapper checkgroupMapper;
    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public void save(Checkgroup checkgroup, Integer[] checkitemids) {
        //添加检查组
        //1.添加检查组表信息
        int insert = checkgroupMapper.insert(checkgroup);
        Integer checkgroupId = checkgroup.getId();
        //2.添加中间表信息
        for(Integer checkitemid : checkitemids){
            CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
            checkgroupCheckitem.setCheckgroupId(checkgroupId);
            checkgroupCheckitem.setCheckitemId(checkitemid);
            checkgroupCheckitemMapper.insert(checkgroupCheckitem);
        }
    }

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        Page<Checkgroup> page = new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();
        QueryWrapper<Checkgroup> queryWrapper = new QueryWrapper<>();
        if(queryString!=null && !queryString.equals("")){
            queryWrapper.like("code",queryString)
                    .or().like("name",queryString)
                    .or().like("helpCode",queryString);
        }
        Page<Checkgroup> checkgroupPage = checkgroupMapper.selectPage(page,queryWrapper);

        return new PageResult(checkgroupPage.getTotal(),checkgroupPage.getRecords());
    }

    @Override
    public void update(Checkgroup checkgroup, Integer[] checkitemids) {
        //更新检查组信息
        //1.更新检查组表信息
        checkgroupMapper.updateById(checkgroup);
        //2.删除中间表原有的数据
        checkgroupCheckitemMapper.delete(new QueryWrapper<CheckgroupCheckitem>().eq("checkgroup_id",checkgroup.getId()));
        //3.重新添加中间表的数据
        Integer checkgroupId = checkgroup.getId();
        for(Integer checkitemid : checkitemids){
            CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
            checkgroupCheckitem.setCheckgroupId(checkgroupId);
            checkgroupCheckitem.setCheckitemId(checkitemid);
            checkgroupCheckitemMapper.insert(checkgroupCheckitem);
        }
    }
}
