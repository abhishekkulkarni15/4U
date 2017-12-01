package com.voidstrike.alanlin.logic;

import java.util.Iterator;
import java.util.LinkedList;

import com.voidstrike.alanlin.dbmgr.DBMgr;
import com.voidstrike.alanlin.logic.Activity;
import com.voidstrike.alanlin.logic.Comment;
import com.voidstrike.alanlin.logic.Post;

import net.sf.json.JSONObject;

public class User implements Iterable<Post>{
	// Variables part
	private String nickName;
	private String userID;
	private String userPSW;
	private String userName; // userName is the email address;
	private String userYear, userMonth, userDate;
	private String imagePath; // the image path of user profile;
	private String userStatus;
	private String userCountry, userState, userCity, userStreet;
	private String userPhone;
	private LinkedList<Comment> commentList;
	private LinkedList<Post> postList;
	private LinkedList<Activity> acList;
	private LinkedList<User> friendList;
	
	
	// Constructors
	public User()
	{ // All parameters initialized as NULL
		this.setNickName(null);
		this.userID = null;
		this.setUserPSW(null);
		this.setUserName(null);
		this.setUserYear(null); this.setUserMonth(null); this.setUserDate(null);
		this.setImagePath(null);
		this.setUserStatus(null);
		this.setUserCountry(null); this.setUserState(null); this.setUserCity(null); this.setUserStreet(null);
		this.setUserPhone(null);
		commentList = new LinkedList<>();
		postList = new LinkedList<>();
		acList = new LinkedList<>();
		friendList = new LinkedList<>();
	}
	
	public User(String nickName, String userID, String PSW, String email, String year, String month,
			String date, String imgPath, String status, String country, String state, String city, String street, String phone)
	{
		this();
		this.setNickName(nickName);
		this.userID = userID;
		this.setUserPSW(PSW);
		this.setUserName(email);
		this.setUserYear(year); this.setUserMonth(month); this.setUserDate(date);
		this.setImagePath(imgPath);
		this.setUserStatus(status);
		this.setUserCountry(country); this.setUserState(state); this.setUserCity(city); this.setUserStreet(street);
		this.setUserPhone(phone);
	}
	
	// Methods
	public boolean register(DBMgr executor){
		User tmp = executor.getUser(userName);
		if (tmp != null)
			return false;
		executor.add(this);
		return true;
	}
	
	public boolean validatePSW(String inputPSW){
		return inputPSW != null && inputPSW.equals(userPSW);
	}
	
	public JSONObject getJSONObject(){
		JSONObject res = new JSONObject();
		res.put("uid", userID);
		res.put("fName", nickName);
		res.put("email", userName);
		res.put("phone", userPhone);
		return res;
	}
	
	public void addPost(Post newPost){
		postList.add(newPost);
	}
	
	public void addComment(Comment newComment){
		commentList.add(newComment);
	}
	
	public void addActivity(Activity newActivity){
		acList.add(newActivity);
	}
	
	public void post(Post newPost, DBMgr aux){
		aux.add(this.userID, newPost);
	}
	
	public void addFriend(User other, String date, DBMgr aux){
		aux.add(this, other, date);
	}
	
	public void joinActivity(Activity target, DBMgr aux){
		aux.add(this, target, false);
	}
	
	public void createActivity(Activity target, DBMgr aux){
		aux.add(this, target, true);
	}

	// Get & Set methods
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}

	public String getUserPSW() {
		return userPSW;
	}

	public void setUserPSW(String userPSW) {
		this.userPSW = userPSW;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserYear() {
		return userYear;
	}

	public void setUserYear(String userYear) {
		this.userYear = userYear;
	}

	public String getUserMonth() {
		return userMonth;
	}

	public void setUserMonth(String userMonth) {
		this.userMonth = userMonth;
	}

	public String getUserDate() {
		return userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserStreet() {
		return userStreet;
	}

	public void setUserStreet(String userStreet) {
		this.userStreet = userStreet;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	// Iterators
	public Iterator<Post> iterator() {
		return postList.iterator();
	}
	
	public Iterator<Comment> commentIterator(){
		return commentList.iterator();
	}
	
	public Iterator<Activity> acIterator(){
		return acList.iterator();
	}

}
