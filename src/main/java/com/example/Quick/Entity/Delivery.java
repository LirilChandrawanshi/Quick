package com.example.Quick.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Document(collection = "deliveries")
public class Delivery {
    @Id
    private String id;

    @DocumentReference
    private Order order;
    private String deliveryAddress;
    private String status;
    private LocalDateTime estimatedDeliveryTime;

}
