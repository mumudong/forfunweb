package com.mumu.core.filter;


import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 1375914927651089616L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
