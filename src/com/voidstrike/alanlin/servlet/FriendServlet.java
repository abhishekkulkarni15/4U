package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dbmgr.DBMgr;
import com.voidstrike.alanlin.dbmgr.Singleton;
import com.voidstrike.alanlin.logic.User;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
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
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		Singleton sin = new Singleton();
		DBMgr tmp = sin.getInstance();
		
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("email");
		String otherUserID = (String) request.getParameter("friendName");
		tmp = new DBMgr();
		if (userID == null){
			json.put("flag", false);
			json.put("msg", "user doesn't login");
			try{
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
		User currentUser = tmp.getUser(userID);
		User friendUser = tmp.getUser(otherUserID);

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(now);
		
		currentUser.addFriend(friendUser, currentDate, tmp);
	
		json.put("flag", true);
		json.put("msg", "make friend success");
		try{
			response.getWriter().write(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch(Exception e){
			e.printStackTrace();
		}
		tmp.closeAll();
	}

}

