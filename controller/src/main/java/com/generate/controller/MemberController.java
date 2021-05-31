package com.generate.controller;


import com.generate.pojo.Member;
import com.generate.service.IMemberService;
import com.generate.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
@RequestMapping("/member")
public class MemberController {

    @Reference
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
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean, SeData seData){
        System.out.println("分页查询");
        try {
            System.out.println("查询成功");
            return iMemberService.listPage(queryPageBean,seData);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @RequestMapping("memberEcharts")
    //图表数据查询
    public Result memberEcharts(){
        try {
            Map<String, Object> map = iMemberService.memberEcharts();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("memberClass")
    //分类图表数据查询
    public Result memberClass(){
        try {
            Map<String, Object> map = iMemberService.memberClass();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("download")
    //下载页面数据
    public ResponseEntity<byte[]> download(QueryPageBean queryPageBean, SeData seData){
        //打印seData数据
        System.out.println(seData);

        if(queryPageBean.getQueryString().equals("null")){
          queryPageBean.setQueryString("");
        }

        //查询当前页数据
        PageResult pageResult = iMemberService.listPage(queryPageBean, seData);

        //创建表格文件并写入数据
        File file = listToExcel(pageResult.getRows(), queryPageBean.getCurrentPage());
        System.out.println(pageResult.getRows());

        //响应到客户端
        return MyFileUtils.download(file.getName(), "D:/upload/");
    }

    /**
     * 创建excel文件，并将当前页会员数据写入文件
     * @param list 当前页会员数据
     * @param pageNum 当前页的页码
     * @return 文件对象
     */
    private File listToExcel(List< Member > list, int pageNum){
        //1.创建文件基本信息
        //创建文件对象
        File file = new File("D:/upload/第" + pageNum + "页会员数据.xlsx");

        //创建工作簿和工作表
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("第" + pageNum + "页会员数据");

        //创建表头
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("身份证号码");
        row.createCell(4).setCellValue("电话号码");
        row.createCell(5).setCellValue("注册时间");
        row.createCell(6).setCellValue("邮箱");
        row.createCell(7).setCellValue("生日");

        //2.填入数据
        for (int i = 1; i <= list.size(); i++){
            Member member = list.get(i - 1);
            XSSFRow row1 = sheet.createRow(i);
            row1.createCell(0).setCellValue(member.getId());
            row1.createCell(1).setCellValue(member.getName());
            row1.createCell(2).setCellValue(member.getSex());
            row1.createCell(3).setCellValue(member.getIdcard());
            row1.createCell(4).setCellValue(member.getPhonenumber());
            row1.createCell(5).setCellValue(member.getRegtime().toString());
            row1.createCell(6).setCellValue(member.getEmail());
            row1.createCell(7).setCellValue(member.getBirthday().toString());
        }

        try {
            workbook.write(new FileOutputStream(file));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}

