package com.voidstrike.alanlin.user;

import com.voidstrike.alanlin.dao.UserDao;

public class User {
	protected String userName, userID;
	
	// Constructors
	public User(){
		userName = null;
		userID = null;
	}
	
	public User(String name){
		this.userName = name;
		userID = null;
	}
	
	public User(String name, String userID){
		this.userName = name;
		this.userID = userID;
	}
	
	// Default get&set methods
	public void setUserName(String name){
		this.userName = name;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public void setUserID(String ID){
		this.userID = ID;
	}
	
	public String getUserID(){
		return this.userID;
	}
	
	// Methods
	// Validate
	public boolean valUserbyPSW(String inPSW){
		UserDao tmpDao = new UserDao();
		String realPSW = tmpDao.getUserPSW(userName);
		if (realPSW == null || (realPSW != null && !realPSW.equals(inPSW)))
			return false;
		else{
			// login success will set userID
			setUserID(tmpDao.getUserId(userName));
			return true;
		}
	}
	
	public void register(String email, String psw, String fullName, String phone){
		UserDao tmpDao = new UserDao();
		tmpDao.registUser(email, psw, fullName, phone);
	}
	
	public void postComment(String text, String date, String imgPath){
		UserDao tmpDao = new UserDao();
		tmpDao.post(this.userID, text, date, imgPath);
	}

}
