package com.oa.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author kk
 * @since 2017-10-29
 */
@Data
@Entity
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 菜单ID
	 */

	private Integer menuId;



}
