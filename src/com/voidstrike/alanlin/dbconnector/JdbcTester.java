package com.voidstrike.alanlin.dbconnector;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.voidstrike.alanlin.dbmgr.DBHelper;

public class JdbcTester {
	
	static String sql = null;
	static DBHelper db1 = null;
	static ResultSet ret = null;
	
	public static void main(String[] args){
		sql = "select * from activity";
		db1 = new DBHelper(sql);
		
		try{
			ret = db1.pst.executeQuery();
			while(ret.next()){
				String aID = ret.getString(1);
				String aName = ret.getString(2);
				System.out.println(aID + "\t" + aName);
			}
			ret.close();
			db1.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

}
