
package com.oa.auth.vo;


import com.oa.auth.model.Role;
import lombok.Data;

/**
 * @author kk
 *
 * @date 2018/1/20
 * 角色Vo
 */
@Data
public class RoleVO extends Role {
    
    /**
     * 角色部门Id
     */
    private Integer roleDeptId;

    /**
     * 部门名称
     */
    private String deptName;
}
