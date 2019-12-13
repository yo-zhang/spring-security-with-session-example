package com.example.springsessionsecurity.controller;

import com.example.springsessionsecurity.controller.exception.BusinessException;
import com.example.springsessionsecurity.service.OneTimePasswordService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OneTimePasswordController {

    private final OneTimePasswordService oneTimePasswordService;

    public OneTimePasswordController(OneTimePasswordService oneTimePasswordService) {
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @PostMapping("/auth/otp/{phone}")
    public void createOtp(@PathVariable String phone) throws BusinessException {
        oneTimePasswordService.createOtp(phone);
    }


}
