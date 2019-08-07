package com.oa.auth.listener;


import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.Role;
import com.oa.auth.model.RoleUser;
import com.oa.auth.model.User;
import com.oa.auth.repositories.RoleRepository;
import com.oa.auth.repositories.RoleUserRepository;
import com.oa.auth.service.UserService;
import com.oa.auth.vo.MqUser;

import ins.platform.employee.vo.EmployeeInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 监听用户信息的变更
 * @author: kk
 * @create: 2019-01-07
 **/
@Component
@Slf4j
public class UserMsgMqListener {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleUserRepository roleUserRepository;
    @Autowired
    private RoleRepository roleRepository;
//    @RabbitHandler
    @RabbitListener(queues="employeeInfo.auth")
    public void process(EmployeeInfoVo employeeInfoVo) {
//        System.out.println("receiver:"+hello);

        MqUser mqUser = packUser(employeeInfoVo);
        if(mqUser==null){
            return ;
        }

        try{
            if(mqUser.getFlag()==0){
                User user = userService.savebyMqUser(mqUser);
                mqUser.getRoleIds().forEach(roleId -> {
                    RoleUser userRole = new RoleUser();
                    userRole.setUserId(user.getUserId());
                    userRole.setRoleId(roleId);
                    roleUserRepository.save(userRole);
                });
            }else{

                userService.updatebyMqUser(mqUser);
            }
        }catch (Exception e){
            log.error("保存数据出现异常!");
            e.printStackTrace();
        }


    }

    private MqUser packUser(EmployeeInfoVo employeeInfoVo){
        if(employeeInfoVo==null){
            log.error("清分用户数据为null，请数据导入系统排查问题!");
            return null;
        }
        MqUser mqUser = new MqUser();
        if(employeeInfoVo.getIsNew()!=null){
            if( employeeInfoVo.getIsNew().equals(1)){
                mqUser.setFlag(0);//新增
            }else{
                mqUser.setFlag(1);//更新
            }
        }else{
            log.error("清分用户数据的更新标志位为null，请数据导入系统排查问题！==="+employeeInfoVo);
            return null;
        }
        if(StringUtils.isEmpty(employeeInfoVo.getUsername())||StringUtils.isEmpty(employeeInfoVo.getUsercode())){
            log.error("清分用户数据的姓名或者工号为空，请数据导入系统排查问题！==="+employeeInfoVo);
            return null;
        }
        mqUser.setWorkCode(employeeInfoVo.getUsercode());
        mqUser.setUserName(employeeInfoVo.getUsername());
        mqUser.setPhone(employeeInfoVo.getTelephone());
        mqUser.setDeptId(employeeInfoVo.getProjectId());
        List<Integer> roles =new ArrayList<>();
        Role role =roleRepository.findRoleByRoleCode(employeeInfoVo.getJobId());
        if(role ==null){
            log.error("清分用户数据的岗位代码为null，请数据导入系统排查问题！==="+employeeInfoVo);
            return null;
        }
        roles.add(role.getRoleId());
        mqUser.setRoleIds(roles);
        if(employeeInfoVo.getFlag().equals("0")){
            //离职
            mqUser.setStatus(DetailConst.STATUS_INVALID);
        }else{
            mqUser.setStatus(DetailConst.STATUS_NORMAL);
        }
        return mqUser;
    }
}
