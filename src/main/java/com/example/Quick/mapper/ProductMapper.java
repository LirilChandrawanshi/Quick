package com.example.Quick.mapper;

import com.example.Quick.Entity.Product;
import com.example.Quick.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);

    Product toProduct(ProductDto productDto);
}
