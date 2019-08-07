package com.oa.auth.compute.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @description: 开发bug信息
 * @author: kk
 * @create: 2018-12-20
 **/
@Data
public class Bug {

    private Long id;
    String bugNo;

    Integer bugLevel;
    Integer bugCount;
    Integer calcType; //--0修改的bug  --1造的bug
    String deployVersion;//发布版本




}
