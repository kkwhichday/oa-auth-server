package com.oa.auth.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description: 用户角色关系表
 * @author: kk
 * @create: 2018-12-28
 **/
@Data
@Entity
public class RoleUser {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Integer id;

        /**
         * 用户ID
         */
        private Integer userId;
        /**
         * 角色ID
         */
        private Integer roleId;



    }

