package com.example.springsessionsecurity.service;

import com.example.springsessionsecurity.controller.exception.BusinessException;
import com.example.springsessionsecurity.jpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OneTimePasswordService {

    private final static long OTP_TTL = 15 * 60;

    private final static long OTP_MIN_INTERVAL = 60;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    AccountRepository accountRepository;

    public void createOtp(String phone) throws BusinessException {
        accountRepository.findByPhone(phone)
                .orElseThrow(() -> new BusinessException("the phone number is not exist"));

        Long ttl = redisTemplate.getExpire(phone);

        if(ttl > 0 && OTP_TTL - ttl < OTP_MIN_INTERVAL){
            throw new BusinessException("one time password short message minimum interval is " + OTP_MIN_INTERVAL + " seconds");
        }

        String code = generateVerificationCode();
        redisTemplate.opsForValue().set(phone, code, OTP_TTL, TimeUnit.SECONDS);
        //TODO call Nofitication servcie
    }

    public String getOtp(String phone) {
        return redisTemplate.opsForValue().get(phone);
    }


    private String generateVerificationCode() {
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
}
