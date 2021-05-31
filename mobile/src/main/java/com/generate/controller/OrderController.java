package com.generate.controller;


import com.generate.service.IOrderService;
import com.generate.util.MessageConstant;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/order")
public class OrderController {
    @Reference
    private IOrderService iOrderService;

    @RequestMapping("submit")
    public Result order(@RequestBody Map<String , String> map){
        try {
            System.out.println("请求体map为:" + map);
            Result result = iOrderService.submit(map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }
}

