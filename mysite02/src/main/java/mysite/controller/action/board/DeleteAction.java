package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int titleId = Integer.parseInt(request.getParameter("titleId"));
		
		new BoardDao().deleteByTitleId(titleId);
		
		response.sendRedirect(request.getContextPath() + "/board?pageIdx=1");
	}

}
