package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dbmgr.DBMgr;
import com.voidstrike.alanlin.logic.User;

import net.sf.json.JSONObject;

import com.voidstrike.alanlin.logic.Post;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		DBMgr tmp = new DBMgr();
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("email");
		if (userID == null){
			json.put("flag", false);
			json.put("msg", "user doesn;t login");
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
		String context = request.getParameter("text");
		String imgPath = request.getParameter("img");
		
		if ((context == null || context.trim().isEmpty()) && (imgPath == null || imgPath.trim().isEmpty())){
			json.put("flag", false);
			json.put("msg", "cannot post without context and image");
			try{
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
		
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(now);
		
		Post newPost = new Post(context, imgPath, currentDate, null, 0);
		currentUser.post(newPost, tmp);
		
		json.put("flag", true);
		json.put("msg", "post success");
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
