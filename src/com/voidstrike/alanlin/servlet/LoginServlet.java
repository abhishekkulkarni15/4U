package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.logic.Activity;
import com.voidstrike.alanlin.logic.Comment;
import com.voidstrike.alanlin.logic.Post;
import com.voidstrike.alanlin.logic.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.voidstrike.alanlin.dbmgr.DBMgr;

@WebServlet("/LoginServlet")
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
		DBMgr dbmgr = new DBMgr();
		User currentUser = dbmgr.getUser(username);
		dbmgr.closeAll();
		
		boolean valStatus = currentUser.validatePSW(password);
		JSONObject json = new JSONObject();
		
		// Check valStatus
		if (!valStatus){
			json.put("flag", false);
			json.put("msg", "Username or password is wrong");
			try{
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
//			json.put("flag", true);
//			json.put("msg", "Welcome Back");
			String userId = currentUser.getUserID();
			HttpSession session = request.getSession();
			session.setAttribute("id", userId);
			session.setAttribute("email", username);
			// Write response in JSON
			// Temp Test code
			json.put("flag", true);
			JSONArray postArray = new JSONArray();
			Iterator tmpIter = currentUser.iterator();
			Post tmpPost;
			while(tmpIter.hasNext()){
				tmpPost =(Post) tmpIter.next();
				postArray.add(tmpPost.getJSONObject());
			}
			
			JSONArray commentArray = new JSONArray();
			tmpIter = currentUser.commentIterator();
			Comment tmpComment;
			while(tmpIter.hasNext()){
				tmpComment =(Comment) tmpIter.next();
				commentArray.add(tmpComment.getJSONObject());
			}
			
			JSONArray activityArray = new JSONArray();
			tmpIter = currentUser.acIterator();
			Activity tmpActivity;
			while(tmpIter.hasNext()){
				tmpActivity =(Activity) tmpIter.next();
				activityArray.add(tmpActivity.getJSONObject());
			}
			
			json.put("posts", postArray);
			json.put("comments", commentArray);
			json.put("activities", activityArray);
			
			try{
				response.getWriter().write(json.toString());
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
