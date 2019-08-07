package com.oa.auth.compute.handler;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @description: 处理excel数据
 * @author: kk
 * @create: 2018-12-21
 **/
public interface ExcelHandler<T> {
    T  handle(Workbook workbook);
}
