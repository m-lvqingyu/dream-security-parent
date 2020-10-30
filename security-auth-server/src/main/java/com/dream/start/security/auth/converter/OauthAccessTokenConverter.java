package com.dream.start.security.auth.converter;

import com.dream.start.security.auth.entity.jwt.Oauth2Token;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author Lv.QingYu
 */
public class OauthAccessTokenConverter {

    public static Oauth2Token tokenConverter(OAuth2AccessToken oAuth2AccessToken) {
        Oauth2Token oauth2Token = Oauth2Token.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .build();
        return oauth2Token;
    }

}
