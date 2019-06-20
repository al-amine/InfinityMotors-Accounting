package com.st.il.infinitymotors.accounting.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.st.il.infinitymotors.accounting.models.OrderItem;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {

	List<OrderItem> findAllByOrderId(int orderId);
}
