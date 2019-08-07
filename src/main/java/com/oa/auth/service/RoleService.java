package com.oa.auth.service;

import com.oa.auth.model.Role;
import com.oa.auth.model.RoleDept;
import com.oa.auth.repositories.RoleDeptRepository;
import com.oa.auth.repositories.RoleRepository;
import com.oa.auth.vo.R;
import com.oa.auth.vo.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 角色服务
 * @author: kk
 * @create: 2018-12-30
 **/
@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleDeptRepository roleDeptRepository;


    public R<Boolean> saveRoleAndRoleDept(RoleDTO roleDTO){

//        Boolean exists =roleRepository.exists(QRole.role.roleCode.
//                eq(roleDTO.getRole().getRoleCode()));


        Role role = roleRepository.findRoleByRoleCode(roleDTO.getRole().getRoleCode());
        String msg ="";
        if(role==null){
            role =roleRepository.save(roleDTO.getRole());
        }else{
            //如果rolecode已经存在，就不添加新的角色,只关联机构
            msg.concat("该角色标识已存在，只关联机构.");
        }

        RoleDept roleDept = new RoleDept();
        if(roleDTO.getRoleDeptId()==null){
            //只添加角色，不关联机构
            msg.concat("没有填写机构，只添加角色数据.");
            return new R<Boolean>(true,msg);
        }
        roleDept.setDeptId(roleDTO.getRoleDeptId());
        roleDept.setRoleId(role.getRoleId());
        roleDeptRepository.save(roleDept);
        return new R<Boolean>(true,msg);
    }
}
