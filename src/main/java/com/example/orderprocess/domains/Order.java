package com.example.orderprocess.domains;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ToString
public class Order {
    @Id
    @Indexed
    private String id;
    private String orderName;
    private String status;

}
