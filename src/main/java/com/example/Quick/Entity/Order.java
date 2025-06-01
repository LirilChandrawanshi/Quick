package com.example.Quick.Entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    private String customerName;
    private String status;
    private LocalDateTime orderTime;
    private List<OrderItem> items;
    private String shippingAddress;
    private Double totalAmount;
}