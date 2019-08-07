package com.oa.auth.config.security;



import com.oa.auth.model.User;
import com.oa.auth.repositories.RoleUserRepository;
import com.oa.auth.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/** 
  * @Description:     
  * @Author:         kk
  * @CreateDate:     2018/12/11
 */
@Slf4j
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    private static final String PASSWORD = "password";
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NOPadding";

    private String key="0123451357902468";
    @Autowired
    private RoleUserRepository roleUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        user = userRepository.findUserByWorkCode(username);
        if(user==null){

            log.warn("工号为"+username+"成功登陆");
            user = userRepository.findUserByUsername(username);
        }
        List<String> list = roleUserRepository.findRoleByUserId(user.getUserId());
        return new UserDetailsImpl(user,list);

    }

}
