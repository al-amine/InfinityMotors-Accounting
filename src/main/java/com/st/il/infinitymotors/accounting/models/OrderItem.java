package com.st.il.infinitymotors.accounting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_orderitem")
public class OrderItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderitemId;
	
	@Column(name="orderId")
	private Integer orderId;
	
	@Column(name="carId")
	private Integer carId;

	
	public OrderItem() {}
	
	public OrderItem(Integer orderitemId, Integer orderId, Integer carId) {
		this.orderitemId = orderitemId;
		this.orderId = orderId;
		this.carId = carId;
	}

	public Integer getOrderItemId() {
		return orderitemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderitemId = orderItemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderitemId == null) ? 0 : orderitemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (orderitemId == null) {
			if (other.orderitemId != null)
				return false;
		} else if (!orderitemId.equals(other.orderitemId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderitemId + ", orderId=" + orderId + ", carId=" + carId + "]";
	}
}
