package com.example.demo.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.OrderDetail;

public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Integer>{
	List<OrderDetail> findByOrderedIdIn(Collection<Integer> orderIds);
}
