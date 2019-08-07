package com.oa.auth.compute.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @description: 开发需求信息
 * @author: kk
 * @create: 2018-12-20
 **/
@Data
public class Requirement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    String rNo;
    Long eId;
    Integer rLevel;
    Integer rCount;
    String deployVersion;//发布版本
    @ManyToOne
    @JoinColumn(name = "eId", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;
}
