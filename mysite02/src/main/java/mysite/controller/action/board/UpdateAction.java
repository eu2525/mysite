package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long titleId = Long.parseLong(request.getParameter("titleId"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		HttpSession session = request.getSession();
		
		BoardVo vo = new BoardVo();
		vo.setId(titleId);
		vo.setTitle(title);
		vo.setContents(content);
		
		new BoardDao().updateBoard(vo);
		
		response.sendRedirect("/mysite02/board?pageIdx=1");
	}

}
