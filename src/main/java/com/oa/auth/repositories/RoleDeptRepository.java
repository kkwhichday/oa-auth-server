package com.oa.auth.repositories;
import com.oa.auth.model.Role;
import com.oa.auth.model.RoleDept;
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
public interface RoleDeptRepository extends JpaRepository<RoleDept, Integer>{


}
