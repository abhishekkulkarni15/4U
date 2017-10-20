package com.voidstrike.alanlin.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dao.ResourcePath;
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
		Map<Integer,String> imagePath = new HashMap<Integer, String>();
		int i =0 ;
		// Check valStatus
		if (!valStatus){
			String json = "{\"flag\":\"false\",\"msg\":\"Username or password is wrong\"}";
			// Write response in JSON
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
				request.getRequestDispatcher("/login.html").forward(request, response);
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
			File file = new File(ResourcePath.userDirPath.concat(userId).concat("/"));
			File[] listOfFiles = file.listFiles();
			if(listOfFiles!=null){
				for(File f : listOfFiles){
					imagePath.put(i, "Users/".concat(userId).concat("/"+f.getName()));
					i++;
				}
			}
			session.setAttribute("imagesPath", imagePath);
			request.setAttribute("imagesPath", imagePath);
			// Write response in JSON
			request.getRequestDispatcher("/index.jsp").forward(request, response);
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
