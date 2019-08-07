package com.oa.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 权限控制
 * @author: kk
 * @create: 2018-12-22
 **/
@Slf4j
@Service("permissionService")
public class PermissionService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;


        if (principal != null) {
            if ("anonymousUser".equals(principal)) {
//                throw new UnloginException("请先登录认证！");
                return hasPermission;
            }
            if (grantedAuthorityList.isEmpty()) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }
            for (SimpleGrantedAuthority g : grantedAuthorityList) {
                if (g.getAuthority().equals("ROLE_ADMIN")) {
                    return true;
                }
            }

            if (!antPathMatcher.match("/admin/user/**", request.getRequestURI())
                    && !antPathMatcher.match("/admin/menu/userMenu/**", request.getRequestURI())
                    && !antPathMatcher.match("/admin/dept/**", request.getRequestURI())
                    && !antPathMatcher.match("/admin/role/**", request.getRequestURI())
                    && !antPathMatcher.match("/admin/top/**", request.getRequestURI())
                    && !antPathMatcher.match("/admin/portal/**", request.getRequestURI())){
                return false;
            }
        }
        return true;
    }
}
