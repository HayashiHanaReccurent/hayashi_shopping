package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * itemテーブル(商品情報のテーブル)
 * 
 * @author student
 *
 */
@Entity
@Table(name = "items")
public class Items {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "price")
	private Integer price;

	@Column(name = "stock")
	private Integer stock;

	@Column(name = "image")
	private String image;

	@Column(name = "name")
	private String name;

	@Column(name = "delivery_days")
	private Integer deliverDays;
	
	@Column(name = "caption")
	private String caption;
	
	//コンストラクタ
	public Items() {
		super();
	}
	
	public Items(String caption) {
		this.caption = caption;
	}
	
	@Transient
	private Integer quantity;

	// getter
	public Integer getId() {
		return id;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public Integer getDeliverDays() {
		return deliverDays;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
