package com.dream.start.security.auth.helper;

import com.dream.start.security.auth.constant.AuthenticationConstant;
import com.dream.start.security.auth.entity.jwt.OauthUserInfo;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lv.QingYu
 * @description JWT内容增强器
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OauthUserInfo userInfo = (OauthUserInfo) authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(AuthenticationConstant.JWT_USER_UID_KEY, userInfo.getUserUid());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(paramMap);
        return accessToken;
    }

}
