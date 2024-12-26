package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class RegisterAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		BoardVo vo = new BoardVo();		
		
		if(request.getParameter("gNo") != "") {
			int replygNo = Integer.parseInt(request.getParameter("gNo"));
			int replyoNo = Integer.parseInt(request.getParameter("oNo"));
			int replyDepth = Integer.parseInt(request.getParameter("depth"));
			new BoardDao().insertReplyBoard(title, content, replygNo, replyoNo, replyDepth, authUser.getId());
		} else {
			vo.setTitle(title);
			vo.setContents(content);
			
			System.out.println(content);
			
			new BoardDao().insertNewBoard(vo, authUser.getId());
		}		
		
		response.sendRedirect("/mysite02/board?pageIdx=1");
	}

}
