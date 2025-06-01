package com.example.Quick.Controller;

import com.example.Quick.Entity.Order;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders in the system")
    @ApiResponse(responseCode = "200", description = "Orders found and returned")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @Operation(summary = "Get order by ID", description = "Retrieves a specific order by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found", 
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @Parameter(description = "ID of the order to retrieve") @PathVariable String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Place a new order", description = "Creates a new order with the current time and PLACED status")
    @ApiResponse(responseCode = "201", description = "Order successfully created")
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("PLACED");
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @Operation(summary = "Update order status", description = "Updates the status of an existing order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found", 
                    content = @Content)
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @Parameter(description = "ID of the order to update") @PathVariable String id,
            @Parameter(description = "New status for the order") @RequestParam String status) {
        Optional<Order> existingOrder = orderRepository.findById(id);

        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setStatus(status);
            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Cancel an order", description = "Cancels an existing order by setting its status to CANCELLED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found", 
                    content = @Content),
        @ApiResponse(responseCode = "400", description = "Order cannot be cancelled", 
                    content = @Content)
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @Parameter(description = "ID of the order to cancel") @PathVariable String id) {
        Optional<Order> existingOrder = orderRepository.findById(id);

        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            // Can only cancel orders that are not already delivered or cancelled
            if (!order.getStatus().equals("DELIVERED") && !order.getStatus().equals("CANCELLED")) {
                order.setStatus("CANCELLED");
                return ResponseEntity.ok(orderRepository.save(order));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get orders by status", description = "Retrieves all orders with a specific status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @Parameter(description = "Status to filter orders by") @PathVariable String status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get orders by customer", description = "Retrieves all orders for a specific customer")
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(
            @Parameter(description = "Name of the customer") @PathVariable String customerName) {
        List<Order> orders = orderRepository.findByCustomerNameIgnoreCase(customerName);
        return ResponseEntity.ok(orders);
    }
}
