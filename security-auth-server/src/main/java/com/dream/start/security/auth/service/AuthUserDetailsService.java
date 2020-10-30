package com.dream.start.security.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    
    private List<User> userList;

    @PostConstruct
    private void initData() {
        String password = this.passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("mack", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("jack", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("ruth", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
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
        List<User> findUserInfoList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (findUserInfoList == null || findUserInfoList.isEmpty()) {
            throw new UsernameNotFoundException("你输入的帐号或密码不正确，请重新输入！");
        }
        User user = findUserInfoList.get(0);
        User newUser = new User(user.getUsername(), user.getPassword(), user.getAuthorities());
        return newUser;
    }

}
