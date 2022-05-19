package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.OrderDetail;

public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Integer>{
}
