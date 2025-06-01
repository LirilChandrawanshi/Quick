package com.example.Quick.Repository;

import com.example.Quick.Entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStatus(String status);
    List<Order> findByCustomerNameIgnoreCase(String customerName);
    List<Order> findByOrderTimeBetween(LocalDateTime start, LocalDateTime end);

}
