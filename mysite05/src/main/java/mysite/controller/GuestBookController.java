package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.GuestBookService;
import mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	private GuestBookService guestBookService;
	
	public GuestBookController(GuestBookService guestBookService) {
		this.guestBookService = guestBookService;
	}
	
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("list", guestBookService.getContentsList());
		return "guestbook/list";
	}
	
	@RequestMapping("/add")
	public String add(GuestBookVo guestbookVo) {
		guestBookService.addContents(guestbookVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		return "/guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(
		@PathVariable("id") Long id,
		@RequestParam(value="password", required=true, defaultValue="") String password) {
		guestBookService.deleteContents(id, password);
		return "redirect:/guestbook";
	}
}