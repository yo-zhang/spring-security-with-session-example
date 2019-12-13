package com.example.springsessionsecurity.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService myUserDetailsService;

    private final AuthenticationProvider phoneLoginAuthenticationProvider;

    private final AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    private final AuthenticationFailureHandler myAuthenticationFailureHandler;

    public SecurityConfig(UserDetailsService myUserDetailsService, AuthenticationProvider phoneLoginAuthenticationProvider, AuthenticationSuccessHandler myAuthenticationSuccessHandler, AuthenticationFailureHandler myAuthenticationFailureHandler) {
        this.myUserDetailsService = myUserDetailsService;
        this.phoneLoginAuthenticationProvider = phoneLoginAuthenticationProvider;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(phoneLoginAuthenticationProvider);
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authenticated
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("accountId")
                .loginProcessingUrl("/auth/login").permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .logout().logoutSuccessHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_NO_CONTENT))
                .logoutUrl("/auth/logout").invalidateHttpSession(true).permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .csrf().disable();

        http.addFilterBefore(phoneLoginAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //sessionManagement
        http.sessionManagement().maximumSessions(5).and().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.requestCache().requestCache(getHttpSessionRequestCache());
    }

    private HttpSessionRequestCache getHttpSessionRequestCache() {
        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
        httpSessionRequestCache.setCreateSessionAllowed(false);
        return httpSessionRequestCache;
    }

    private PhoneLoginAuthenticationTokenFilter phoneLoginAuthenticationTokenFilter() throws Exception {
        PhoneLoginAuthenticationTokenFilter filter = new PhoneLoginAuthenticationTokenFilter();
        filter.setAuthenticationManager(this.authenticationManager());
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return filter;
    }
}