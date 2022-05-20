package com.example.demo.Entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	// 作ったクラスを配列として扱う
	private Map<Integer, Items> items = new HashMap<>();

	// 合計金額を格納する変数
	private int total;

	// getter
	public Map<Integer, Items> getItems() {
		return items;
	}

	public int getTotal() {
		return total;
	}

	// コンストラクタ
	public Cart() {

	}

	/**
	 * アイテム情報をカートに追加する処理
	 * 
	 * @param item
	 * @param quantity
	 */
	public void addCart(Items item, int quantity) {
		Items existedItem = items.get(item.getId());

		// アイテムが存在しない場合は追加
		if (existedItem == null) {
			// 数量を設定
			item.setQuantity(quantity);

			// リストに追加
			items.put(item.getId(), item);
		}
		// アイテムが存在する場合は追加しないけど数量は追加
		else {
			// もともとのカートにある量に、追加した数量を反映させる
			existedItem.setQuantity(existedItem.getQuantity() + quantity);
		}

		// カートの中身を再計算する
		recalcTotal();
	}

	/**
	 * カートの中身の総合計を算出する処理
	 */
	public void recalcTotal() {
		total = 0;
//		<tr th:each="item:${items}">
		// th:each = "変数" : 配列
		//
		for (Items item : items.values()) {
			// 価格x数量を合計に入れる
			total += item.getPrice() * item.getQuantity();
		}
	}
}
