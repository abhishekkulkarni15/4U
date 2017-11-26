package com.voidstrike.alanlin.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidstrike.alanlin.logic.User;

import net.sf.json.JSONObject;

import com.voidstrike.alanlin.dao.ResourcePath;
import com.voidstrike.alanlin.dao.UserDao;
import com.voidstrike.alanlin.dbmgr.DBMgr;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String email = request.getParameter("reg_email");
		String password = request.getParameter("reg_password");
		String fullName = request.getParameter("reg_fullname");
		String phone = request.getParameter("reg_phone");
		JSONObject json = new JSONObject();
		User currentUser = new User();
		currentUser.setUserName(email);
		currentUser.setUserPSW(password);
		currentUser.setNickName(fullName);
		currentUser.setUserPhone(phone);
		
		PrintWriter out = response.getWriter();
		DBMgr tmp;

		// Handle illegal situations
		if (email == null || email.trim().isEmpty()) {
			json.put("flag", false);
			json.put("msg", "email cannot be empty");
		}
		else if (password == null || password.trim().isEmpty()) {
			json.put("flag", false);
			json.put("msg", "password cannot be empty");
		}
		else if (fullName == null || fullName.trim().isEmpty()) {
			json.put("flag", false);
			json.put("msg", "username cannot be empty");
		}
		else{
			tmp = new DBMgr();
			if(currentUser.register(tmp)){
				json.put("flag", true);
				json.put("msg", "register success");
				UserDao dao = new UserDao();
				File folder = new File(ResourcePath.userDirPath + dao.getUserId(email));
			    boolean successful = folder.mkdir();
			   // response.setHeader("registermsg", json.get("msg").toString());
			    RequestDispatcher rs = request.getRequestDispatcher("login.html");
				rs.forward(request, response);
			}
			else{
				json.put("flag", false);
				json.put("msg", "user already exists");
			}
			tmp.closeAll();
		}
		try{
			out.write(json.toString());
			out.flush();
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
