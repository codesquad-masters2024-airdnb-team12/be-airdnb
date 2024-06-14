package com.airbnb.global.auth.oauth2.exception;

import javax.naming.AuthenticationException;

public class OAuth2ProcessingException extends AuthenticationException {
    public OAuth2ProcessingException(String message) {
        super(message);
    }
}