package com.oa.auth.config.security;


import com.oa.auth.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 * 认证服务相关配置
 */
@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore jdbcTokenStore;



    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * 这里使用数据库方式，从sys_oauth_client_details表取客户端信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory().withClient("kk").secret("kk")
                .accessTokenValiditySeconds(SecurityConstants.TOKEN_VALID_SECONDS)
                .authorizedGrantTypes("password","refresh_token","authorization_code")
                    .scopes("server");
    }


    /**
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        //token增强配置

        endpoints
                .tokenStore(jdbcTokenStore)
                //认证管理器，当你选择了资源所有者密码（password）授权类型时，需要注入这个管理器
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false)
                //当你设置了userDetailsService,"refresh_token" 即刷新令牌授权类型模式的流程中就会包含一个检查，用来确保这个账号是否仍然有效
                .userDetailsService(userDetailsService);

    }



    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                //SpEL表达式
                .tokenKeyAccess("isAuthenticated()")// 不是匿名用户就能访问/oauth/check_token， 用于资源服务访问的令牌解析端点
                .checkTokenAccess("permitAll()");//  公共密钥公布在/oauth/token_key，始终允许访问
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * tokenstore 定制化处理
     *当一个令牌被创建了，你必须对其进行保存，这样当一个客户端使用这个令牌对资源服务进行请求的时候才能够引用这个令牌。
     *当一个令牌是有效的时候，它可以被用来加载身份信息，里面包含了这个令牌的相关权限。
     *一般有InMemoryTokenStore，JdbcTokenStore，JwtTokenStore,RedisTokenStore四种方式
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);
        return tokenStore;
    }

}

