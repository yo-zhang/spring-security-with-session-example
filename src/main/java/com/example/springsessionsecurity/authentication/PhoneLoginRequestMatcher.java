package com.example.springsessionsecurity.authentication;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class PhoneLoginRequestMatcher implements RequestMatcher {

    private final static String LOGIN_URL = "/auth/login";

    private final static String AUTH_HEADER_NAME = "auth-type";

    private final static String AUTH_TYPE = "otp";

    @Override
    public boolean matches(HttpServletRequest request) {
        String authType = request.getHeader(AUTH_HEADER_NAME);
        return new AntPathRequestMatcher(LOGIN_URL).matches(request) && AUTH_TYPE.equals(authType);
    }
}
