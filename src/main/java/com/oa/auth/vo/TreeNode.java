package com.oa.auth.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kk
 * @date 2017年11月9日23:33:45
 */
@Data
public class TreeNode {
    protected Number id;
    protected Number parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
