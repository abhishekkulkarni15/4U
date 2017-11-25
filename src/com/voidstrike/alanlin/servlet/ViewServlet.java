package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.StringBuilder;
import java.util.Iterator;

import com.voidstrike.alanlin.logic.Post;
import com.voidstrike.alanlin.logic.User;
import com.voidstrike.alanlin.dbmgr.DBMgr;

/**
 * Servlet implementation class ViewServlet
 */
@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// Parameter initialize
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("id");
		String userName = (String) session.getAttribute("email");
		String otherUserEmail = (String) request.getParameter("otherUser");
		String json;
		if (userID == null && otherUserEmail == null){ // Error Case
			json = "{\"flag\":false,\"msg\":\"user doesn't login\"}";
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
	
		User currentUser;
		DBMgr tmpMgr = new DBMgr();
		if (otherUserEmail != null)
			currentUser = tmpMgr.getUser(otherUserEmail);
		else{
			currentUser = tmpMgr.getUser(userName);
		}
		
		// Build response JSON String
		StringBuilder auxSB = new StringBuilder();
		auxSB.append("{\"flag\":true,\"posts\":[");
		Iterator<Post> tmpIter = currentUser.iterator();
		Post tmpPost;
		while(tmpIter.hasNext()){
			tmpPost = tmpIter.next();
			auxSB.append(tmpPost.getJSONObject());
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
		tmpMgr.closeAll();
		return;
	}

}
