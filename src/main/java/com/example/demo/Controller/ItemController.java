package com.example.demo.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Items;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.UsersRepository;

@Controller
public class ItemController {
	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ItemRepository itemRepository;

	// 初期画面処理
	@RequestMapping("/itemView")
	public ModelAndView itemView(ModelAndView mv) {
		// 商品情報をすべて取得
		List<Items> itemList = itemRepository.findAll();
		
		mv.addObject("items", itemList);
		//昇順に並び替える
		mv.addObject("items", itemRepository.findALLByOrderByIdAsc());

		// 商品画像を表示
		String imgurl = "/image.png";
		mv.addObject("coffee_vietnum.png", imgurl);

		// 遷移先を指定
		mv.setViewName("shopping/itemView");
		return mv;
	}

	/**
	 * 商品の検索ボタンを押下時の処理
	 * 
	 * @param mv
	 * @param searchWord
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(ModelAndView mv, @RequestParam("searchWord") String searchWord) {
		List<Items> itemList = itemRepository.findAllByNameContaining(searchWord);
		mv.addObject("items", itemList);
		if(itemList.size() > 0) {
			mv.addObject("message","商品の検索が完了しました");
			mv.setViewName("shopping/itemView");
			return mv;
		}
		mv.addObject("message","該当する商品はありませんでした");
		mv.setViewName("shopping/itemView");
		return mv;
	}

	// 商品詳細ページを表示
	// <a th:href="|/item/${item.id}/detail|">
	@RequestMapping("/item/{id}/detail")
	public ModelAndView viewItemDetail(@PathVariable("id") int id, ModelAndView mv) {
		// 商品情報をすべて取得
//		List<Items> itemList = itemRepository.findAll();
		mv.addObject("items", itemRepository.findById(id).get());
		mv.setViewName("shopping/itemDetail");
		return mv;
	}

}
