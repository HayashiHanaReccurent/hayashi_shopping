package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Items;

public interface ItemRepository extends JpaRepository<Items, Integer>{

}
