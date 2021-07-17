package com.example.orderprocess.utils;

import com.example.orderprocess.dto.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        String authHeader = Collections.list(httpServletRequest.getHeaderNames()).stream()
                .filter(h -> h.equalsIgnoreCase("Authorization"))
                .findFirst()
                .orElse(null);
        if(Strings.isBlank(authHeader) || Strings.isBlank(httpServletRequest.getHeader(authHeader))){
            returnResponse(httpServletResponse, HttpStatus.BAD_REQUEST, "Authentication should be provided");
            return;
        }
        returnResponse(httpServletResponse, HttpStatus.UNAUTHORIZED, "UnAuthorized");
    }

    private void returnResponse(HttpServletResponse httpServletResponse, HttpStatus status, String msg) throws IOException {
        byte[] response =new ObjectMapper().writeValueAsString(new Error(status.toString(), msg)).getBytes();
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setStatus(status.value());
        httpServletResponse.getOutputStream().write(response);
    }

    @Override
    public void afterPropertiesSet(){
        setRealmName("Order Processing");
        super.afterPropertiesSet();
    }
}
