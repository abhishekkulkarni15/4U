package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dao.UserDao;
import com.voidstrike.alanlin.user.User;

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
		response.setContentType("text/html;charset=utf-8");
		String json;
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("id");
		if (userID == null){
			json = "{\"flag\":\"false\",\"msg\":\"user doesn't login\"}";
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
		String context = request.getParameter("text");
		String imgPath = request.getParameter("img");
		if ((context == null || context.trim().isEmpty()) && (imgPath == null || imgPath.trim().isEmpty())){
			json = "{\"flag\":\"false\",\"msg\":\"cannot post without context and image\"}";
			try{
				response.getWriter().print(json);
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
		
		User currentUser = new User("", userID);
		currentUser.postComment(context, currentDate, imgPath);
		
		json = "{\"flag\":\"true\",\"msg\":\"post success\"}";
		try{
			response.getWriter().print(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
