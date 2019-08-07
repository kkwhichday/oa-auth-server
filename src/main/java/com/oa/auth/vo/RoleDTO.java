package com.oa.auth.vo;

import com.oa.auth.model.Role;
import lombok.Data;

/**
 * @description: 封装角色连表查询结果
 * @author: kk
 * @create: 2018-12-30
 **/
@Data
public class RoleDTO {
    Role role;
    /**
     * 角色部门Id
     */
    private Integer roleDeptId;
}
