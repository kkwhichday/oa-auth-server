package com.oa.auth.controller;

import com.google.common.base.Joiner;
import com.oa.auth.constants.DetailConst;
import com.oa.auth.model.*;
import com.oa.auth.repositories.DeptRepository;
import com.oa.auth.repositories.RoleUserRepository;
import com.oa.auth.repositories.UserRepository;
import com.oa.auth.repositories.VideoRepository;
import com.oa.auth.service.UserService;
import com.oa.auth.util.Query;
import com.oa.auth.vo.R;
import com.oa.auth.vo.UserDTO;
import com.oa.auth.vo.UserInfo;
import com.oa.auth.vo.UserVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

/**
 * @description: 用户
 * @author: kk
 * @create: 2018-12-22
 **/
@RestController
@RequestMapping("/admin/user")
public class UserController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private VideoRepository videoRepository;


    /*
     * @Author kk
     * @Description 返回教学视频是否都看过，true:看过
     * @Date 2019/1/29
     * @Param []
     * @return java.lang.Boolean
     **/
    @GetMapping("/video/status")
    public Boolean getReadStatus(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user=userRepository.findUserByWorkCode(authentication.getName());
        List<String> vids=null;
        if(!StringUtils.isEmpty(user.getSalt())){
            vids = Arrays.asList(user.getSalt().split(","));
        }else{
            return false;
        }
        List<String> src = videoRepository.findSrcByStatus(DetailConst.STATUS_NORMAL);

        return vids.contains(src);
    }

    @GetMapping("/video/src")
    public List<String> getVideoSrc(){

        List<String> src = videoRepository.findSrcByStatus(DetailConst.STATUS_NORMAL);
        return src;
    }

    @PutMapping(value="/video/src/{srcpath:.+}",produces =  "application/json;charset=UTF-8")
    public R putVideoReadFlag(@PathVariable String srcpath){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user=userRepository.findUserByWorkCode(authentication.getName());
        List<String> vids=new ArrayList<>();
        if(!StringUtils.isEmpty(user.getSalt())){
            vids.addAll(Arrays.asList(user.getSalt().split(",")));

        }
        Video video = videoRepository.findByStatusAndSrc(DetailConst.STATUS_NORMAL,srcpath);
        if(!vids.contains(video.getSrc())){
            vids.add(video.getSrc());
            user.setSalt(Joiner.on(",").join(vids));
            userRepository.save(user);
        }

        return new R<>(Boolean.TRUE);
    }


    @GetMapping("/info")
    public UserInfo user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user=userRepository.findUserByWorkCode(authentication.getName());
        UserInfo userInfo = new UserInfo();
        List<String> list = roleUserRepository.findRoleByUserId(user.getUserId());

        String[] rolecodes = new String[list.size()];

        list.toArray(rolecodes);
        List<String> permission = userService.findPermissionsByRole(rolecodes);
        String[] array = new String[permission.size()];
        userInfo.setPermissions(permission.toArray(array));
        userInfo.setUser(user);
        userInfo.setRoles(rolecodes);

        return userInfo;

    }

    @GetMapping("/info/detail")
    public UserVO getUserDetail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findUserByWorkCode(authentication.getName());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        List<Role> rolelist = roleUserRepository.findRoleListByUserId(user.getUserId());
        userVO.setRoleList(rolelist);
        String deptname = deptRepository.findDeptByDeptId(user.getDeptId()).getName();
        userVO.setDeptName(deptname);
        return userVO;
    }


    /**
     * 分页查询用户
     *
     * @param params 参数集
     * @return 用户集合
     */
    @RequestMapping("/userPage")
    public Page<User> userPage(@RequestParam Map<String, Object> params) {

        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        Object username = params.get("username");
        if(!StringUtils.isEmpty(username)){
            builder.and(qUser.username.like("%"+username+"%"));
        }
        Pageable pageable = new Query(params).getPageable();
        Page<User> page = userRepository.findAll(builder,pageable);
//        Page<User> page =  userRepository.findAll(pageable);
        List<UserVO> list = new ArrayList<>(page.getNumberOfElements());
        page.getContent().forEach(user->{
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            List<Role> rolelist = roleUserRepository.findRoleListByUserId(user.getUserId());
            userVO.setRoleList(rolelist);
            String deptname = deptRepository.findDeptByDeptId(user.getDeptId()).getName();
            userVO.setDeptName(deptname);
            list.add(userVO);
        });


        return  new PageImpl(list,pageable,page.getTotalElements());
    }

    @GetMapping("/{userid}")
    public User userbyid(@PathVariable Integer userid){

        User user = userRepository.findUserByUserId(userid);

        return user;

    }

    @PostMapping
    public R<Boolean> user(@RequestBody UserDTO userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setCreateTime(new Date());
        user.setStatus(DetailConst.STATUS_NORMAL);
        user.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        user.setAvatar(DetailConst.USER_AVATAR);
        user =userRepository.save(user);
        Integer userId = user.getUserId();

        userDto.getRole().forEach(roleId -> {
            RoleUser userRole = new RoleUser();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            roleUserRepository.save(userRole);
        });
        return new R<>(Boolean.TRUE);
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping
    public R<Boolean> userUpdate(@RequestBody UserDTO userDto) {


        return  userService.update(userDto);
    }


    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @DeleteMapping("/{id}")
    public R<Boolean> userDel(@PathVariable Integer id) {
//        User User = userRepository.findOne(id);
        userRepository.delete(id);
        return new R<>(true);
    }


    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @PutMapping("/editInfo")
    public R<Boolean> editInfo(@RequestBody UserDTO userDto) {
//        User olduser = userRepository.findUserByUsername(userDto.getUsername());
        User olduser = userRepository.findUserByUsernameAndPhone(
                userDto.getUsername(),userDto.getPhone());
        if(!ENCODER.matches(userDto.getPassword(),olduser.getPassword())){
            return new R<>(false,"原始密码不正确！");
        }
        userDto.setUpdateTime(new Date());

        olduser.setAvatar(userDto.getAvatar());
        olduser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        olduser.setPhone(userDto.getPhone());
        userRepository.save(olduser);
        return new R<>(true);
    }

}
