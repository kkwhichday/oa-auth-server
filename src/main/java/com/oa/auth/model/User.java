package com.oa.auth.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 登录用户信息
 * @author: kk
 * @create: 2018-12-22
 **/
@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;
    private String workCode;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String avatar;
    private Date createTime;
    private Date updateTime;
    private String status;
    private Integer deptId;

}
