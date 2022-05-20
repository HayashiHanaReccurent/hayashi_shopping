package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	// List<Item> findByCategoryCode(Integer categoryCode);

	// select * from users where email = 'mm'
	// and password = 'test'
	List<Users> findByUserNameEqualsAndPasswordEquals(String userName, String password);

}
