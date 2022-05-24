package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Ordered;

public interface OrderedRepository  extends JpaRepository<Ordered, Integer>{
	List<Ordered> findAllByUserId(Integer userid);
}
