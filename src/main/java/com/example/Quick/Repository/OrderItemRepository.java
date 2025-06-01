package com.example.Quick.Repository;

import com.example.Quick.Entity.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
    List<OrderItem> findByProductId(String productId);
}
