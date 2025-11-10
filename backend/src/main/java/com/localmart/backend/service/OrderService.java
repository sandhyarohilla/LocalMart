package com.localmart.backend.service;

import com.localmart.backend.dto.order.OrderResponse;
import com.localmart.backend.dto.order.OrderItemResponse;
import com.localmart.backend.model.Customer;
import com.localmart.backend.model.Order;
import com.localmart.backend.model.Seller;
import com.localmart.backend.repository.CustomerRepository;
import com.localmart.backend.repository.OrderRepository;
import com.localmart.backend.repository.ProductRepository;
import com.localmart.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Gets the order history for the currently logged-in customer.
     */
    public List<OrderResponse> getOrdersForCustomer() {
        Customer customer = getAuthenticatedCustomer();
        List<Order> orders = orderRepository.findByCustomer_CustomerIdOrderByOrderDateDesc(customer.getCustomerId());
        return orders.stream()
                .map(this::mapToOrderResponse) // Convert each Order to an OrderResponse DTO
                .collect(Collectors.toList());
    }

    /**
     * Gets all received orders for the currently logged-in seller.
     */
    public List<OrderResponse> getOrdersForSeller() {
        Seller seller = getAuthenticatedSeller();
        List<Order> orders = orderRepository.findOrdersBySellerId(seller.getSellerId());
        return orders.stream()
                .map(this::mapToOrderResponse) // Reuse the same DTO
                .collect(Collectors.toList());
    }

    /**
     * Approves an order (changes status to "Approved").
     * Only the seller who owns products in the order can approve it.
     */
    @Transactional
    public OrderResponse approveOrder(Long orderId) {
        Seller seller = getAuthenticatedSeller();
        
        // Use the method that eagerly loads order items and products
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Verify that the seller owns at least one product in this order
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new RuntimeException("Order has no items");
        }
        
        boolean sellerOwnsProduct = order.getOrderItems().stream()
                .anyMatch(item -> {
                    if (item.getProduct() == null || item.getProduct().getSeller() == null) {
                        return false;
                    }
                    return item.getProduct().getSeller().getSellerId().equals(seller.getSellerId());
                });
        
        if (!sellerOwnsProduct) {
            throw new SecurityException("You do not have permission to approve this order. This order does not contain any products from your store.");
        }
        
        String currentStatus = order.getStatus();
        if (currentStatus == null || !"Pending".equalsIgnoreCase(currentStatus)) {
            throw new RuntimeException("Only pending orders can be approved. Current status: " + currentStatus);
        }
        
        order.setStatus("Approved");
        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    /**
     * Rejects an order (changes status to "Rejected").
     * Only the seller who owns products in the order can reject it.
     * When rejecting, we should restore stock quantities.
     */
    @Transactional
    public OrderResponse rejectOrder(Long orderId) {
        Seller seller = getAuthenticatedSeller();
        
        // Use the method that eagerly loads order items and products
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Verify that the seller owns at least one product in this order
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new RuntimeException("Order has no items");
        }
        
        boolean sellerOwnsProduct = order.getOrderItems().stream()
                .anyMatch(item -> {
                    if (item.getProduct() == null || item.getProduct().getSeller() == null) {
                        return false;
                    }
                    return item.getProduct().getSeller().getSellerId().equals(seller.getSellerId());
                });
        
        if (!sellerOwnsProduct) {
            throw new SecurityException("You do not have permission to reject this order. This order does not contain any products from your store.");
        }
        
        String currentStatus = order.getStatus();
        if (currentStatus == null || !"Pending".equalsIgnoreCase(currentStatus)) {
            throw new RuntimeException("Only pending orders can be rejected. Current status: " + currentStatus);
        }
        
        // Restore stock for products owned by this seller
        order.getOrderItems().stream()
                .filter(item -> {
                    if (item.getProduct() == null || item.getProduct().getSeller() == null) {
                        return false;
                    }
                    return item.getProduct().getSeller().getSellerId().equals(seller.getSellerId());
                })
                .forEach(item -> {
                    item.getProduct().setStockQuantity(
                            item.getProduct().getStockQuantity() + item.getQuantity()
                    );
                    productRepository.save(item.getProduct());
                });
        
        order.setStatus("Rejected");
        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    // --- HELPER METHODS ---

    private Customer getAuthenticatedCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
    }

    private Seller getAuthenticatedSeller() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return sellerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found"));
    }

    /**
     * Converts an Order Entity into an OrderResponse DTO.
     */
    private OrderResponse mapToOrderResponse(Order order) {
        
        // --- THIS IS THE FIX (Lines 73-77) ---
        // We use "OrderItemResponse" directly, not "OrderResponse.OrderItemResponse"
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtPurchase(),
                        item.getProduct().getImageUrl()
                ))
                .collect(Collectors.toList());
        // ------------------------------------

        // Create the main OrderResponse DTO
        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getShippingAddress(),
                itemResponses
        );
    }
}