package com.oa.auth.filter;



import com.oa.auth.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
  * @Description:    前端密码处理器
  * @Author:         kk
  * @CreateDate:     2018/12/15
 */
@Slf4j
//@Configuration
//@WebFilter(urlPatterns = "/oauth/token/**",filterName = "loginFilter")
public class DecodePasswordFilter implements Filter {
    private static final String PASSWORD = "password";
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NOPadding";

    private String key="0123451357902468";

    
    private static String decryptAES(String data, String pass) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        SecretKeySpec keyspec = new SecretKeySpec(pass.getBytes(), KEY_ALGORITHM);
        IvParameterSpec ivspec = new IvParameterSpec(pass.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] result = cipher.doFinal(Base64.decode(data.getBytes(SecurityConstants.UTF8)));
        return new String(result, SecurityConstants.UTF8);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ServletContext ctx = request.getServletContext();
        Map<String, String[]> params = request.getParameterMap();

        if (params == null) {
            return ;
        }
        String[] passList = params.get(PASSWORD);
        if (passList==null || passList.length==0) {
            return ;
        }

        String password = passList[0];
        if (!password.isEmpty()) {
            try {
                password = decryptAES(password, key);
            } catch (Exception e) {
                log.error("密码解密失败:{}", password);
            }
            passList[0]=password.trim();
            Map<String, String[]> map = new HashMap<String,String[]>(request.getParameterMap());
            map.put(PASSWORD,passList);

        }

    }

    @Override
    public void destroy() {

    }
}
