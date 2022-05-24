package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Items;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.OrderDetailHistory;
import com.example.demo.Entity.OrderHistory;
import com.example.demo.Entity.Ordered;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderedRepository;
import com.example.demo.Repository.PayRepository;
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

	@Autowired
	PayRepository payRepository;
	
	@Autowired
	ItemRepository itemRepository;

	/**
	 * カートの中身を表示する処理
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/order")
	public ModelAndView order(ModelAndView mv) {
		// カートのセッション情報を取得
		Cart cartSession = getCartFromSession();

		// カートに追加した商品情報と総額を表示
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
	 * !!!!!!!テスト用(クレカ登録なしモード)!!!!!!!!!!!! 注文内容を確認ボタン押下時の処理
	 * 
	 * @param creditNo
	 * @param creditSecurity
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
	public ModelAndView orderConfirm(ModelAndView mv) {
		// カートのセッション情報を取得
		Cart cartSession = getCartFromSession();

		// カートに追加した商品情報と総額を表示
		mv.addObject("items", cartSession.getItems());
		mv.addObject("total", cartSession.getTotal());
		// 注文確認画面に遷移
		mv.setViewName("shopping/orderComplete");

		return mv;
	}

	/**
	 * クレカ登録モード 注文内容を確認ボタン押下時の処理
	 * 
	 * @param creditNo
	 * @param creditSecurity
	 * @param mv
	 * @return
	 */
//	@RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
//	public ModelAndView orderConfirm(@RequestParam("creditNo") String creditNo,
//			@RequestParam(value = "creditSecurity", defaultValue = "") Integer creditSecurity, ModelAndView mv) {
//		// カートのセッション情報を取得(確認画面で総額を表示するため)
//		Cart cartSession = getCartFromSession();
//
//		// カートに追加した商品情報と総額を表示
//		mv.addObject("items", cartSession.getItems());
//		mv.addObject("total", cartSession.getTotal());
//		
//		// 未入力チェック(クレカ番号、セキュリティコード)
//		if (isNull(creditNo) || creditSecurity == null || String.valueOf(creditSecurity).length() < 3) {
//			mv.addObject("message", "未入力の項目があるかクレジットカードの情報が間違っています");
//			mv.setViewName("shopping/orderItemPage");
//			return mv;
//		}
//		
//		// ユーザーの登録情報から主キーを取得
//		Integer usersId = (Integer) session.getAttribute("id");
//
//		// 入力済ならデータベースにクレカ情報を登録
//		Pay newPayInfo = new Pay(usersId, creditNo, creditSecurity);
//		payRepository.saveAndFlush(newPayInfo);
////		int userId = payRepository.saveAndFlush(newPayInfo).getId();
//		
//		// 確認時に使うためにクレカ番号をセッションに追加
//		session.setAttribute("creditNo", creditNo);
//
//		// 注文確認画面に遷移
//		mv.setViewName("shopping/orderComplete");
//
//		return mv;
//	}

	// 注文するボタン押下時の処理
	// <form action="/doOrder" method="post">
	@RequestMapping(value = "/doOrder", method = RequestMethod.POST)
	public ModelAndView doOrder(ModelAndView mv) {
		// 0.カートの情報を取得
		Cart cartSession = getCartFromSession();

		// カートに追加した商品情報と総額を表示
		mv.addObject("items", cartSession.getItems());
		mv.addObject("total", cartSession.getTotal());

		// 主キーを取ってきてね！→取得
		Integer userId = (Integer) session.getAttribute("id");

		// 2.オーダー情報の登録 : Orderedへの登録
		Ordered orderData = new Ordered(userId, new Date(), cartSession.getTotal());
		int orderCode = orderedRepository.saveAndFlush(orderData).getId();

		// 3.オーダー詳細情報の登録 : OrderDetailへの登録
		for (Items item : cartSession.getItems().values()) {
			OrderDetail orderDetail = new OrderDetail(orderCode, item.getId(), item.getQuantity());
			orderDetailRepository.saveAndFlush(orderDetail);

			// カートのセッション情報を消す
			session.removeAttribute("cart");
//			session.setAttribute("cartSession", null);

			// 表示したい画面を設定
			mv.setViewName("shopping/ordered");
		}
		return mv;
	}
	
	// 注文履歴
		@RequestMapping("/orderHistory")
		public ModelAndView history(ModelAndView mv) {
			
			// セッションからユーザー情報を取得
			Users userInfo = getUsersFromSession();
			Users user = (Users) session.getAttribute("userInfo");
			Integer id = user.getId();
//			Users userId = (Users) session.getAttribute("id");
//			Integer id = userId.getId();
			
			// Useridが一致するorder情報一覧を取得
			List<Ordered> orders = orderedRepository.findAllByUserId(id);
			
			// orderidのリストを生成
			Collection<Integer> orderIds = new ArrayList<>();
			for(Ordered order : orders) {
				orderIds.add(order.getId());
			}
			
			// orderedIdIn でorderDetail情報から、詳細情報を取得
			List<OrderDetail> orderdetails = orderDetailRepository.findByOrderedIdIn(orderIds);
			
			// itemidのリストを生成
			Collection<Integer> itemIds = new ArrayList<>();
			for(OrderDetail orderDetail : orderdetails) {
				itemIds.add(orderDetail.getItemId());
			}
			
			// codeIn でitem一覧を取得
			List<Items> items = itemRepository.findByIdIn(itemIds);
			
			// 表示用のクラスOrderHistoryを生成して、それに当てはめる
			
			List<OrderHistory> orderHistories = new ArrayList<>();
			
			for( Ordered order : orders) {
				OrderHistory orderHistory = new OrderHistory();
				// Orderをセット
				orderHistory.setOrder(order);
				
				List<OrderDetailHistory> orderDetailHistories = new ArrayList<>();
				for(OrderDetail orderDetail : orderdetails) {
					if(order.getId() == orderDetail.getOrderedId()){
						OrderDetailHistory orderDetailHistory = new OrderDetailHistory();
						
						orderDetailHistory.setOrderDetail(orderDetail);
						
						for(Items item : items) {
							if(orderDetail.getItemId() == item.getId()){
								orderDetailHistory.setItem(item);
								break;
							}
						}
						orderDetailHistories.add(orderDetailHistory);		
					}
				}		
				orderHistory.setOrderDetails(orderDetailHistories);
				
				orderHistories.add(orderHistory);
			}
			mv.addObject("orderHistories", orderHistories);
					
			mv.setViewName("users/orderHistory");
			return mv;
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

	// ユーザー登録情報の取得メソッド
	public Users getUsersFromSession() {
		Users usersSession = (Users) session.getAttribute("userInfo");
		return usersSession;
	}
}
