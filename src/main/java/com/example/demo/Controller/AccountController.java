package com.example.demo.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Items;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.UsersRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ItemRepository itemRepository;

	/**
	 * 初期画面設定(ログイン画面の表示)
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/")
	public String start() {
		// セッション情報の削除
		session.invalidate();
		return "loginLogout/index";
	}

	// 新規登録画面に遷移する処理
	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {
		mv.setViewName("loginLogout/signup");
		return mv;
	}

	/**
	 * 新規登録ページで登録ボタンを押下した時の処理
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @param address
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signup(@RequestParam("userName") String userName, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("address") String address, @RequestParam("tel") String tel,
			@RequestParam("addressnum") String addressnum, ModelAndView mv) {
		// 未入力チェック
		if (isNull(userName) || isNull(password) || isNull(email) || isNull(email) || isNull(name) || isNull(address)
				|| isNull(tel)) {
			// 未入力ならエラーメッセージを表示、登録画面を再度表示
			mv.addObject("message", "未入力の項目があります。");
			mv.setViewName("loginLogout/signup");
			return mv;
		}
		// チェックを通過したらデータベースに反映させる
		Users newUserAccount = new Users(userName, address, email, tel, name, password, addressnum);
		usersRepository.saveAndFlush(newUserAccount);

		// 新規登録完了 画面に遷移
		mv.setViewName("loginLogout/completeSignup");

		return mv;
	}

	// ログインボタン押下時の処理(ログイン処理)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("userName") String userName, @RequestParam("password") String password,
			ModelAndView mv) {
		// 未入力チェック
		if (isNull(userName) || isNull(password)) {
			// 未入力ならエラーメッセージを表示
			mv.addObject("message", "未入力の項目があります。");
			mv.setViewName("loginLogout/index");
			return mv;
		}

		// 入力済ならログインチェックに入る
		// 登録されたユーザー情報をすべて取得する
		List<Users> userList = usersRepository.findAll();

		// 入力された情報がデータベースにあるか調べる
		userList = usersRepository.findByUserNameEqualsAndPasswordEquals(userName, password);

		// 一致する情報がない場合はエラーメッセージを表示
		if (userList.size() == 0) {
			mv.addObject("message", "ユーザーネーム、またはパスワードが違います");
			mv.setViewName("loginLogout/index");
			return mv;
		}

		// 照合できたら商品情報をすべて取得
		List<Items> itemList = itemRepository.findAll();
		mv.addObject("items", itemList);

		// ユーザーネームをセッションに入れてトップページに遷移(ヘッダー表示用)
		session.setAttribute("userList", userList);
		// 配送情報の入力時に使うので住所などもセッションに入れる
		session.setAttribute("userInfo", userList.get(0));
		session.setAttribute("id", userList.get(0).getId());
//		session.setAttribute("name", userList.get(0).getName());
//		session.setAttribute("password", userList.get(0).getPassword());
//		session.setAttribute("email", userList.get(0).getEmail());
//		session.setAttribute("addressnum", userList.get(0).getAddressnum());
//		session.setAttribute("address", userList.get(0).getAddress());
//		session.setAttribute("tel", userList.get(0).getTel());
		mv.setViewName("shopping/itemView");
		return mv;
	}

	// マイページ ボタン押下時の処理
	@RequestMapping("/mypage")
	public ModelAndView mypage(ModelAndView mv) {
		mv.setViewName("users/mypage");
		return mv;
	}

	// 登録情報を更新ページに遷移時の処理
	@RequestMapping("/editInfo")
	public ModelAndView editInfo(ModelAndView mv) {
		// セッション情報を再取得する
		Users userInfo = getUsersFromSession();
		session.setAttribute("userInfo", userInfo);
		mv.addObject("userInfo", session.getAttribute("userInfo"));
		//登録情報の変更ページに遷移
		mv.setViewName("users/editInfo");
		return mv;
	}

	// 登録情報を更新ボタン押下時の処理
	@RequestMapping(value = "/editInfo", method = RequestMethod.POST)
	public ModelAndView editInfo(@RequestParam("userName") String userName, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("addressnum") String addressnum, @RequestParam("address") String address,
			@RequestParam("tel") String tel, ModelAndView mv) {

		// 未入力チェック
		if (isNull(userName) || isNull(password) || isNull(email) || isNull(name) || isNull(addressnum)
				|| isNull(address) || isNull(tel)) {
			// 未入力ならエラーメッセージを表示
			mv.addObject("message", "未入力の項目があります");

			// 遷移先の指定
			mv.setViewName("users/editInfo");
			return mv;
		}
		// 未入力チェックを通過した場合、 セッションからid取得
		Users userId = (Users) session.getAttribute("id");
		Integer id = userId.getId();

		// 入力された値に更新
		Users editUserAccount = new Users(id, userName, address, email, tel, name, password, addressnum);

		// 入力された値をデータベースに反映させる
		usersRepository.saveAndFlush(editUserAccount);

		// セッション情報を再取得する
		session.setAttribute("userInfo", editUserAccount);
		mv.addObject("userInfo", session.getAttribute("userInfo"));

		// 更新完了メッセージを表示してマイページに遷移
		mv.addObject("message", "登録情報の更新が完了しました");
		mv.setViewName("users/mypage");

		return mv;
	}

	// ログアウト処理
	// <a href="logout">ログアウト</a>
	@RequestMapping("/logout")
	public String logout() {
		return start();
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
