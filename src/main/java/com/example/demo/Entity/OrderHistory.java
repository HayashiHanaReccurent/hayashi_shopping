package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
	private Ordered order;
	private List<OrderDetailHistory> orderDetails = new ArrayList<>();
	
	public OrderHistory() {
		super();
	}
	public Ordered getOrder() {
		return order;
	}
	public void setOrder(Ordered order) {
		this.order = order;
	}
	public List<OrderDetailHistory> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailHistory> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
