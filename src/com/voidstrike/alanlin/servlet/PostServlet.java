package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dao.UserDao;

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
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("id");
		if (userID == null){
			request.setAttribute("msg", "user doesn't login");
			request.getRequestDispatcher("/login.html").forward(request, response);
			return;
		}
		String context = request.getParameter("text");
		String imgPath = request.getParameter("img");
		if ((context == null || context.trim().isEmpty()) && (imgPath == null && imgPath.trim().isEmpty())){
			request.setAttribute("msg", "Post Failed, Cannot have no context and image");
			request.getRequestDispatcher("/index.html").forward(request, response);	
			return;
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(now);
		
		UserDao u = new UserDao();
		u.post(userID, context, currentDate, imgPath);
		
		request.setAttribute("msg", "Post Success");
		request.getRequestDispatcher("/index.html").forward(request, response);	
	}

}
