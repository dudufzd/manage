package com.generate.controller;


import com.generate.pojo.Checkgroup;
import com.generate.service.ICheckgroupService;
import com.generate.util.MessageConstant;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 检查组 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private ICheckgroupService iCheckgroupService;

    @RequestMapping("save")
    //新增检查组信息
    public Result save(@RequestBody Checkgroup checkgroup, Integer[] checkitemids){
        try {
            iCheckgroupService.save(checkgroup, checkitemids);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("listPage")
    //检查组信息分页显示
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = iCheckgroupService.listPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0l,null);
        }
    }

    @RequestMapping("getById")
    //单个检查组信息，根据id查询
    public Result getById(Integer id){
        try {
            Checkgroup checkgroup = iCheckgroupService.getById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkgroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("update")
    //新增检查组信息
    public Result update(@RequestBody Checkgroup checkgroup, Integer[] checkitemids){
        try {
            iCheckgroupService.update(checkgroup, checkitemids);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("delete")
    public Result remove(Integer id){
        try {
            iCheckgroupService.removeById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("listAll")
    public Result listAll(){
        try {
            List<Checkgroup> list = iCheckgroupService.list();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
