package com.generate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generate.mapper.MenuMapper;
import com.generate.pojo.Menu;
import com.generate.service.IMenuService;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
