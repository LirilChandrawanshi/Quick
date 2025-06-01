package com.example.Quick.mapper;

import com.example.Quick.Entity.Order;
import com.example.Quick.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDtoList(List<Order> orders);

    Order toOrder(OrderDto orderDto);
}
