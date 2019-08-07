package com.oa.auth.controller;

import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.Dept;
import com.oa.auth.repositories.DeptRepository;
import com.oa.auth.util.CustomBeanUtils;
import com.oa.auth.util.TreeUtil;
import com.oa.auth.vo.DeptTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description: 组织机构
 * @author: kk
 * @create: 2018-12-28
 **/
@RestController
@RequestMapping("admin/dept")
public class DeptController {
    @Autowired
    private DeptRepository deptRepository;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return Dept
     */
    @GetMapping("/{id}")
    public Dept get(@PathVariable Integer id) {
        return deptRepository.findDeptByDeptId(id);
    }


    /**
     * 通过ID查询
     *
     * @param id ID
     * @return Dept
     */
    @GetMapping("/sub/{id}")
    public  List<DeptTree> getSubDepts(@PathVariable Integer id) {

        if(id<0){return null;}
        List<Dept> list = deptRepository.findDeptsByDelFlag(DetailConst.STATUS_NORMAL);
        return TreeUtil.getDeptTree(list, id);

    }
    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public List<DeptTree> getTree() {
        List<Dept> list = deptRepository.findDeptsByDelFlag(DetailConst.STATUS_NORMAL);


        return TreeUtil.getDeptTree(list, 0);
    }

    /**
     * 添加
     *
     * @param dept 实体
     * @return success/false
     */
    @PostMapping
    public Boolean add(@RequestBody Dept dept) {
        dept.setDelFlag(DetailConst.STATUS_NORMAL);
        dept.setCreateTime(new Date());
        return deptRepository.save(dept) != null;
    }

    /**
     * 删除
     *
     * @param id ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        deptRepository.delete(id);
    }

    /**
     * 编辑
     *
     * @param dept 实体
     * @return success/false
     */
    @PutMapping
    public Boolean edit(@RequestBody Dept dept) {

        dept.setUpdateTime(new Date());
        Dept olddept = deptRepository.findOne(dept.getDeptId());
        try {
            CustomBeanUtils.MergeBean(dept, olddept, Arrays.asList("deptId"),false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return deptRepository.save(olddept) != null;
    }
}

