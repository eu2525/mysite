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

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect("/mysite02/board?pageIdx=1");
		}
		else {
			if(request.getParameter("titleId") != null) {
				int titleId = Integer.parseInt(request.getParameter("titleId"));
				BoardVo vo = new BoardDao().findById(titleId);
				request.setAttribute("vo", vo);
				System.out.println(vo);
			}
		
			request.setAttribute("authUser", authUser);
	
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
			rd.forward(request, response);
		}
	}

}
