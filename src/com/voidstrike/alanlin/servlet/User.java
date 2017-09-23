package com.voidstrike.alanlin.servlet;
import java.sql.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.YearMonth;

public class User {
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
	public User(int id)
	{
		//String URL = "jdbc:mysql://localhost:3306/library";
		ResultSet result = null;
			
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
	public void login(int id)
	{
		
	}
	public void sign_up(String name,String email )
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL,"root","ly7334195");
	        Statement stmt = conn.createStatement();
	        String sql="";
	        System.out.println(sql);
	        stmt.executeUpdate(sql);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		User test = new User(1);
		System.out.println(test.name);
	}
}
