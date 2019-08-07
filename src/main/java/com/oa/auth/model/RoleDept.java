package com.oa.auth.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @description: 组织角色关系表
 * @author: kk
 * @create: 2018-12-28
 **/
@Data
@Entity
public class RoleDept {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 部门ID
     */

    private Integer deptId;




    @Override
    public String toString() {
        return "RoleDept{" +
                ", id=" + id +
                ", roleId=" + roleId +
                ", deptId=" + deptId +
                "}";
    }
}
