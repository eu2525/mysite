//package mysite.controller;
//
//import java.util.Map;
//
//import javax.swing.plaf.basic.BasicFileChooserUI.UpdateAction;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import mysite.controller.action.board.DeleteAction;
//import mysite.controller.action.board.ListAction;
//import mysite.controller.action.board.ModifyAction;
//import mysite.controller.action.board.RegisterAction;
//import mysite.controller.action.board.ViewAction;
//import mysite.controller.action.board.WriteAction;
//import mysite.service.BoardService;
//
//@Controller
//@RequestMapping("/board")
//public class BoardController {
//	private BoardService boardService;
//	
//	public BoardController(BoardService boardService) {
//		this.boardService = boardService;
//	}
//	
//	
//	private Map<String, Action> mapAction = Map.of(
//			"modify", new ModifyAction(),
//			"view", new ViewAction(),
//			"write", new WriteAction(),
//			"register", new RegisterAction(),
//			"update", new UpdateAction(),
//			"delete", new DeleteAction()
//		);
//		
//	@Override
//	protected Action getAction(String actionName) {
//		return mapAction.getOrDefault(actionName, new ListAction());		
//	}
//}
