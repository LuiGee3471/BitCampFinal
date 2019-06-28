package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoController {
  @GetMapping("/home")
  public String videoHome() {
    return "video/home";
  }

  @GetMapping("/detail")
  public String getDetail(int id, Model model) {
    /*
     * parameter로 받은 아이디 값을 이용,
     * 해당하는 글을 불러와서
     * 페이지에 글을 넘겨준다
     */
    return "video/detail";
  }

  @GetMapping("/write")
  public String getWritePage() {
    return "video/write";
  }
  
  @PostMapping("/write")
  public String writeVideoArticle() {
    /*
     * 글 쓰기 데이터를 받아서
     * 해당 글의 페이지로 넘겨준다.
     */
    return "redirect:/video/detail";
  }
}
