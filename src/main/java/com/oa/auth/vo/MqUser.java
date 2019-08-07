package com.oa.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 清分用户数据
 * @author: kk
 * @create: 2019-01-07
 **/
@Data
public class MqUser implements Serializable {
    private String workCode;
    private String userName;
    private String phone;
    private Integer deptId;
    private List<Integer> roleIds;
    private Integer flag; //--0 新增 --1 更新
    private String status;

}
