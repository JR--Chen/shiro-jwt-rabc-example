package com.hakcerda.shirorbacexample.model;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author chenjuanrong
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
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
