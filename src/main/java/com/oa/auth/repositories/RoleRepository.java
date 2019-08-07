package com.oa.auth.repositories;



import com.oa.auth.model.Role;
import com.oa.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 用户信息
 * @author: kk
 * @create: 2018-12-22
 **/
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> , QueryDslPredicateExecutor<Role> {
    Role findRoleByRoleCode(String roleCode);
    Role findRoleByRoleId(Integer roleId);

    @Query("select b from RoleDept a, Role b WHERE a.roleId=b.roleId and a.deptId=:deptid")
    List<Role> selectListByDeptId(@Param("deptid") Integer deptid);



}
