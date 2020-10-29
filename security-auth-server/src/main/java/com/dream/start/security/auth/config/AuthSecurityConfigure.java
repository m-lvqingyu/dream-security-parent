package com.dream.start.security.auth.config;

import com.dream.start.security.auth.constant.AuthenticationConstant;
import com.dream.start.security.auth.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Lv.QingYu
 * @description 用于处理/oauth开头的请求，Spring Cloud OAuth内部定义的获取令牌，刷新令牌的请求地址都是以/oauth/开头的，
 * 也就是说AuthSecurityConfigure用于处理和令牌相关的请求。
 */
@Order(2)
@EnableWebSecurity
public class AuthSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 主要配置身份认证来源，也就是用户及其角色
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
        super.configure(auth);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] urlArray = AuthenticationConstant.DEFAULT_OAUTH_AUTHORITY_VERIFICATION_URL;
        // 只处理 urlArray 数组中所定义的请求
        http.requestMatchers().antMatchers(urlArray)
                .and()
                // urlArray 数组中所定义的请求都需要经过验证
                .authorizeRequests().antMatchers(urlArray).authenticated()
                .and()
                // 禁用CSRF（跨域防护）
                .csrf().disable();
        super.configure(http);
    }
}
