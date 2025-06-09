package com.restoreit.repository;

import com.restoreit.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT DISTINCT o FROM Order o JOIN o.products p WHERE p.user.id = :userId AND o.isComplete = false")
    List<Order> findPendingOrdersByUserId(@Param("userId") UUID userId);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.products p WHERE p.user.id = :userId AND o.isComplete = true")
    List<Order> findCompletedOrdersByUserId(@Param("userId") UUID userId);

}
