package com.oa.auth.vo;

import com.oa.auth.model.Menu;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kk
 * @date 2017年11月9日23:33:27
 */
@Data
public class MenuTree extends TreeNode implements Serializable {
//    private String icon;
    private String name;
    private String url;
    private boolean spread = false;
    private String path;
    private String component;
    private String authority;
    private String redirect;
    private String code;
    private String type;
    private String label;
    private Integer sort;
    private MenuMeta meta = new MenuMeta();
    public MenuTree() {
    }

   /* public MenuTree(int id, String name, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.label = name;
    }

    public MenuTree(int id, String name, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
        this.label = name;
    }*/

    public MenuTree(Menu menu) {
        this.id = menu.getMenuId();
        this.parentId = menu.getParentId();
//        this.icon = menuVo.getIcon();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.path = menu.getPath();
        this.component = menu.getComponent();
        this.type = menu.getType();
        this.label = menu.getName();
        this.sort = menu.getSort();
        meta.setIcon(menu.getIcon());
        meta.setId(menu.getMenuId());
        meta.setTitle(menu.getName());
    }
}
