package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import mysite.vo.UserVo;

@Controller
public class MainController {

	@RequestMapping({"/", "/main"})
	public String index(Model model, HttpServletRequest request) {		
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "안녕!";
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("김귀릭");
		vo.setEmail("rnlfl@gmail.com");
		return vo;
	}
}