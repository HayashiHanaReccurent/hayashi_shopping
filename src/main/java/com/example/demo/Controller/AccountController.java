package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Repository.UsersRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	/**
	 * 初期画面設定(ログイン画面の表示)
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView login(ModelAndView mv) {
		// セッション情報の削除
		session.invalidate();
		mv.setViewName("loginLogout/index");
		return mv;
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
			@RequestParam("address") String address, @RequestParam("tel") String tel, ModelAndView mv) {
		// 未入力チェック
		if (isNull(userName) || isNull(password) || isNull(email) || isNull(email) || isNull(name) || isNull(address)
				|| isNull(tel)) {
			// 未入力ならエラーメッセージを表示、登録画面を再度表示
			mv.addObject("message", "未入力の項目があります。");
			mv.setViewName("loginLogout/signup");
			return mv;
		}
//		// チェックを通過したらデータベースに反映させる
//		Users newUserAccount = new Users(userName, password, email, name, address, tel);
//		usersRepository.saveAndFlush(newUserAccount);
//		
		// 新規登録完了 画面に遷移
		mv.setViewName("loginLogout/completeSignup");

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

}
