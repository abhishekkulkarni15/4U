package com.voidstrike.alanlin.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidstrike.alanlin.dao.ResourcePath;
import com.voidstrike.alanlin.dao.UserDao;
import com.voidstrike.alanlin.user.User;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String email = request.getParameter("reg_email");
		String password = request.getParameter("reg_password");
		String fullName = request.getParameter("reg_fullname");
		String phone = request.getParameter("reg_phone");
		String json;
		User currentUser = new User(email);
		PrintWriter out = response.getWriter();

		// Handle illegal situations
		if (email == null || email.trim().isEmpty()) {
			json = "{\"flag\":\"false\",\"msg\":\"email cannot be empty\"}";
		}
		else if (password == null || password.trim().isEmpty()) {
			json = "{\"flag\":\"false\",\"msg\":\"password cannot be empty\"}";
		}
		else if (fullName == null || fullName.trim().isEmpty()) {
			json = "{\"flag\":\"false\",\"msg\":\"username cannot be empty\"}";
		}
		else{
			currentUser.register(email, password, fullName, phone);
			request.setAttribute("msg", "Congratulation: " + fullName
					+ ", Register Success");
			json = "{\"flag\":\"true\",\"msg\":\"register successful\"}";
		}
		try{
			response.getWriter().print(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
