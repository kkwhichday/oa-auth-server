package com.oa.auth.controller;

import com.oa.auth.model.*;
import com.oa.auth.repositories.DeptRepository;
import com.oa.auth.repositories.RoleRepository;
import com.oa.auth.service.RoleService;
import com.oa.auth.service.UserService;
import com.oa.auth.util.CustomBeanUtils;
import com.oa.auth.util.Query;
import com.oa.auth.vo.R;
import com.oa.auth.vo.RoleDTO;
import com.oa.auth.vo.RoleVO;
import com.oa.auth.vo.UserVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * @description: 角色管理
 * @author: kk
 * @create: 2018-12-23
 **/
@RestController
@RequestMapping("admin/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    //实体管理对象
    @Autowired
    private EntityManager entityManager;
    //queryDSL,JPA查询工厂
    private JPAQueryFactory queryFactory;

    //实例化查询工厂
    @PostConstruct
    public void init()
    {
        queryFactory = new JPAQueryFactory(entityManager);
    }


    /**
     * 获取角色列表
     *
     * @param deptId  部门ID
     * @return 角色列表
     */
    @GetMapping("/roleList/{deptId}")
    public List<Role> roleList(@PathVariable Integer deptId) {
        return roleRepository.selectListByDeptId(deptId);

    }


    @RequestMapping("/rolePage")
    public Page<RoleVO> userPage(@RequestParam Map<String, Object> params) {
        Pageable pageable = new Query(params).getPageable();
        JPAQuery<Role> jpaQuery = queryFactory.select(QRole.role)
                .from(QRole.role)
                .orderBy(QRole.role.roleId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        QueryResults<Role> rs = jpaQuery.fetchResults();
        return new PageImpl(rs.getResults(),pageable,rs.getTotal());
    }


    /**
     * 分页查询角色
     *
     * @param params 参数集
     * @return 角色集合
     */
    /*@RequestMapping("/rolePage")
    public Page<RoleVO> userPage(@RequestParam Map<String, Object> params) {
    //部门角色，适用于各部门都有自己的角色岗位，如果部门岗位都相同，则不需要部门角色表

        Pageable pageable = new Query(params).getPageable();
        JPAQuery<Tuple> jpaQuery = queryFactory.select(QRole.role,QDept.dept)
                .from(QRole.role,QRoleDept.roleDept,QDept.dept)
                .where(QRole.role.roleId.eq(QRoleDept.roleDept.roleId),
                        QDept.dept.deptId.eq(QRoleDept.roleDept.deptId))
                .orderBy(QRole.role.roleId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        //添加查询条件
//        jpaQuery.where(predicate);
        //拿到结果

        QueryResults<Tuple> results = jpaQuery.fetchResults();
        List<RoleVO> list = new ArrayList<RoleVO>((int)results.getTotal());
        for (Tuple row : results.getResults()) {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(row.get(QRole.role),roleVO);
            roleVO.setRoleDeptId(row.get(QDept.dept).getDeptId());
            roleVO.setDeptName(row.get(QDept.dept).getName());
            list.add(roleVO);

        }
        return new PageImpl(list,pageable,results.getTotal());
    }
*/

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public Role role(@PathVariable Integer id) {
        return roleRepository.findOne(id);
    }

    /**
     * 添加角色
     *
     * @param roleVO 角色信息
     * @return success、false
     */
    @PostMapping
    public R<Boolean> role(@RequestBody RoleVO roleVO) {

        RoleDTO roleDTO = new RoleDTO();
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setCreateTime(new Date());
        role.setDelFlag("0");
        roleDTO.setRole(role);
        roleDTO.setRoleDeptId(roleVO.getRoleDeptId());

        return roleService.saveRoleAndRoleDept(roleDTO);
    }

    /**
     * 修改角色
     *
     * @param roleVO 角色信息
     * @return success/false
     */
    @PutMapping
    public R<Boolean> roleUpdate(@RequestBody RoleVO roleVO) {
        Role oldRole = roleRepository.findOne(roleVO.getRoleId());

        try {
            CustomBeanUtils.MergeBean(roleVO, oldRole, Arrays.asList("roleId"),false);
        } catch (Exception e) {
            e.printStackTrace();
            return new R<>(false,e.getMessage());
        }

        return new R<>(roleRepository.save(oldRole)!=null);
    }

    @DeleteMapping("/{id}")
    public R<Boolean> roleDel(@PathVariable Integer id) {
        try {
            roleRepository.delete(id);
        }catch (Exception e){
            return new R<>(false,e.getMessage());
        }

        return new R<>(true);
    }


}
