package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Items;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.UsersRepository;

@Controller
public class CartController {

	@Autowired
	HttpSession session;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	UsersRepository usersRepository;

	/**
	 * カートの初期処理メソッド
	 * 
	 * @return
	 */
	public Cart getCartFromSession() {
		// セッションのカート情報を取得(なければ作成)
		// 戻ってきた型をCart型にする（書かないとObject型になる）
		Cart cartSession = (Cart) session.getAttribute("cart");

		// カート情報のセッションがない場合、カート初期処理
		if (cartSession == null) {
			cartSession = new Cart();
			// カテゴリ一覧と名前の一覧を取得する
			session.setAttribute("cart", cartSession);
		}
		return cartSession;
	}

	/**
	 * カートの中身を表示する処理
	 * @param mv
	 * @return
	 */
	@RequestMapping("/cart")
	public ModelAndView cartList(ModelAndView mv) {
		//カートのセッション情報を取得
		Cart cartSession = getCartFromSession();
		
		//カートに追加した商品情報と総額を表示
		mv.addObject("items", cartSession.getItems());
		mv.addObject("total", cartSession.getTotal());
		mv.setViewName("shopping/cart");
		return mv;
	}

	/**
	 * カートに追加ボタン押下時の処理(カート追加処理)
	 * 
	 * @param id
	 * @param mv
	 * @return
	 */
	// <a th:href="|/cart/add/${item.id}|">カートに追加</a>
	@RequestMapping("/cart/add/{id}")
	public ModelAndView addCart(@PathVariable("id") int id, ModelAndView mv) {

		// カートの情報を取得
		Cart cartSession = getCartFromSession();

		// 商品コードをもとに、商品情報を取得
		Items item = itemRepository.getById(id);

		// カート情報に商品情報を追加
		cartSession.addCart(item, 1);

		// ページに表示したい情報を設定(商品と合計)
		mv.addObject("items", cartSession.getItems());
		mv.addObject("total", cartSession.getTotal());

		// カートの中身ページ遷移
		mv.setViewName("shopping/cart");
		return mv;
	}
	
	/**
	 * カートの削除処理
	 * @param id
	 * @param mv
	 * @return
	 */
	//		<li><a th:href="|/cart/delete/${item.value.code}|">削除</a></li>
	@RequestMapping("/cart/delete/{id}")
	public ModelAndView deleteCart(
			@PathVariable("id") int id, 
			ModelAndView mv) {

		//カートの情報を取得
		Cart cartSession = getCartFromSession();

		// カートの中からコードが一致するアイテムを削除
		cartSession.deleteCart(id);
		
		// ページ表示に必要なデータを設定
		mv.addObject("items", cartSession.getItems());
		mv.addObject("total", cartSession.getTotal());
		
		// カートの中身ページ遷移
		mv.setViewName("shopping/cart");

		return mv;

	}

	
}
