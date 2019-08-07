
package com.oa.auth.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/** 
  * @Description:    归属组织
  * @Author:         kk
  * @CreateDate:     2018/12/28
 */
@Data
@Entity
public class Dept{

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer deptId;
    /**
     * 组织名称
     */
    private String name;

    private String code;
    /**
     * 排序
     */

    private Integer orderNum;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 修改时间
     */

    private Date updateTime;
    /**
     * 是否删除  -1：已删除  0：正常
     */

    private String delFlag;


    private Integer parentId;




    @Override
    public String toString() {
        return "SysDept{" +
                ", deptId=" + deptId +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
