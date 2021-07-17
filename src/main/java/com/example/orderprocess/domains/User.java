package com.example.orderprocess.domains;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {
    @Indexed(unique = true)
    private String userName;
    private String password;
}
