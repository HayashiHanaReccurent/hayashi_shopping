package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Ordered;

public interface OrderedRepository  extends JpaRepository<Ordered, Integer>{

}
