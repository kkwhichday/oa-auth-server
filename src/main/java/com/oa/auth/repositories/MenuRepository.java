package com.oa.auth.repositories;



import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @description: 用户信息
 * @author: kk
 * @create: 2018-12-22
 **/
@Transactional
public interface MenuRepository extends JpaRepository<Menu, Integer> {
//    List<Menu> findMenusByRoleName(String rolesname);
    List<Menu> findMenusByDelFlagAndType(String delFlag, String type);
    Menu findMenuByMenuId(Integer menuId);

    @Query(value="select  distinct a from Menu a, RoleMenu b, Role c WHERE c.roleCode in :roleCode " +
            " and a.menuId=b.menuId and b.roleId=c.roleId"+
            " and a.type='"+ DetailConst.MENU +"'")
    List<Menu> findMenusByRole(@Param("roleCode") String... roleCode);

    @Query(value="select  distinct a.permission from Menu a, RoleMenu b, Role c WHERE c.roleCode in :roleCode " +
            " and a.menuId=b.menuId and b.roleId=c.roleId"+
            " and a.type='"+ DetailConst.BUTTON +"'")
    List<String> findPermissionsByRole(@Param("roleCode") String... roleCode);

}
