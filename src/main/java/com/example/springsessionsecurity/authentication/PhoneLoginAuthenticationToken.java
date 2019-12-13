package com.example.springsessionsecurity.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PhoneLoginAuthenticationToken extends AbstractAuthenticationToken {

    private String phoneNumber;

    private String oneTimePassword;

    public PhoneLoginAuthenticationToken(String phoneNumber, String oneTimePassword) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.oneTimePassword = oneTimePassword;
        setAuthenticated(false);
    }

    public PhoneLoginAuthenticationToken(String phoneNumber, String oneTimePassword, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phoneNumber = phoneNumber;
        this.oneTimePassword = oneTimePassword;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }
}
