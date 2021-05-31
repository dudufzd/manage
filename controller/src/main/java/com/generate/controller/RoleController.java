package com.generate.controller;


import com.generate.pojo.Checkitem;
import com.generate.pojo.Role;
import com.generate.service.IRoleService;
import com.generate.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private IRoleService iRoleService;

    @RequestMapping("listPage")
    //分页查询
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        System.out.println("分页查询");
        try {
            System.out.println("查询成功");
            return iRoleService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @RequestMapping("remove")
    //角色删除
    public Result remove(Integer id){
        try {
            boolean remove = iRoleService.removeById(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    @RequestMapping("getById")
    //单个角色查询
    public Result getById(Integer id){
        try {
            Role role = iRoleService.getById(id);
            if(role == null){
                return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
            }
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @RequestMapping("update")
    //更新角色
    public Result update(@RequestBody Role role){
        try {
            boolean update = iRoleService.updateById(role);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    @RequestMapping("save")
    //角色添加
    public Result save(@RequestBody Role role){
        try {
            boolean save = iRoleService.save(role);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ROLE_FAIL);
        }
    }
}

