package com.dream.start.security.auth.config;

import com.dream.start.security.auth.constant.AuthenticationConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author LvQingYu
 * @description 用于处理非/oauth/开头的请求，其主要用于资源的保护，客户端只能通过OAuth2协议发放的令牌来从资源服务器中获取受保护的资源。
 */
@Configuration
@EnableResourceServer
public class AuthResourceServerConfigure extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] urlArray = AuthenticationConstant.AUTHORITY_VERIFICATION_URL;
        // urlArray数组中的请求都需要经过认证
        http.requestMatchers().antMatchers(urlArray)
                .and()
                .authorizeRequests().antMatchers(urlArray).authenticated()
                .and()
                .csrf().disable();
    }
}
