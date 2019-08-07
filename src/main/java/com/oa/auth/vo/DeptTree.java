package com.oa.auth.vo;

import com.oa.auth.model.Dept;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kk
 * @date 2018/1/20
 * 机构树
 */
@Data
public class DeptTree extends TreeNode {
    private String name;

}
