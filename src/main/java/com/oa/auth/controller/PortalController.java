package com.oa.auth.controller;

import com.oa.auth.model.SystemConfig;
import com.oa.auth.repositories.DeptRepository;
import com.oa.auth.repositories.PortalRepository;
import com.oa.auth.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @description: 门户
 * @author: kk
 * @create: 2018-12-30
 **/
@RestController
@RequestMapping("admin/portal")
public class PortalController {

    @Autowired
    private PortalRepository portalRepository;

    @RequestMapping("/getAll")
    public List<SystemConfig> getAllconfig(){

        return portalRepository.findAll();
    }

    @PutMapping("/putUrl")
    public R<Boolean> putUrl(String code,String url){
        portalRepository.updateUrlBycode(code,url);
        return new R<>(true);
    }



}
