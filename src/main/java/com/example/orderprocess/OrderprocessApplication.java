package com.example.orderprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class OrderprocessApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderprocessApplication.class, args);
	}

}
