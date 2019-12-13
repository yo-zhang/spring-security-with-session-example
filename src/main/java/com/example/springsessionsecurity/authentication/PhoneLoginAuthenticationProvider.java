package com.example.springsessionsecurity.authentication;

import com.example.springsessionsecurity.jpa.entity.Account;
import com.example.springsessionsecurity.jpa.repository.AccountRepository;
import com.example.springsessionsecurity.service.OneTimePasswordService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PhoneLoginAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsService myUserDetailsService;

    private final AccountRepository accountRepository;

    private final OneTimePasswordService oneTimePasswordService;

    public PhoneLoginAuthenticationProvider(MyUserDetailsService myUserDetailsService, AccountRepository accountRepository, OneTimePasswordService oneTimePasswordService) {
        this.myUserDetailsService = myUserDetailsService;
        this.accountRepository = accountRepository;
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        PhoneLoginAuthenticationToken authenticationToken = (PhoneLoginAuthenticationToken) authentication;
        String phoneNumber = authenticationToken.getPhoneNumber();
        String oneTimePassword = authenticationToken.getOneTimePassword();

        if(oneTimePassword.equals(oneTimePasswordService.getOtp(phoneNumber))){
            Account account = accountRepository.findByPhone(phoneNumber)
                    .orElseThrow(() -> new UsernameNotFoundException(phoneNumber));

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(account.getId());
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }else {
            throw new BadCredentialsException("incorrect verification code");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (PhoneLoginAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
