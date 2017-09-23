package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public String name=new String();
	public int User_id;
	public String email=new String();
	Date dob;
	public int year;
	public int month;
	public int day;
	public String pic=new String();
	public int phone;
	enum sex{M,F};
	static String URL = "jdbc:mysql://localhost:3306/4unew?useSSL=false";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
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
		ResultSet result = null;
		int id = 1;
		
		   try
		   {  
		    	Class.forName("com.mysql.jdbc.Driver");
		        Connection conn = DriverManager.getConnection(URL,"root","ly7334195");
		        Statement stmt = conn.createStatement();
		        
		        String sql = "select * from USER where id="+id+";";
		        System.out.println(sql);
		        result=stmt.executeQuery(sql);
		        result.next();
		        //System.out.println(str)
		        	name=result.getString(1);
		        	email=result.getString(4);
		        	year=result.getInt(5);
		        	month=result.getInt(6);
		        	day=result.getInt(7);
		        	pic=result.getString(8);
		        	phone=result.getInt(10);
		        	
		        
		      } catch (Exception e) 
		   		{
		         e.printStackTrace();
		        }
	}
	
}
