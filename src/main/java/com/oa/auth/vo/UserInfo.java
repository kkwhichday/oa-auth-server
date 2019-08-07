package com.oa.auth.vo;


import com.oa.auth.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 组装用户及权限信息
 * @author: kk
 * @create: 2018-12-23
 **/
@Data
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    private User user;
    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;
}