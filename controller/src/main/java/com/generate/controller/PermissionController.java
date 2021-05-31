package com.generate.controller;


import com.generate.pojo.Menu;
import com.generate.pojo.Permission;
import com.generate.service.IMenuService;
import com.generate.service.IPermissionService;
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
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private IPermissionService iPermissionService;

    @RequestMapping("getById")
    //单个权限查询
    public Result getById(Integer id){
        try {
            Permission permission = iPermissionService.getById(id);
            if(permission == null){
                return new Result(false, "查询权限失败");
            }
            return new Result(true, "查询权限成功",permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询权限失败");
        }
    }

    @RequestMapping("listAll")
    //所有权限查询
    public Result listAll(){
        try {
            List<Permission> list = iPermissionService.list();
            return new Result(true, "查询权限成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询权限失败");
        }
    }
}

