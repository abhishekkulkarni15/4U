package com.voidstrike.alanlin.dbconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	public static final String url = "jdbc:mysql://localhost:3306/4unew?useSSL=false";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "ly7334195";
	
	public Connection conn = null;
	public PreparedStatement pst = null;
	
	public DBHelper(String sql){
		try{
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);
			pst = conn.prepareStatement(sql);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public DBHelper(){
		try{
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void setSQL(String sql){
		try {
			pst = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try{
			if (this.conn != null)
				this.conn.close();
			if (this.pst != null)
				this.pst.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
