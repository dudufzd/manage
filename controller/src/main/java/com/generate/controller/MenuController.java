package com.generate.controller;


import com.generate.pojo.Checkitem;
import com.generate.pojo.Menu;
import com.generate.service.ICheckitemService;
import com.generate.service.IMemberService;
import com.generate.service.IMenuService;
import com.generate.util.MessageConstant;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private IMenuService iMenuService;

    @RequestMapping("getById")
    //单个菜单查询
    public Result getById(Integer id){
        try {
            Menu menu = iMenuService.getById(id);
            if(menu == null){
                return new Result(false, "查询菜单失败");
            }
            return new Result(true, "查询菜单成功",menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询菜单失败");
        }
    }

    @RequestMapping("listAll")
    //所有菜单查询
    public Result listAll(){
        try {
            List<Menu> list = iMenuService.list();
            return new Result(true, "查询菜单成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询菜单失败");
        }
    }
}

