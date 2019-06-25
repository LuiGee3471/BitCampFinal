package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/home")
	public String manageHome() {
		return "manage/home";
	}
	
	@PostMapping("/createClass")
	public String createString(Model model) {
		System.out.println(model);
		return "";
	}
}
