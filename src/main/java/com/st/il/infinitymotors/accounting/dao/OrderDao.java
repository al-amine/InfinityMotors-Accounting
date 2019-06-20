package com.st.il.infinitymotors.accounting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.st.il.infinitymotors.accounting.models.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
	
}
