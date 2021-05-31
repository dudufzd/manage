package com.generate.controller;


import com.generate.service.ICheckgroupCheckitemService;
import com.generate.util.MessageConstant;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 检查组与检查项的中间表 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/checkgroup-checkitem")
public class CheckgroupCheckitemController {

    @Reference
    private ICheckgroupCheckitemService iCheckgroupCheckitemService;

    @RequestMapping("/checkgroupId")
    //查询检查组包含的检查项，返回的Integer类型数组
    public Result getListByCheckgroupId(Integer checkgroupId){
        try {
            Result result = iCheckgroupCheckitemService.listByCheckgroupId(checkgroupId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUPCHECKITEM_FAIL);
        }
    }
}

