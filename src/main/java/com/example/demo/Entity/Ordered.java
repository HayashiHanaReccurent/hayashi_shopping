package com.example.demo.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * orderテーブル(注文情報のテーブル)
 * @author student
 *
 */
@Entity
@Table(name = "ordered")
public class Ordered {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "ordered_date")
	private Date orderedDate;

	@Column(name = "total_price")
	private Integer totalPrice;

	//コンストラクタ
	public Ordered() {
		super();
	}
	public Ordered(Integer userId, Date orderDate, Integer totalPrice) {
		super();
		this.userId = userId;
		this.orderedDate = orderDate;
		this.totalPrice = totalPrice;
		}
	// getter
	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}
}
