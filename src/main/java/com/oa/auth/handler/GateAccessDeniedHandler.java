package com.oa.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.oa.auth.constants.SecurityConstants;
import com.oa.auth.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
  * @Description: 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
  *  包装失败信息到PigDeniedException
  * @Author:         kk
  * @CreateDate:     2018/12/14
 */
@Slf4j
@Component
public class GateAccessDeniedHandler extends OAuth2AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;

    /**授权拒绝处理，使用R包装
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        log.info("该用户没有访问权限 {}", request.getRequestURI());
        response.setCharacterEncoding(SecurityConstants.UTF8);
        response.setContentType(SecurityConstants.CONTENT_TYPE);
        R<String> result = new R(new RuntimeException("该用户没有访问权限!"),R.NO_PERMISSION);
        response.setStatus(401);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
