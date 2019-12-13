package com.example.springsessionsecurity.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PhoneLoginAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public PhoneLoginAuthenticationTokenFilter() {
        super(new PhoneLoginRequestMatcher());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals("POST") ) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }else {
            String phoneNumber = request.getParameter("phone");
            String oneTimePassword = request.getParameter("otp");
            if (phoneNumber == null) {
                phoneNumber = "";
            }
            if (oneTimePassword == null) {
                oneTimePassword = "";
            }

            PhoneLoginAuthenticationToken authRequest = new PhoneLoginAuthenticationToken(phoneNumber, oneTimePassword);
            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
}
