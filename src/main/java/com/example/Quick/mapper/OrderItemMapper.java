package com.example.Quick.mapper;

import com.example.Quick.Entity.OrderItem;
import com.example.Quick.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    List<OrderItemDto> toOrderItemDtoList(List<OrderItem> orderItems);

    @Mapping(target = "product.id", source = "productId")
    OrderItem toOrderItem(OrderItemDto orderItemDto);
}
