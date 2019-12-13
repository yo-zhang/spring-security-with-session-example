package com.example.springsessionsecurity.controller.exception;

public class BusinessException extends Exception {


    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

}
