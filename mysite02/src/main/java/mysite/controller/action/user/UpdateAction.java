package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		System.out.println(name);
		String email = request.getParameter("email");
		System.out.println(email);
		String password = request.getParameter("password");
		System.out.println(password);
		String gender = request.getParameter("gender");
		System.out.println(gender);
		
		UserVo vo = new UserVo();
		vo.setName(name);
		vo.setEmail(email);
		vo.setPassword(password);
		vo.setGender(gender);

		UserDao dao = new UserDao();
		dao.update(vo);
	
		//session update
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		System.out.println("Before Session: " + authUser);
		if (authUser != null) {
		    authUser.setName(name);
		    authUser.setEmail(email);
		    authUser.setGender(gender);
		    session.setAttribute("authUser", authUser);
		}
		System.out.println("After Session: " + authUser);
	    response.sendRedirect("/mysite02/user?a=updateform");
	}

}
