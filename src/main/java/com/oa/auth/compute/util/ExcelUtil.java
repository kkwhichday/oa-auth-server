package com.oa.auth.compute.util;


import com.oa.auth.compute.handler.ExcelHandler;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil<T> {

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }


    /**
     * 读取出filePath中的所有数据信息
     * @param file excel文件的绝对路径
     *
     */

    public static <T> T getDataFromExcel(MultipartFile file, ExcelHandler<T> excelHandler) {

        Workbook workbook = null;

        try {
            //2003版本的excel，用.xls结尾
            workbook = new HSSFWorkbook(file.getInputStream());//得到工作簿
            return excelHandler.handle(workbook);
        } catch (Exception ex) {
            //ex.printStackTrace();
            try {
                //2007版本的excel，用.xlsx结尾

                workbook = new XSSFWorkbook(file.getInputStream());//得到工作簿
                return excelHandler.handle(workbook);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取出filePath中的所有数据信息
     * @param filePath excel文件的绝对路径
     *
     */

    public static <T> T getDataFromExcel(String filePath, ExcelHandler<T> excelHandler) {


        //判断是否为excel类型文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
        }
        T t = null;
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //2003版本的excel，用.xls结尾
            workbook = new HSSFWorkbook(fis);//得到工作簿
            t = excelHandler.handle(workbook);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                //2007版本的excel，用.xlsx结尾

                workbook = new XSSFWorkbook(fis);//得到工作簿
                t = excelHandler.handle(workbook);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
    public static void main(String[] args) {
//        getDataFromExcel("",);
String regex =".*([一二三四]季度).*";
        System.out.println("kkk季度234".matches(regex));
        System.out.println("ppp一季度sss".matches(regex));
        System.out.println("ppp一三季度sss".matches(regex));
    }
}