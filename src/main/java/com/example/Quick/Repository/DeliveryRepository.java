package com.example.Quick.Repository;

import com.example.Quick.Entity.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    List<Delivery> findByStatus(String status);

    @Query("{'order.$id': ?0}")
    Optional<Delivery> findByOrderId(String orderId);

    List<Delivery> findByEstimatedDeliveryTimeBefore(LocalDateTime time);

    @Query("{'deliveryAddress': {$regex: ?0, $options: 'i'}}")
    List<Delivery> findByAddressContaining(String addressFragment);
}
