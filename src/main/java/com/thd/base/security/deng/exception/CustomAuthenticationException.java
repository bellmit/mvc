package com.thd.base.security.deng.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
