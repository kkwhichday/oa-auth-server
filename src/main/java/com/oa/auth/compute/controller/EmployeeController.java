package com.oa.auth.compute.controller;



import com.oa.auth.compute.handler.EmployeeExcelHandler;
import com.oa.auth.compute.handler.ExcelHandler;
import com.oa.auth.compute.service.ComputeKpiSerivice;
import com.oa.auth.compute.service.remote.TopBreaker;
import com.oa.auth.compute.service.remote.TopLeaver;
import com.oa.auth.compute.util.DataUtil;
import com.oa.auth.compute.vo.Employee;


import com.oa.auth.compute.util.ExcelUtil;
import com.oa.auth.vo.R;
import ins.platform.attendance.vo.ExpUserDataVo;
import ins.platform.leave.vo.LeaveMonthMVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 雇员信息处理
 * @author: kk
 * @create: 2018-12-21
 **/
@RestController
@RequestMapping("/admin/top")
public class EmployeeController {
    @Resource
    ExcelHandler<Employee> employeeExcelHandler;
    @Resource
    TopBreaker topBreaker;
    @Resource
    TopLeaver topLeaver;

/*    @RequestMapping(value="/attendance/getEmpLateTop10", method=RequestMethod.GET)
    public List<ExpUserDataVo> getEmpLateTop10(){
        String lastMonth = DataUtil.getLastMonth();
        return topBreaker.getEmpLateTop10(lastMonth);
    }
    @RequestMapping(value="/attendance/getEmpEarlyTop10", method=RequestMethod.GET)
    public List<ExpUserDataVo> getEmpEarlyTop10(){
        String lastMonth = DataUtil.getLastMonth();
        return topBreaker.getEmpEarlyTop10(lastMonth);
    }*/

    @RequestMapping(value="/attendance/getEmpLateAndEarlyTop10", method=RequestMethod.GET)
    public List<ExpUserDataVo> getEmpLateAndEarlyTop10(){
       String lastMonth = DataUtil.getLastMonth();
/*         ExpUserDataVo vo = new ExpUserDataVo();
        vo.setUsername("kk");
        vo.setCount(6);
        List<ExpUserDataVo> list = new ArrayList<>();
        list.add(vo);
        vo = new ExpUserDataVo();
        vo.setUsername("pp");
        vo.setCount(5);
        list.add(vo);
        return list;*/
        return topBreaker.getEmpLateAndEarlyTop10(lastMonth);
    }

    @RequestMapping(value="/attendance/getLeaveTop10/{type}", method=RequestMethod.GET)
    public List<LeaveMonthMVo> getLeaveTop10(@PathVariable(value="type") String type){
        if("year".equals(type)){
            String year = DataUtil.getCurrentYear();
    /*        LeaveMonthMVo vo = new LeaveMonthMVo();
        vo.setUsername("kk");
        vo.setCount(new Float(40));
        List<LeaveMonthMVo> list = new ArrayList<>();
        list.add(vo);
        vo = new LeaveMonthMVo();
        vo.setUsername("pp");
        vo.setCount(new Float(21));
        list.add(vo);
        return list;*/
            return topLeaver.getLeaveTop10(year);
        }else if("month".equals(type)){
            String lastMonth = DataUtil.getLastMonth();
//            LeaveMonthMVo vo = new LeaveMonthMVo();
//            vo.setUsername("kk");
//            vo.setCount(new Float(5));
//            List<LeaveMonthMVo> list = new ArrayList<>();
//            list.add(vo);
//            vo = new LeaveMonthMVo();
//            vo.setUsername("ppx");
//            vo.setCount(new Float(2));
//            list.add(vo);
//            return list;
            return topLeaver.getLeaveTop10(lastMonth);
        }else{
            return null;
        }

    }



    @PostMapping("/import")
    public R<Boolean> importEmployees(@RequestParam("file") MultipartFile file){

        String regex =".*([一二三四]季度).*";
        if(!file.getName().matches(regex)){
            return new R<>(false,"文件名必须指定是哪个季度版本");
        }
        String filePath = "D:\\dev\\kpi\\FI-PICC3G-TEST-测试数据采集报告-18二季度.xls";
        List<Employee> list = (List<Employee>) ExcelUtil.getDataFromExcel(file,employeeExcelHandler);
//        employeeRepository.save(list);

        list.get(0).setId(new Long(1));
        list.get(0).setName("张鹏飞啊啊");
//        employeeRepository.save(list.get(0));
        return new R<>(true);
    }
//    @GetMapping("/import2")
//    public Object test(){
////        List<Employee> list= employeeRepository.findAll();
////        return list;
//    }
    @GetMapping("/import3")
    public void test2(){
        String filePath = "D:\\dev\\kpi\\FI-PICC3G-TEST-测试数据采集报告-18二季度.xls";
        List<Employee> list = (List<Employee>) ExcelUtil.getDataFromExcel(filePath,new EmployeeExcelHandler());
//        employeeRepository.save(list);


    }

    public static void main (String [] args){
//        String filePath = "D:\\dev\\kpi\\FI-PICC3G-TEST-测试数据采集报告-18二季度.xls";
        String filePath = "D:\\dev\\kpi\\2018年非车四季度数据采集报告.xls";
        List<Employee> list = (List<Employee>) ExcelUtil.getDataFromExcel(filePath,new EmployeeExcelHandler());
        new ComputeKpiSerivice().compute(list);
    }
}
