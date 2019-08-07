package com.oa.auth.vo;

import com.oa.auth.model.Role;
import com.oa.auth.model.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author kk
 * @date 2018/10/29
 */
@Data
public class UserVO extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色列表
     */
    private List<Role> roleList;
}
