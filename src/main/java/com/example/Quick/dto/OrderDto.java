package com.example.Quick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private String customerName;
    private String status;
    private LocalDateTime orderTime;
    private List<OrderItemDto> items;
    private String shippingAddress;
    private Double totalAmount;
}
