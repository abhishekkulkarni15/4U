package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.user.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		// Get necessary information
		String username = request.getParameter("reg_email");
		String password = request.getParameter("reg_password");
		// create User object and execute the validation process
		User currentUser = new User(username);
		boolean valStatus = currentUser.valUserbyPSW(password);
		
		// Check valStatus
		if (!valStatus){
			String json = "{\"flag\":\"false\",\"msg\":\"Username or password is wrong\"}";
			// Write response in JSON
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			String json = "{\"flag\":\"true\",\"msg\":\"Welcome Back\"}";
			String userId = currentUser.getUserID();
			HttpSession session = request.getSession();
			session.setAttribute("id", userId);
			session.setAttribute("email", username);
			// Write response in JSON
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doPost(request, response);
	}
}
