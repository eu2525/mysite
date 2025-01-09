package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// ListAction
	@RequestMapping("")
	public String index(@RequestParam(value="pageIdx", required=true, defaultValue="1") int currentPage,
						@RequestParam(value="keyword", required=true, defaultValue="") String keyword,
						Model model) 
	{
		model.addAttribute("map", boardService.getContentsList(currentPage, keyword));
		return "board/list";
	}

	
	//WriteAction
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(@RequestParam(value = "titleId", required = false) Long titleId, Model model) {
        if (titleId != null) {
            model.addAttribute("vo", boardService.getContents(titleId));
        }
		return "board/write";
	}
	
	//RegisterAction
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setUserId(authUser.getId());
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
//	//ModifyAction
//	@RequestMapping(value="/update", method=RequestMethod.GET)
//	public String modify() {
//		return "board/modify";
//	}
//	
//	//UpdateAction
//	@RequestMapping(value="/update", method=RequestMethod.POST)
//	public String modify(BoardVo boardVo) {
//		boardService.UpdateContents(boardVo);
//		return "redirect:/guestbook";
//	}
	
	// ViewAction
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam(value="titleId", required=true, defaultValue="1") Long id, Model model) {
		model.addAttribute("vo", boardService.getContents(id));
		
		return "board/view";
	}
	
	//DeleteAction
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("titleId") Long titleId, Model model) {
		model.addAttribute("titleId", titleId);
		
		return "/board/delete";
	}
	
	
}
