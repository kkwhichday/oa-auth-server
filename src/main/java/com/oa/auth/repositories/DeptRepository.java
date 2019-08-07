package com.oa.auth.repositories;

import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.Dept;
import com.oa.auth.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 组织机构
 * @author: kk
 * @create: 2018-12-28
 **/
@Transactional
public interface DeptRepository extends JpaRepository<Dept, Integer> {
    Dept findDeptByDeptId(Integer deptId);
    List<Dept> findDeptsByDelFlag(String delFlag);

    @Query(value="select  distinct a from Dept a, RoleDept b, Role c WHERE c.roleId = :roleId " +
            " and a.deptId=b.deptId and b.roleId=c.roleId")
    Dept findDeptByRole(@Param("roleId") Integer roleId);

    @Query("select a from Dept a where a.parentId=:deptId or a.deptId=:deptId")
    List<Dept> findDeptByParentDeptId(@Param("deptId") Integer deptId);


}
