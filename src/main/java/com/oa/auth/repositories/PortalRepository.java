package com.oa.auth.repositories;


import com.oa.auth.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 系统配置表
 * @author: kk
 * @create: 2018-12-30
 **/
@Transactional
public interface PortalRepository extends JpaRepository<SystemConfig, Integer> {
    SystemConfig findSystemConfigBySysCode(String sysCode);

    @Modifying
    @Query("update SystemConfig set url=:url where sysCode=:code")
    void updateUrlBycode(@Param("code")String code, @Param("url")String url);

}
