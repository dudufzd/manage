package com.generate.controller;


import com.generate.pojo.Checkitem;
import com.generate.service.ICheckitemService;
import com.generate.util.MessageConstant;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 检查项 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("checkitem")
public class CheckitemController {
    @Reference
    private ICheckitemService iCheckitemService;

    @RequestMapping("save")
//    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    //添加操作
    public Result save(@RequestBody Checkitem checkitem){
        try {
            boolean save = iCheckitemService.save(checkitem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }
    @RequestMapping("listPage")
    //分页查询
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return iCheckitemService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @RequestMapping("remove")
    //删除操作
    public Result remove(Integer id){
        try {
            iCheckitemService.removeById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("getById")
    public Result getById(Integer id){
        try {
            Checkitem checkitem = iCheckitemService.getById(id);
            if(checkitem == null){
                return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
            }
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("update")
    //更新检查项
    public Result update(@RequestBody Checkitem checkitem){
        try {
            boolean update = iCheckitemService.updateById(checkitem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("listAll")
    //加载所有检查项
    public Result listAll(){
        try {
            List<Checkitem> list = iCheckitemService.list();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}

