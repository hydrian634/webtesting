package com.webtesting.demo.service;

import com.webtesting.demo.dto.OrderResponse;
import com.webtesting.demo.dto.OrderItemResponse;
import com.webtesting.demo.model.Order;
import com.webtesting.demo.model.OrderItem;
import com.webtesting.demo.model.User;
import com.webtesting.demo.repository.OrderRepository;
import com.webtesting.demo.repository.OrderItemRepository;
import com.webtesting.demo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
    @Transactional
    public OrderResponse createOrder(User user, Order order) {
        log.info("Creating order for user: {}", user.getId());
        order.setUser(user);
        
        // Calculate total amount
        BigDecimal total = order.getItems().stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        
        Order savedOrder = orderRepository.save(order);
        return convertToResponse(savedOrder);
    }
    
    public List<OrderResponse> getUserOrders(User user) {
        log.info("Fetching orders for user: {}", user.getId());
        return orderRepository.findByUserOrderByCreatedAtDesc(user)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    public OrderResponse getOrderById(Long orderId) {
        log.info("Fetching order with id: {}", orderId);
        return orderRepository.findById(orderId)
            .map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }
    
    public OrderResponse getOrderByNumber(String orderNumber) {
        log.info("Fetching order with number: {}", orderNumber);
        return orderRepository.findByOrderNumber(orderNumber)
            .map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with number: " + orderNumber));
    }
    
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        log.info("Updating order {} status to: {}", orderId, status);
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        try {
            order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid order status: {}", status);
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        
        Order updated = orderRepository.save(order);
        return convertToResponse(updated);
    }
    
    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
            .map(item -> OrderItemResponse.builder()
                .id(item.getId())
                .product(null) // ProductResponse can be added if needed
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build())
            .collect(Collectors.toList());
        
        return OrderResponse.builder()
            .id(order.getId())
            .orderNumber(order.getOrderNumber())
            .status(order.getStatus().toString())
            .shippingAddress(null) // AddressResponse can be added if needed
            .items(items)
            .totalAmount(order.getTotalAmount())
            .createdAt(order.getCreatedAt())
            .updatedAt(order.getUpdatedAt())
            .build();
    }
}
