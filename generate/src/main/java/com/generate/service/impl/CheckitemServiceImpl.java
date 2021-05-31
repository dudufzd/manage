package com.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.generate.pojo.Checkitem;
import com.generate.mapper.CheckitemMapper;
import com.generate.service.ICheckitemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 检查项 服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class CheckitemServiceImpl extends ServiceImpl<CheckitemMapper, Checkitem> implements ICheckitemService {
    @Resource
    private CheckitemMapper checkitemMapper;

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        //创建分页对象
        Page<Checkitem> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        System.out.println("当前页：" + queryPageBean.getCurrentPage());
        System.out.println("页面大小：" + queryPageBean.getPageSize());
        //设置查询条件
        QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
        //注间这里写表的列名称，不是写实全类的属性名称
        if(queryPageBean.getQueryString()!=null && !queryPageBean.getQueryString().equals("")){//有查条件
            queryWrapper.like("name",queryPageBean.getQueryString())
                    .or().like("code",queryPageBean.getQueryString());
        }
        //没有查询条件不是查全部
        Page<Checkitem> checkitemPage = checkitemMapper.selectPage(page, queryWrapper);
        System.out.println("数据总条数" + checkitemPage.getTotal());
        return new PageResult(checkitemPage.getTotal(), checkitemPage.getRecords());
    }
}
