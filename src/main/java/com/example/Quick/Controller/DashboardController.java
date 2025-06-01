package com.example.Quick.Controller;

import com.example.Quick.Repository.DeliveryRepository;
import com.example.Quick.Repository.OrderRepository;
import com.example.Quick.Repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Dashboard metrics and statistics")
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Operation(summary = "Get system statistics", description = "Retrieves overall statistics about the system")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();

        // Product statistics
        stats.put("totalProducts", productRepository.count());

        // Order statistics
        stats.put("totalOrders", orderRepository.count());
        stats.put("pendingOrders", orderRepository.findByStatus("PLACED").size());
        stats.put("deliveredOrders", orderRepository.findByStatus("DELIVERED").size());

        // Get today's orders
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        stats.put("todaysOrders", orderRepository.findByOrderTimeBetween(startOfDay, LocalDateTime.now()).size());

        // Delivery statistics
        stats.put("activeDeliveries", deliveryRepository.findByStatus("SCHEDULED").size() + 
                                     deliveryRepository.findByStatus("OUT_FOR_DELIVERY").size());
        stats.put("completedDeliveries", deliveryRepository.findByStatus("DELIVERED").size());

        return ResponseEntity.ok(stats);
    }
}
