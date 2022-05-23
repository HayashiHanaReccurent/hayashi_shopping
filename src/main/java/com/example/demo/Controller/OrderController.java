package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderedRepository;
import com.example.demo.Repository.UsersRepository;

@Controller
public class OrderController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OrderedRepository orderedRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	/**
	 * カートの中身を表示する処理
	 * @param mv
	 * @return
	 */
	@RequestMapping("/order")
	public ModelAndView order(
			ModelAndView mv) {
		//カートのセッション情報を取得
		Cart cartSession = getCartFromSession();
		
		
//		Users userInfo = UsersRepository.findById(id).get();
		
		//カートに追加した商品情報と総額を表示
		mv.addObject("items", cartSession.getItems());
		mv.addObject("total", cartSession.getTotal());
		mv.setViewName("shopping/orderItemPage");
		return mv;
	}

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
	 * 未入力チェックメソッド
	 * 
	 * @param text
	 * @return
	 */
	public boolean isNull(String text) {
		// 値がある場合はfalse
		return (text == null || text.length() == 0);
	}

	
}
