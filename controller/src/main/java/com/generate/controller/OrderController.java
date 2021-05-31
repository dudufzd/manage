package com.generate.controller;


import com.generate.service.IMemberService;
import com.generate.service.IOrderService;
import com.generate.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.List;
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
    @Reference
    private IMemberService iMemberService;

    @RequestMapping("getBusiness")
    //数据统计
    //数据结构：
    //reportData:{
    //    reportDate:null,
    //    todayNewMember :0,//本日新增会员数
    //    totalMember :0,//总会员数
    //    thisWeekNewMember :0,//本周新增会员数
    //    thisMonthNewMember :0,//本月新增会员数
    //    todayOrderNumber :0,//今日预约数
    //    todayVisitsNumber :0,//今日到诊数
    //    thisWeekOrderNumber :0,//本周预约数
    //    thisWeekVisitsNumber :0,//本周到诊数
    //    thisMonthOrderNumber :0,//本月预约数
    //    thisMonthVisitsNumber :0,//本月到诊数
    //    hotSetmeal :[
    //        {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
    //        {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
    //    ]
    //}
    public Result getBusiness(){
        try {
            Map<String, Object> map = new HashMap<>();
            //    todayNewMember :0,//本日新增会员数
            //    totalMember :0,//总会员数
            //    thisWeekNewMember :0,//本周新增会员数
            //    thisMonthNewMember :0,//本月新增会员数
            map.put("todayNewMember", iMemberService.todayNewMember());
            map.put("totalMember", iMemberService.totalMember());
            map.put("thisWeekNewMember", iMemberService.thisWeekNewMember());
            map.put("thisMonthNewMember", iMemberService.thisMonthNewMember());

            //    todayOrderNumber :0,//今日预约数
            //    todayVisitsNumber :0,//今日到诊数
            //    thisWeekOrderNumber :0,//本周预约数
            //    thisWeekVisitsNumber :0,//本周到诊数
            //    thisMonthOrderNumber :0,//本月预约数
            //    thisMonthVisitsNumber :0,//本月到诊数
            map.put("todayOrderNumber", iOrderService.todayOrderNumber());
            map.put("todayVisitsNumber", iOrderService.todayVisitsNumber());
            map.put("thisWeekOrderNumber", iOrderService.thisWeekOrderNumber());
            map.put("thisWeekVisitsNumber", iOrderService.thisWeekVisitsNumber());
            map.put("thisMonthOrderNumber", iOrderService.thisMonthOrderNumber());
            map.put("thisMonthVisitsNumber", iOrderService.thisMonthVisitsNumber());

            //hotSetmeal :[],//本月热门套餐
            map.put("hotSetmeal", iOrderService.hotSetmeal());
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("download")
    //下载页面统计数据
    public ResponseEntity<byte[]> download(){
        //查询当前页数据
        try {
            Result result = getBusiness();
            Map<String, Object> map = (Map)result.getData();
            System.out.println(map);

            //创建表格文件并写入数据
            File file = new File("D:\\upload\\ordertemplate.xlsx");
            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            //获取工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            //修改数据
            sheet.getRow(1).getCell(1).setCellValue(Double.valueOf(iMemberService.todayNewMember().toString()));
            sheet.getRow(1).getCell(3).setCellValue(Double.valueOf(iMemberService.totalMember().toString()));
            sheet.getRow(2).getCell(1).setCellValue(Double.valueOf(iMemberService.thisWeekNewMember().toString()));
            sheet.getRow(2).getCell(1).setCellValue(Double.valueOf(iMemberService.thisMonthNewMember().toString()));

            sheet.getRow(4).getCell(1).setCellValue(Double.valueOf(iOrderService.todayOrderNumber().toString()));
            sheet.getRow(4).getCell(3).setCellValue(Double.valueOf(iOrderService.todayVisitsNumber().toString()));
            sheet.getRow(5).getCell(1).setCellValue(Double.valueOf(iOrderService.thisWeekOrderNumber().toString()));
            sheet.getRow(5).getCell(3).setCellValue(Double.valueOf(iOrderService.thisWeekVisitsNumber().toString()));
            sheet.getRow(6).getCell(1).setCellValue(Double.valueOf(iOrderService.thisMonthOrderNumber().toString()));
            sheet.getRow(6).getCell(3).setCellValue(Double.valueOf(iOrderService.thisMonthVisitsNumber().toString()));

            List<Map<String, Object>> list = iOrderService.hotSetmeal();
            for(int i = 9, j = 0; i < list.size() + 9; i++, j++){
                Map<String, Object> objectMap = list.get(j);
                XSSFRow row = sheet.getRow(i);
                row.getCell(0).setCellValue(objectMap.get("name").toString());
                row.getCell(1).setCellValue(objectMap.get("setmeal_count").toString());
                row.getCell(2).setCellValue(objectMap.get("proportion").toString());
                row.getCell(3).setCellValue("");
            }
            inputStream.close();

            //响应到客户端
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return MyFileUtils.download(file.getName(), "D:/upload/");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}







