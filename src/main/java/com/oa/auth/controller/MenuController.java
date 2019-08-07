package com.oa.auth.controller;

import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.Menu;
import com.oa.auth.repositories.MenuRepository;
import com.oa.auth.service.UserService;
import com.oa.auth.util.TreeUtil;
import com.oa.auth.vo.MenuTree;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @description: 用户
 * @author: kk
 * @create: 2018-12-22
 **/
@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserService userService;



    @GetMapping("/userMenu")
    public Object menu(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = CollectionUtil.join(authentication.getAuthorities(),",");

        return userService.produceMenuTree(auth.split(","));

    }

    @GetMapping("/allTree")
    public Object allMenu(){

        List<Menu> list = menuRepository.findMenusByDelFlagAndType("0", DetailConst.MENU);
        List<MenuTree> menuTreeList = new ArrayList<>();
        list.forEach(menu -> {
            if (DetailConst.MENU.equals(menu.getType())) {
                menuTreeList.add(new MenuTree(menu));
            }
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, -1);

    }
    @GetMapping("/{menuid}")
    public Menu menubyid(@PathVariable Integer menuid){

        Menu menu = menuRepository.findMenuByMenuId(menuid);

        return menu;

    }
}
