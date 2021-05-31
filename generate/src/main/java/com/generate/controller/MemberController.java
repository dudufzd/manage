package com.generate.controller;


import com.generate.service.IMemberService;
import com.generate.util.MessageConstant;
import com.generate.util.PageResult;
import com.generate.util.QueryPageBean;
import com.generate.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private IMemberService iMemberService;

    @RequestMapping("remove")
    //会员删除
    public Result remove(Integer id){
        try {
            boolean remove = iMemberService.removeById(id);
            return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
    }

    @RequestMapping("listPage")
    //分页查询
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return iMemberService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }
}

