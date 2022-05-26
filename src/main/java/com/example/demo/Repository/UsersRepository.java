package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	//ユーザーネームとパスワードを絞り込む
	List<Users> findByUserNameEqualsAndPasswordEquals(String userName, String password);
	List<Users> findAllByEmail(String email);
}
