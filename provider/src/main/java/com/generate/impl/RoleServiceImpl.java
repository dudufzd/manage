package com.generate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.RoleMapper;
import com.generate.pojo.Role;
import com.generate.pojo.Setmeal;
import com.generate.service.IRoleService;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        Page<Role> page = new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(queryString != null&&!queryString.equals("")){
            queryWrapper.like("name",queryString);
        }
        Page<Role> rolePage = roleMapper.selectPage(page, queryWrapper);
        return new PageResult(rolePage.getTotal(),rolePage.getRecords());
    }
}
