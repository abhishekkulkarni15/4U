package com.voidstrike.alanlin.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidstrike.alanlin.dao.ResourcePath;
import com.voidstrike.alanlin.dao.UserDao;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String email = request.getParameter("reg_email");
		String password = request.getParameter("reg_password");
		String fullName = request.getParameter("reg_fullname");
		String phone = request.getParameter("reg_phone");
		PrintWriter out = response.getWriter();

		// Handle illegal situations
		if (email == null || email.trim().isEmpty()) {
			request.setAttribute("msg", "Email cannot be empty");
			request.getRequestDispatcher("/login.html").forward(request,
					response);
			return;
		}
		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("msg", "Password cannot be empty");
			request.getRequestDispatcher("/login.html").forward(request,
					response);
			return;
		}
		if (fullName == null || fullName.trim().isEmpty()) {
			request.setAttribute("msg", "FullName cannot be empty");
			request.getRequestDispatcher("/login.html").forward(request,
					response);
			return;
		}

		UserDao u = new UserDao();
		u.registUser(email, password, fullName, phone);
		request.setAttribute("msg", "Congratulation: " + fullName
				+ ", Register Success");
		// Validation needed
		File dir = new File(ResourcePath.userDirPath + u.getUserId(email));
		boolean successful = dir.mkdir();
		if (successful) {
			// creating the directory succeeded
			System.out.println("directory was created successfully");
		} else {
			// creating the directory failed
			System.out.println("failed trying to create the directory");
		}
		//
		response.setContentType("text/html");
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Hi!" + request.getAttribute("msg") + "');");
		out.println("location='login.html';");
		out.println("</script>");
		// request.getRequestDispatcher("/login.html").forward(request,
		// response);
		// response.sendRedirect("login.html");
	}
}
