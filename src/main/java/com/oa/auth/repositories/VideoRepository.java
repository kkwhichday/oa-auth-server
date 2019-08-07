package com.oa.auth.repositories;


import com.oa.auth.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 系统配置表
 * @author: kk
 * @create: 2018-12-30
 **/
@Transactional
public interface VideoRepository extends JpaRepository<Video, Integer> {


    @Query("select src from Video where status=:status")
    List<String> findSrcByStatus(@Param("status") String status);

    Video findByStatusAndSrc(String status,String src);




}
