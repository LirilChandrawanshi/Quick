package com.example.Quick.Entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document(collection = "orderItems")
public class OrderItem {
    @Id
    private String id;

    @DocumentReference
    private Product product;

    private Integer quantity;
    private Double price;
}
