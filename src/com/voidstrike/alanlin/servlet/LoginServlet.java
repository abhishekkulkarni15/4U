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
		
		// Check valStatus
		if (!valStatus){
			String json = "{\"flag\":false,\"msg\":\"Username or password is wrong\"}";
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
			String json = "{\"flag\":true,\"msg\":\"Welcome Back\"}";
			String userId = currentUser.getUserID();
			HttpSession session = request.getSession();
			session.setAttribute("id", userId);
			session.setAttribute("email", username);
			// Write response in JSON
			// Temp Test code
			StringBuilder auxSB = new StringBuilder();
			auxSB.append("{\"flag\":true,\"posts\":[");
			Iterator tmpIter = currentUser.iterator();
			Post tmpPost;
			while(tmpIter.hasNext()){
				tmpPost =(Post) tmpIter.next();
				auxSB.append(tmpPost.getJSONObject());
				if(tmpIter.hasNext())
					auxSB.append(",");
			}
			auxSB.append("],");
			
			tmpIter = currentUser.commentIterator();
			auxSB.append("\"comments\":[");
			Comment tmpComment;
			while(tmpIter.hasNext()){
				tmpComment =(Comment) tmpIter.next();
				auxSB.append(tmpComment.getJSONObject());
				if(tmpIter.hasNext())
					auxSB.append(",");
			}
			auxSB.append("],");
			
			tmpIter = currentUser.acIterator();
			auxSB.append("\"activities\":[");
			Activity tmpActivity;
			while(tmpIter.hasNext()){
				tmpActivity =(Activity) tmpIter.next();
				auxSB.append(tmpActivity.getJSONObject());
				if(tmpIter.hasNext())
					auxSB.append(",");
			}
			auxSB.append("]}");
			
			json = auxSB.toString();
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
