package com.example.orderprocess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private String errorStatus;
    private String errorMessage;
}
