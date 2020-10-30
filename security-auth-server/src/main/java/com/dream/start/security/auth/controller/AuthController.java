package com.dream.start.security.auth.controller;

import com.dream.start.security.auth.converter.OauthAccessTokenConverter;
import com.dream.start.security.auth.entity.jwt.Oauth2Token;
import com.dream.start.security.core.result.Result;
import com.dream.start.security.core.result.ResultCode;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author Lv.QingYu
 */
@Slf4j
@RestController
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * 获取Token
     *
     * @param principal
     * @param parameters grant_type:表示授权类型 client_id:客户端唯一标识 client_secret:客户端秘钥 username:用户名 password:密码
     * @return
     */
    @PostMapping("/oauth/token")
    public Result postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) {
        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
            Oauth2Token oauth2Token = OauthAccessTokenConverter.tokenConverter(oAuth2AccessToken);
            return Result.success(oauth2Token);
        } catch (Exception e) {
            log.error("[用户身份信息认证]-失败！用户信息：{}，异常信息：{}", parameters, Throwables.getStackTraceAsString(e));
        }
        return Result.custom(ResultCode.USER_AUTHENTICATION_FAILED);
    }

}
