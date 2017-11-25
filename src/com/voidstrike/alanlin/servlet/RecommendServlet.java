package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dbmgr.DBMgr;
import com.voidstrike.alanlin.logic.Activity;
import com.voidstrike.alanlin.logic.User;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/RecommendServlet")
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String json;
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("email");
		String recommendType = (String) request.getAttribute("type");
		DBMgr auxMgr = new DBMgr();
		User currentUser = auxMgr.getUser(userEmail);
		
		if (recommendType == null){
			// Error workPattern
			json = "{\"flag\":false,\"msg\":\"unsupported operation\"}";
		}
		else if (recommendType.equals("activity")){
			// Get Activity Recommendation
			LinkedList<Activity> acList = auxMgr.getLinkedActivityList();
			Iterator<Activity> tmpIter = acList.iterator();
			StringBuilder auxSB = new StringBuilder();
			auxSB.append("{\"flag\":true,\"activities\":[");
			Activity tmpAc;
			while(tmpIter.hasNext()){
				tmpAc = tmpIter.next();
				auxSB.append(tmpAc.getJSONObject());
				if(tmpIter.hasNext())
					auxSB.append(",");
			}
			auxSB.append("]}");
			json = auxSB.toString();
		}
		else if (recommendType.equals("friend")){
			// Get Friend Recommendation
			LinkedList<User> userList = auxMgr.getLinkedUserList(currentUser.getUserID());
			Iterator<User> tmpIter = userList.iterator();
			StringBuilder auxSB = new StringBuilder();
			auxSB.append("{\"flag\":true,\"friends\":[");
			User tmpUser;
			while(tmpIter.hasNext()){
				tmpUser = tmpIter.next();
				auxSB.append(tmpUser.getJSONObject());
				if(tmpIter.hasNext())
					auxSB.append(",");
			}
			auxSB.append("]}");
			json = auxSB.toString();
		}
		else{
			// Error workPattern
			json = "{\"flag\":false,\"msg\":\"unsupported operation\"}";
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