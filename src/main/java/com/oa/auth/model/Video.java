package com.oa.auth.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * @description: 教学视频
 * @author: kk
 * @create: 2019-01-29
 **/
@Data
@Entity
public class Video implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer vId;
    private String src;
    private String status;//0--有效 1--无效
}
