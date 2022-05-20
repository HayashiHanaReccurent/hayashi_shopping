package com.example.demo.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	//初期画面処理
	@RequestMapping("/itemView")
	public ModelAndView itemView(ModelAndView mv) {
		//商品情報をすべて取得
		List <Items> itemList = itemRepository.findAll();
		mv.addObject("items",itemList);
		
		//遷移先を指定
		mv.setViewName("shopping/itemView");
		return mv;
	}
}
