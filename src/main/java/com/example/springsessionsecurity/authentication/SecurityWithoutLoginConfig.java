//package com.example.security.authentication;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityWithoutLoginConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //authenticated
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .and()
//                .csrf().disable();
//
//        http.requestCache().requestCache(getHttpSessionRequestCache());
//    }
//
//    private HttpSessionRequestCache getHttpSessionRequestCache() {
//        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
//        httpSessionRequestCache.setCreateSessionAllowed(false);
//        return httpSessionRequestCache;
//    }
//}