package com.oa.auth.model;

/**
 * @description: 角色表
 * @author: kk
 * @create: 2018-12-22
 **/


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author kk
 * @since 2017-10-29
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer roleId;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;
    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;


    Role(Role role){

    }
    @Override
    public String toString() {
        return "Role{" +
                ", roleId=" + roleId +
                ", roleName=" + roleName +
                ", roleCode=" + roleCode +
                ", roleDesc=" + roleDesc +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
