package com.voidstrike.alanlin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.voidstrike.alanlin.dbconnector.DBHelper;

;

public class UserDao {
	public String getUserPSW(String username) {
		ResultSet rs = null;
		String psw = null;
		String sql = "select password from user where email = ?";
		DBHelper currentDb = new DBHelper(sql);

		try {
			currentDb.pst.setString(1, username);
			rs = currentDb.pst.executeQuery();
			if (rs == null) {
				return null;
			}
			if (rs.next()) {
				psw = rs.getString("password");
			} else {
				psw = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (currentDb != null)
					currentDb.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return psw;
	}

	public String getUserId(String username) {
		ResultSet rs = null;
		String id = null;
		String sql = "select id from user where email = ?";
		DBHelper currentDb = new DBHelper(sql);

		try {
			currentDb.pst.setString(1, username);
			rs = currentDb.pst.executeQuery();
			if (rs == null) {
				return null;
			}
			if (rs.next()) {
				id = rs.getString("id");
			} else {
				id = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (currentDb != null)
					currentDb.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public void registUser(String email, String psw, String fullName,
			String phone) {
		String sql = "insert into user(Name,Password,email,phone) values (?,?,?,?);";
		DBHelper currentDb = new DBHelper();
		try {
			// Regist User --> insert User information into User table
			currentDb.setSQL(sql);
			currentDb.pst.setString(1, fullName);
			currentDb.pst.setString(2, psw);
			currentDb.pst.setString(3, email);
			currentDb.pst.setString(4, phone);
			currentDb.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			currentDb.close();
		}
	}

	public void post(String uid, String text, String date, String imgPath) {
		String sql = "insert into post (uid,text,time,image) values (?,?,?,?);";
		DBHelper currentDb = new DBHelper();
		try {
			// Regist User --> insert User information into User table
			currentDb.setSQL(sql);
			currentDb.pst.setString(1, uid);
			currentDb.pst.setString(2, text);
			currentDb.pst.setString(3, date);
			currentDb.pst.setString(4, imgPath);
			currentDb.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			currentDb.close();
		}
	}

	public ResultSet viewPost(String uid) {
		String sql = "select * from post where uid = ? order by id desc;";
		ResultSet rs = null;
		DBHelper currentDb = new DBHelper();
		try {
			// Regist User --> insert User information into User table
			currentDb.setSQL(sql);
			currentDb.pst.setString(1, uid);
			rs = currentDb.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			currentDb.close();
		}

		return rs;
	}

	// public static void main(String[] args){
	// String psw = new UserDao().findUsername("teste3r@gmail.com");
	// System.out.println(psw);
	// }

	public static int getPostCount(String uid) {
		String sql = "select count(*) from post where uid = ?;";

		ResultSet rs = null;
		DBHelper currentDb = new DBHelper();
		try {
			// Regist User --> insert User information into User table
			currentDb.setSQL(sql);
			currentDb.pst.setString(1, uid);
			//System.out.println(sql);
			rs = currentDb.pst.executeQuery();
			// only for one element
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			currentDb.close();
		}
		return 0;
		// return rs;
	}
}
