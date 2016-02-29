package com.thd.base.security.deng;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 5414106440823275021L;

    public CustomAuthenticationToken(String principal, String credentials, String corpCode) {
        super(principal, credentials);
        this.corpCode = corpCode;
    }

    private String corpCode;

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}