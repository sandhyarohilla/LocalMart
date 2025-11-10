package com.localmart.backend.repository;

import com.localmart.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // 1. ADD IMPORT
import org.springframework.data.repository.query.Param; // 2. ADD IMPORT
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Finds all orders placed by a specific customer
    List<Order> findByCustomer_CustomerIdOrderByOrderDateDesc(Long customerId);

    // --- 3. ADD THIS NEW METHOD ---
    /**
     * Finds all orders that contain at least one product from a specific seller.
     * It uses DISTINCT to ensure each order appears only once.
     * Uses JOIN FETCH to eagerly load order items and products.
     */
    @Query("SELECT DISTINCT o FROM Order o " +
           "JOIN FETCH o.orderItems oi " +
           "JOIN FETCH oi.product p " +
           "JOIN FETCH p.seller " +
           "WHERE p.seller.sellerId = :sellerId " +
           "ORDER BY o.orderDate DESC")
    List<Order> findOrdersBySellerId(@Param("sellerId") Long sellerId);
    
    /**
     * Finds an order by ID with all order items and products eagerly loaded.
     */
    @Query("SELECT o FROM Order o " +
           "LEFT JOIN FETCH o.orderItems oi " +
           "LEFT JOIN FETCH oi.product p " +
           "LEFT JOIN FETCH p.seller " +
           "WHERE o.orderId = :orderId")
    java.util.Optional<Order> findByIdWithItems(@Param("orderId") Long orderId);
}