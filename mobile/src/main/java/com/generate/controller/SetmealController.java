package com.generate.controller;


import com.generate.pojo.Setmeal;
import com.generate.service.ISetmealService;
import com.generate.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private ISetmealService iSetmealService;

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    @RequestMapping("listPage")
    //套餐分页查询
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return iSetmealService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, null);
        }
    }

    @RequestMapping("getInfo")
    //套餐详情查询
    public Result getInfo(Integer id){
        try {
            Setmeal setmeal = iSetmealService.getInfo(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}

