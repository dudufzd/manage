package com.generate.controller;


import com.generate.pojo.Checkgroup;
import com.generate.service.ICheckgroupService;
import com.generate.util.MessageConstant;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import com.generate.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

import javax.annotation.Resource;

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

    @Resource
    private ICheckgroupService iCheckgroupService;

    @RequestMapping("save")
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
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = iCheckgroupService.listPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0l,null);
        }
    }
}

