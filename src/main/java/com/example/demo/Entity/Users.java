package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

/**
 * userテーブル(ユーザー情報のテーブル)
 * 
 * @author student
 *
 */
@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "tel")
	private String tel;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "addressnum")
	private String addressnum;

	// コンストラクタ
	public Users() {
		super();
	}

	// 主キー以外を引数にしたコンストラクタ
	public Users(String userName, String address, String email, String tel, String name, String password,
			String addressnum) {
		super();
		this.userName = userName;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.name = name;
		this.password = password;
		this.addressnum = addressnum;
	}

	// すべてを引数にしたコンストラクタ
	public Users(Integer id, String userName, String address, String email, String tel, String name, String password,
			String addressnum) {
		super();
		this.id = id;
		this.userName = userName;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.name = name;
		this.password = password;
		this.addressnum = addressnum;
	}

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

	public String getTel() {
		return tel;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getAddressnum() {
		return addressnum;
	}

	public static HttpSession get(int i) {

		return null;
	}
}
