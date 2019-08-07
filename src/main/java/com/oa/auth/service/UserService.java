package com.oa.auth.service;

import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.*;
import com.oa.auth.repositories.MenuRepository;
import com.oa.auth.repositories.RoleRepository;
import com.oa.auth.repositories.RoleUserRepository;
import com.oa.auth.repositories.UserRepository;
import com.oa.auth.util.CustomBeanUtils;
import com.oa.auth.util.TreeUtil;
import com.oa.auth.vo.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xiaoleilu.hutool.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * @description: 登录用户查询
 * @author: kk
 * @create: 2018-12-22
 **/
@Service
@Transactional
@Slf4j
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(entityManager);
    }


    //根据角色生成菜单数
//    @Cacheable(value = "MenuTree_", key = "caches[0].name+methodName+#rolecodes")
    public List<MenuTree> produceMenuTree(String ...rolecodes){

/*
        QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
        List<Menu> list =
                queryFactory.select(qRoleMenu.menu).from(qRoleMenu).innerJoin(qRoleMenu.menu).innerJoin(qRoleMenu.role)
                        .where(qRoleMenu.menu.type.eq(DetailConst.MENU)).distinct().fetch();
*/
        QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
        List<Menu> list =queryFactory.select(QMenu.menu).from(qRoleMenu,QRole.role,QMenu.menu)
                .where(qRoleMenu.menuId.eq(QMenu.menu.menuId).and(qRoleMenu.roleId.eq(QRole.role.roleId))
                        .and(QMenu.menu.type.eq(DetailConst.MENU))
                .and(QRole.role.roleCode.in(rolecodes))).distinct()
                .fetch();
//        List<Menu> list =menuRepository.findMenusByRole(rolecodes);
        List<MenuTree> menuTreeList = new ArrayList<>();
        list.forEach(menu -> {
            if (DetailConst.MENU.equals(menu.getType())) {
                menuTreeList.add(new MenuTree(menu));
            }
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, -1);
    }


    //根据角色返回权限
//    @Cacheable(value = "MenuTree_", key = "caches[0].name+methodName+#roleCodes")
   public  List<String> findPermissionsByRole(String... roleCodes){
/*        QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
        List<String> list =
                queryFactory.select(qRoleMenu.menu.permission).from(qRoleMenu).innerJoin(qRoleMenu.menu).innerJoin(qRoleMenu.role)
                        .where(qRoleMenu.menu.type.eq(DetailConst.BUTTON),
                                qRoleMenu.role.roleCode.in(roleCodes)

                        ).distinct().fetch();*/
       QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
       List<String> list =queryFactory.select(QMenu.menu.permission).from(qRoleMenu,QRole.role,QMenu.menu)
               .where(qRoleMenu.menuId.eq(QMenu.menu.menuId).and(qRoleMenu.roleId.eq(QRole.role.roleId))
                       .and(QMenu.menu.type.eq(DetailConst.BUTTON))
                       .and(QRole.role.roleCode.in(roleCodes))).distinct()
               .fetch();
        return list;

    }


    public R<Boolean> update(UserDTO userDto){
        User olduser = userRepository.findOne(userDto.getUserId());
        userDto.setUpdateTime(new Date());
        try {
            CustomBeanUtils.MergeBean(userDto, olduser, Arrays.asList("userId","salt","avatar","password"),false);
        } catch (Exception e) {
            e.printStackTrace();
            return new R<>(false,e.getMessage());
        }
        List<RoleUser> oldroleUserList= roleUserRepository.
                findRoleUserByUserId(olduser.getUserId());
        Map<String,RoleUser> map = new HashMap();
        for(Integer r: userDto.getRole()){
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(userDto.getUserId());
            roleUser.setRoleId(r);
            map.put(r.toString()+userDto.getUserId().toString(),roleUser);
        }
        for(RoleUser roleUser:oldroleUserList){
            String key =roleUser.getRoleId().toString()+roleUser.getUserId().toString();
            if(map.containsKey(key)){
                map.remove(key);
            }else{
                roleUserRepository.delete(roleUser);
            }
        }
        map.forEach((key,value)->{
            roleUserRepository.save(value);
        });
        userRepository.save(olduser);
        return new R<>(true);
    }



    public User savebyMqUser(MqUser mqUser){

        User olduser = userRepository.findUserByWorkCode(mqUser.getWorkCode());
        if(olduser!=null){

            log.warn(mqUser.getUserName()+"该用户的工号已存在,工号为"+mqUser.getWorkCode());
            throw new RuntimeException(mqUser.getUserName()+"该用户的已工号已存在,工号为"+mqUser.getWorkCode());
        }
        User user = new User();

        user.setUsername(mqUser.getUserName());
        user.setDeptId(mqUser.getDeptId());
        user.setWorkCode(mqUser.getWorkCode());
        user.setStatus(mqUser.getStatus());
        user.setCreateTime(new Date());
        user.setPhone(mqUser.getPhone());
        user.setPassword(ENCODER.encode(DetailConst.DEFAULT_PASSWORD));
        user.setAvatar(DetailConst.USER_AVATAR);
        user =userRepository.save(user);
        return user;


    }

    public Boolean updatebyMqUser(MqUser mqUser){
        log.warn((mqUser.getUserName()+" 开始更新此用户信息=============")+mqUser.toString());
        User olduser = userRepository.findUserByWorkCode(mqUser.getWorkCode());
        if(olduser==null){
            throw new RuntimeException("工号为"+mqUser.getWorkCode()+"的用户在系统里没有数据!");
        }
        olduser.setUpdateTime(new Date());

        olduser.setPhone(mqUser.getPhone());
        olduser.setWorkCode(mqUser.getWorkCode());
        olduser.setDeptId(mqUser.getDeptId());
        if(mqUser.getStatus().equals(DetailConst.STATUS_INVALID)){
            olduser.setStatus(DetailConst.STATUS_INVALID);
            //离职直接更改标志位
            userRepository.save(olduser);
            return true;
        }


        if(mqUser.getRoleIds()!=null &&mqUser.getRoleIds().size()>0){
            List<RoleUser> oldroleUserList= roleUserRepository.
                    findRoleUserByUserId(olduser.getUserId());
            Map<String,RoleUser> map = new HashMap();
            for(Integer r: mqUser.getRoleIds()){
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(olduser.getUserId());
                roleUser.setRoleId(r);
                map.put(r.toString(),roleUser);
            }
            for(RoleUser roleUser:oldroleUserList){
                String key =roleUser.getRoleId().toString();
                if(map.containsKey(key)){
                    map.remove(key);
                }else{
                    roleUserRepository.delete(roleUser);
                }
            }
            map.forEach((key,value)->{
                roleUserRepository.save(value);
            });
        }

        userRepository.save(olduser);
        return true;
    }
}
