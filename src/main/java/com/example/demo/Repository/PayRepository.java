package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer> {
//	//クレカ番号で絞り込む
//	List<Pay> findByCreditNo(String creditNo);
	List<Pay> findByUserId(Integer userId);
	List<Pay> findByCreditNo(String creditNo);
	//ユーザーidとクレカ番号を絞り込む
	List<Pay> findByUserIdEqualsAndCreditNoEquals(Integer userId, String creditNo);
}
