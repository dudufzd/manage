package com.generate.controller;


import com.generate.pojo.Ordersetting;
import com.generate.service.IOrdersettingService;
import com.generate.util.MessageConstant;
import com.generate.util.MyCalendar;
import com.generate.util.MyFileUtils;
import com.generate.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {
    @Value("${fileUploadPath}")
    private String fileUploadPath;

    @Reference
    private IOrdersettingService iOrdersettingService;

    @RequestMapping("upload")
    public Result upload(@RequestParam("excelFile")MultipartFile multipartFile){
        //上传文件
        try {
            File excel = MyFileUtils.upload(multipartFile, fileUploadPath);
            if(excel != null){//上传成功后执行
                List<Ordersetting> list = excelToList(excel);
                iOrdersettingService.saveList(list);
                return new Result(true, MessageConstant.UPLOAD_SUCCESS);
            }
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }

    private List<Ordersetting> excelToList(File excel) throws IOException, InvalidFormatException {
        //获取工作表读取对象sheet和工作表最大行数
        XSSFWorkbook workbook = new XSSFWorkbook(excel);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();

        //将表中数据读取到List集合中
        List<Ordersetting> list = new ArrayList<>();
        for(int i = 1; i <= lastRowNum; i++){
            XSSFRow row = sheet.getRow(i);
            Date date = row.getCell(0).getDateCellValue();
            int number = (int)row.getCell(1).getNumericCellValue();
            Ordersetting ordersetting = new Ordersetting(0, date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), number, 0);
            list.add(ordersetting);
        }
        return list;
    }

    @RequestMapping("listCalendar")
    public Result list(String date){
        try {
            List<MyCalendar> calendars = iOrdersettingService.listOrdersetting(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, calendars);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("update")
    public Result update(String date, Integer number){
        try {
            iOrdersettingService.update(date, number);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}

