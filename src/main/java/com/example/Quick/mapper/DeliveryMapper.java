package com.example.Quick.mapper;

import com.example.Quick.Entity.Delivery;
import com.example.Quick.dto.DeliveryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(target = "orderId", source = "order.id")
    DeliveryDto toDeliveryDto(Delivery delivery);

    List<DeliveryDto> toDeliveryDtoList(List<Delivery> deliveries);

    @Mapping(target = "order.id", source = "orderId")
    Delivery toDelivery(DeliveryDto deliveryDto);
}
