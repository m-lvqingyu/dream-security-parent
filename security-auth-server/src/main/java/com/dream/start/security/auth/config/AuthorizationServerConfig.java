package com.dream.start.security.auth.config;

import com.dream.start.security.auth.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author Lv.QingYu
 * @description 认证服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 配置客户端详情: 内存方式
     * 客户端从认证服务器获取令牌的时候，必须使用client_id为Tencent，client_secret为123456的标识来获取；
     * 该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌；
     * 在获取client_id为Tencent的令牌的时候，scope只能指定为all，否则将获取失败；
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置client_id
                .withClient("Tencent")
                // 配置client_secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问token的有效期。单位:秒。
                // 比如：设置600秒，则当前时间十分钟以内，通过client_id= Tencent & client_secret 申请令牌是没有问题的，但是超过十分钟，就会申请不到令牌；
                // 不过，当前时间十分钟以内已经申请的令牌，依然可以继续使用
                .accessTokenValiditySeconds(60)
                // 配置刷新token的有效期
                .refreshTokenValiditySeconds(60)
                // 配置grant_type，表示授权类型
                .authorizedGrantTypes("password", "refresh_token")
                // 配置申请的权限范围
                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(authUserDetailsService);
    }

    /**
     * 允许表单认证
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
    }

}
