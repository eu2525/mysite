package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;

public class LogoutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Logout은 Session Map에서의 정보를 삭제해주면 됌.
		HttpSession session = request.getSession();
		if(session != null) {
			//로그아웃 처리!
			session.removeAttribute("authUser");
			// session Map에서 session id를 없애고 새로운 session ID를 생성
			session.invalidate();	
		}
		
		response.sendRedirect(request.getContextPath());
	}

}
