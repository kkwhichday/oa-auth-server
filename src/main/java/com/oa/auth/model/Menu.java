package com.oa.auth.model;

/**
 * @description: 菜单
 * @author: kk
 * @create: 2018-12-22
 **/

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author kk
 * @since 2018-11-08
 */
@Data
@Entity
public class Menu  {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 请求链接
     */
    private String url;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 父菜单ID
     */

    private Integer parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * VUE页面
     */
    private String component;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    private String type;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 更新时间
     */

    private Date updateTime;
    /**
     * 0--正常 1--删除
     */

    private String delFlag;
    /**
     * 前端URL
     */
    private String path;





    @Override
    public String toString() {
        return "Menu{" +
                ", menuId=" + menuId +
                ", name=" + name +
                ", permission=" + permission +
                ", url=" + url +
                ", method=" + method +
                ", icon=" + icon +
                ", component=" + component +
                ", sort=" + sort +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
