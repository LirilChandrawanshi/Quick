package com.example.Quick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private String id;
    private String orderId;
    private String deliveryAddress;
    private String status;
    private LocalDateTime estimatedDeliveryTime;
}
