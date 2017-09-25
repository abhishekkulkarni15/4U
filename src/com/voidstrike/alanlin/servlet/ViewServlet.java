package com.voidstrike.alanlin.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.voidstrike.alanlin.dao.ResourcePath;
import com.voidstrike.alanlin.dao.UserDao;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("id");
		ResultSet rs = null;
		UserDao u = new UserDao();
		rs = u.viewPost(userID);
		String text, imgPath;
		int postCount = 0;

		// changed 10 to get row count from post table
		try {
			while (postCount < UserDao.getPostCount(userID) && rs.next()) {
				text = rs.getString("text");
				request.setAttribute("t" + postCount, text);
				imgPath = rs.getString("image");
				request.setAttribute("i" + postCount, imgPath);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File folder = new File(ResourcePath.userDirPath + userID);
		File[] listOfFiles = folder.listFiles();
		String[] filePaths = new String[listOfFiles.length];
		int i = 0;
		for (File file : listOfFiles) {
			System.out.println(file.getAbsolutePath());
			filePaths[i] = file.getAbsolutePath();
			i++;
		}
//		request.getSession().setAttribute("imageAbsPaths", filePaths);
		request.setAttribute("imageAbsPaths", filePaths);
		request.setAttribute("postnum", postCount);
		request.getRequestDispatcher("/index.html").forward(request, response);
	}

}
