package com.dream.start.security.auth.entity.jwt;

import lombok.Builder;
import lombok.Data;

/**
 * @author Lv.QingYu
 */
@Data
@Builder
public class Oauth2Token {

    private String token;

    private String refreshToken;

    private int expiresIn;

}
