package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * userテーブル(ユーザー情報のテーブル)
 * @author student
 *
 */
@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "tell")
	private String tell;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	// getter
	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getTell() {
		return tell;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
}
