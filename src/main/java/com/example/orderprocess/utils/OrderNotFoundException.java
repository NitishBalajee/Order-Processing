package com.example.orderprocess.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
    private String message;
    private Throwable exception;

    public OrderNotFoundException(String message){
        this.message = message;
    }

    public OrderNotFoundException(String message, Throwable exception){
        this.message = message;
        this.exception = exception;
    }
}
