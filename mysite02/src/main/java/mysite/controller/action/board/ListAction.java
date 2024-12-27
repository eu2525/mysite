package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class ListAction implements Action {
	private final int BOARDPERPAGE = 5;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = Optional.ofNullable(request.getParameter("keyword")).orElse("");
		int currentPage = Integer.parseInt(Optional.ofNullable(request.getParameter("pageIdx")).orElse("1"));
		int boardCount = new BoardDao().getBoardCount(keyword);
		int totalPage = (int) Math.ceil((double) boardCount / BOARDPERPAGE);
        int prevPage = (((currentPage - 1) / BOARDPERPAGE) * 5) + 1;
        int endPage = Math.min((prevPage + 4), totalPage);
        int boardStartIdx = boardCount - (currentPage - 1) * BOARDPERPAGE;
        
        request.setAttribute("keyword", keyword);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("prevPage", prevPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("boardStartIdx", boardStartIdx);
		
		List<BoardVo> list = new BoardDao().findByPage(keyword, currentPage, 5);
		request.setAttribute("list", list);
				
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if (authUser != null){
			request.setAttribute("userId", authUser.getId());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}
}
