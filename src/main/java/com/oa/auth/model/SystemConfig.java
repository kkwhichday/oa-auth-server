package com.oa.auth.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description: 其他系统地址配置
 * @author: kk
 * @create: 2018-12-30
 **/
@Entity
@Data
public class SystemConfig {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer sysId;
    private String sysCode  ;
    private String sysName;
    private String url;
    private String status;


}
