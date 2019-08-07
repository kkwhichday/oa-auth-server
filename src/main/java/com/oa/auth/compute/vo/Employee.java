package com.oa.auth.compute.vo;


import com.oa.auth.compute.constants.EmpConstants;
import com.oa.auth.compute.model.KpiResult;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 员工表
 * @author: kk
 * @create: 2018-12-20
 **/
@Data
public class Employee {

    private Long id;
    String name;
    Integer eLevel= EmpConstants.Employee;
    Integer groupNo;
    Integer status=0;
    Integer reqCount=0;
    Integer bugCount=0;
    Integer codeCount=0;
    BigDecimal workLoad;
    BigDecimal workQuality;

    private List<Employee> children = new ArrayList<Employee>();
    //SET FOREIGN_KEY_CHECKS = 0 drop自关联表需要先关闭外键检查

    //一对多，一个员工对应多个需求，关联的字段是订单里的eId字段

    private List<Requirement> requirements;


    private List<Bug> bugs;



}
