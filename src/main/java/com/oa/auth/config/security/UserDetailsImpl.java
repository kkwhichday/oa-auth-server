package com.oa.auth.config.security;

import com.oa.auth.constants.DetailConst;
import com.oa.auth.constants.SecurityConstants;
import com.oa.auth.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @description: 实现springSecurity的用户信息接口
 * @author: kk
 * @create: 2018-12-22
 **/
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user=null;
    private List<String> roles;

    public UserDetailsImpl(User user,List<String> rolelist) {
       this.user = user;
       this.roles =rolelist;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();

        for(String role:roles) {
            authorityList.add(new SimpleGrantedAuthority(role));
        }

        authorityList.add(new SimpleGrantedAuthority(SecurityConstants.BASE_ROLE));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getWorkCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(DetailConst.STATUS_NORMAL) ? true : false;
    }

}
