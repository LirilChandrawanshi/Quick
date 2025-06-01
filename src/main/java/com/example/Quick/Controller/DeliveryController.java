package com.example.Quick.Controller;

import com.example.Quick.Entity.Delivery;
import com.example.Quick.Entity.Order;
import com.example.Quick.Repository.DeliveryRepository;
import com.example.Quick.Repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deliveries")
@Tag(name = "Deliveries", description = "Delivery management endpoints")
public class DeliveryController {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(
            @Parameter(description = "ID of the delivery to retrieve") @PathVariable String id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        return delivery.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        // Validate that the order exists
        if (delivery.getOrder() != null && delivery.getOrder().getId() != null) {
            Optional<Order> order = orderRepository.findById(delivery.getOrder().getId());
            if (order.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Update order status to reflect it's now being delivered
            Order existingOrder = order.get();
            existingOrder.setStatus("OUT_FOR_DELIVERY");
            orderRepository.save(existingOrder);
        }

        delivery.setStatus("SCHEDULED");
        delivery.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDelivery);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<Delivery> updateDeliveryStatus(
            @Parameter(description = "ID of the delivery to update") @PathVariable String id,
            @Parameter(description = "New status for the delivery") @RequestParam String status) {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(id);

        if (existingDelivery.isPresent()) {
            Delivery delivery = existingDelivery.get();
            delivery.setStatus(status);

            // If delivery is completed, update the order status
            if (status.equals("DELIVERED") && delivery.getOrder() != null) {
                Optional<Order> order = orderRepository.findById(delivery.getOrder().getId());
                if (order.isPresent()) {
                    Order orderToUpdate = order.get();
                    orderToUpdate.setStatus("DELIVERED");
                    orderRepository.save(orderToUpdate);
                }
            }

            return ResponseEntity.ok(deliveryRepository.save(delivery));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}/address")
    public ResponseEntity<Delivery> updateDeliveryAddress(
            @Parameter(description = "ID of the delivery to update") @PathVariable String id,
            @Parameter(description = "New delivery address") @RequestParam String address) {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(id);

        if (existingDelivery.isPresent()) {
            Delivery delivery = existingDelivery.get();


            if (delivery.getStatus().equals("DELIVERED")) {
                return ResponseEntity.badRequest().build();
            }

            delivery.setDeliveryAddress(address);
            return ResponseEntity.ok(deliveryRepository.save(delivery));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<Delivery>> getDeliveriesByStatus(
            @Parameter(description = "Status to filter deliveries by") @PathVariable String status) {
        List<Delivery> deliveries = deliveryRepository.findByStatus(status);
        return ResponseEntity.ok(deliveries);
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<Delivery> getDeliveryForOrder(
            @Parameter(description = "ID of the order") @PathVariable String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Delivery> delivery = deliveryRepository.findByOrderId(orderId);
        return delivery.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
