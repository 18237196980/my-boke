package com.bo.ke.myboke.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义的web端认证token
 */
public class WebToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    private final String token;

    public WebToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
