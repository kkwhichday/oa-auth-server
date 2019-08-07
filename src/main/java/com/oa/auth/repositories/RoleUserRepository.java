package com.oa.auth.repositories;
import com.oa.auth.model.Role;
import com.oa.auth.model.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @description: 用户信息
 * @author: kk
 * @create: 2018-12-22
 **/
@Transactional
public interface RoleUserRepository extends JpaRepository<RoleUser, Integer>{

    @Query("select c.roleCode from  RoleUser a, User b, Role c WHERE a.userId=b.userId " +
            "and b.userId=:userId and c.roleId=a.roleId")
    List<String> findRoleByUserId(@Param("userId") Integer userId);

    @Query("select c from  RoleUser a, User b, Role c WHERE a.userId=b.userId " +
            "and b.userId=:userId and c.roleId=a.roleId")
    List<Role> findRoleListByUserId(@Param("userId") Integer userId);

    @Query("select a from RoleUser a where a.roleId in :roleIds and a.userId=:userId")
    List<RoleUser> findRoleUserByRolesAndUserId(@Param("roleIds")Integer[] roleIds,@Param("userId")Integer userId);

    List<RoleUser> findRoleUserByUserId(Integer userId);
}
