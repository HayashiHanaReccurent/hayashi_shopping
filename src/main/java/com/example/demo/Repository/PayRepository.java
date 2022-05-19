package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer> {

}
