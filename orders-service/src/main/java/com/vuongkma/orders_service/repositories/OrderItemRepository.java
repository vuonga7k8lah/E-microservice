package com.vuongkma.orders_service.repositories;

import com.vuongkma.orders_service.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
}
