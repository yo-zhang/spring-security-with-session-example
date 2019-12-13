package com.example.springsessionsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SessionDemoController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/testUser")
    public String testUser() {
        System.out.println("USER");
        return "USER";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/testADMIN")
    public String testADMIN() {
        System.out.println("ADMIN");
        return "ADMIN";
    }

    @PreAuthorize("hasRole('DBA')")
    @GetMapping("/testDBA")
    public String testDBA() {
        System.out.println("DBA");
        return "DBA";
    }

    @GetMapping("/remove")
    public void remove(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
    }

}
