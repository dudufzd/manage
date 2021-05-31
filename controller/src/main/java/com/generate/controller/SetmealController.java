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
import java.util.Map;

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

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Reference
    private ISetmealService iSetmealService;

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    //上传图片且返回名字
    @RequestMapping("/upload")
    public String upload(@RequestParam("imgFile") MultipartFile multipartFile){
        File file = MyFileUtils.upload(multipartFile, fileUploadPath);

        //文件上传成功，则将文件名保存到redis中
        if (file != null){
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_IMG_UPLOAD, file.getName());
        }

        return file.getName();
    }

    @RequestMapping("listPage")
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return iSetmealService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, null);
        }
    }

    @RequestMapping("save")
    public Result save(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            iSetmealService.save(setmeal, checkgroupIds);

            //保存数据成功，则将文件名保存到redis中
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_IMG_DB, setmeal.getImg());

            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("getCountSetmeal")
    public Result getCountSetmeal(){
        try {
            Map<String, Object> map = iSetmealService.getCountSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}

