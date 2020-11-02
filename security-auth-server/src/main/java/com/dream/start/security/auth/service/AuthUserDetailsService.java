package com.dream.start.security.auth.service;

import com.dream.start.security.auth.entity.jwt.OauthUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lv.QingYu
 * @description 加载用户信息
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    private List<OauthUserInfo> userList;

    @PostConstruct
    private void initData() {
        String password = this.passwordEncoder.encode("123456");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("1"));

        OauthUserInfo oauthUserInfo = new OauthUserInfo();
        oauthUserInfo.setUserUid("1");
        oauthUserInfo.setUsername("mack");
        oauthUserInfo.setPassword(password);
        oauthUserInfo.setEnabled(true);
        oauthUserInfo.setClientId("Tancent");
        oauthUserInfo.setAuthorities(authorities);

        userList = new ArrayList<>();
        userList.add(oauthUserInfo);
    }

    /**
     * 通过用户名，获取用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<OauthUserInfo> findUserInfoList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (findUserInfoList == null || findUserInfoList.isEmpty()) {
            throw new UsernameNotFoundException("你输入的帐号或密码不正确，请重新输入！");
        }
        OauthUserInfo user = findUserInfoList.get(0);
        return user;
    }


}
