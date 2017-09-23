package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidstrike.alanlin.dao.UserDao;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String email = request.getParameter("reg_email");
		String password = request.getParameter("reg_password");
		String fullName = request.getParameter("reg_fullname");
		String phone = request.getParameter("reg_phone");
		
		// Handle illegal situations
		if (email == null || email.trim().isEmpty()){
			request.setAttribute("msg", "Email cannot be empty");
			request.getRequestDispatcher("/login.html").forward(request, response);
			return;
		}
		if (password == null || password.trim().isEmpty()){
			request.setAttribute("msg", "Password cannot be empty");
			request.getRequestDispatcher("/login.html").forward(request, response);
			return;
		}
		if (fullName == null || fullName.trim().isEmpty()){
			request.setAttribute("msg", "FullName cannot be empty");
			request.getRequestDispatcher("/login.html").forward(request, response);
			return;
		}
		
		UserDao u = new UserDao();
		u.registUser(email, password, fullName, phone);
		request.setAttribute("msg", "Congradulation: "+ fullName+", Register Success");
		request.getRequestDispatcher("/login.html").forward(request, response);	
	}
}
