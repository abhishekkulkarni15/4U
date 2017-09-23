package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dao.UserDao;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// Get necessary information
		String username = request.getParameter("reg_email");
		String password = request.getParameter("reg_password");
		// Get User's real password
		String realPSW = new UserDao().getUserPSW(username);
		
		// PSW check phase 
		if (realPSW == null){
			request.setAttribute("msg", "User doesn't exist");
//			System.out.println("User doesn't exist");
			request.getRequestDispatcher("/login.html").forward(request, response);
		}
		else if (realPSW != null && !realPSW.equals(password)){
			request.setAttribute("msg", "Wrong password!");
			request.getRequestDispatcher("/login.html").forward(request, response);
		}
		else if (realPSW.equals(password)){
			request.setAttribute("msg", username + "Welcome");
			String userId = new UserDao().getUserId(username);
			HttpSession session = request.getSession();
			session.setAttribute("id", userId);
			session.setAttribute("email", username);
			request.getRequestDispatcher("/index.html").forward(request, response);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doPost(request, response);
	}
}
