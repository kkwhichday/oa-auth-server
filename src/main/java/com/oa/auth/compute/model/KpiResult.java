package com.oa.auth.compute.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: kpi得分
 * @author: kk
 * @create: 2018-12-20
 **/
@Data
@Entity
public class KpiResult {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    String ename;
    BigDecimal workLoad;
    BigDecimal workQuality;
    Integer type;//--1 按季度版本统计  --2 按月统计 --3 按年统计
    String ordinalNo;//序数,按月就是月份，按年份就是年份,季度版本就是年份-几季度
    String deployVersion;//发布版本
    Date createTime;

//    @ManyToOne
//    @JoinColumn(name = "eId", insertable = false, updatable = false)
//    @JsonIgnore
//    private Employee employee;
}
