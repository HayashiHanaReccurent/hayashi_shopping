package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * payテーブル(支払い情報のテーブル)
 * @author student
 *
 */
@Entity
@Table(name = "pay")
public class Pay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "credit_no")
	private String creditNo;

	@Column(name = "credit_security")
	private Integer creditSecurity;

	//コンストラクタ
	public Pay() {
		super();
	}
	//主キー以外を引数にしたコンストラクタ
	public Pay(Integer userId, String creditNo, Integer creditSecurity) {
		super();
		this.userId = userId;
		this.creditNo = creditNo;
		this.creditSecurity = creditSecurity;
	}
	// getter
	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public Integer getCreditSecurity() {
		return creditSecurity;
	}
}
