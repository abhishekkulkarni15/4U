package com.voidstrike.alanlin.dbmgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.voidstrike.alanlin.logic.Activity;
import com.voidstrike.alanlin.logic.Comment;
import com.voidstrike.alanlin.logic.Post;
import com.voidstrike.alanlin.logic.User;

public class DBMgr {
	private DBHelper innerCore;
	
	// Constructor
	public DBMgr(){
		innerCore = new DBHelper();
	}
	
	public User getUser(String userEmail){
		String getUserString = "select * from user where email = ?";
		ResultSet rs = null;
		User res = null;
		// Get basic Information of this User
		try {
			innerCore.pst = innerCore.conn.prepareStatement(getUserString);
			innerCore.pst.setString(1, userEmail);
			rs = innerCore.pst.executeQuery();
			if(!rs.next())
				return null; // User doesn't exist
			else{
				res = new User();
				res.setNickName(rs.getString("Name"));
				res.setUserID(rs.getString("id"));
				res.setUserPSW(rs.getString("Password"));
				res.setUserName(rs.getString("email"));
				res.setUserYear(rs.getString("db_year"));
				res.setUserMonth(rs.getString("db_month"));
				res.setUserDate(rs.getString("db_date"));
				res.setImagePath(rs.getString("Image"));
				res.setUserStatus(rs.getString("status"));
				res.setUserCountry(rs.getString("add_country"));
				res.setUserState(rs.getString("add_state"));
				res.setUserCity(rs.getString("add_city"));
				res.setUserStreet(rs.getString("add_street"));
				res.setUserPhone(rs.getString("Phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Get user's Posts
		ResultSet holderSet = getPosts(res.getUserID());
		ResultSet auxSet = null;
		try {
			while (holderSet.next()) {
				Post tmpPost = new Post();
				tmpPost.setPostId(holderSet.getString("id"));
				tmpPost.setContext(holderSet.getString("text"));
				tmpPost.setImgPath(holderSet.getString("image"));
				tmpPost.setLikenum(holderSet.getInt("likenum"));
				tmpPost.setPostDate(holderSet.getString("time"));
				tmpPost.setSid(holderSet.getString("sid"));
				
				auxSet = getComments(tmpPost);
				while(auxSet.next()){
					Comment tmpComment = new Comment();
					tmpComment.setUserId(auxSet.getString("uid"));
					tmpComment.setPostId(auxSet.getString("pid"));
					tmpComment.setContext(auxSet.getString("text"));
					tmpComment.setCommentDate(auxSet.getString("time"));
					tmpPost.addComment(tmpComment);
				}
				res.addPost(tmpPost);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Get user's Comments
		holderSet = getComments(res.getUserID());
		try {
			while (holderSet.next()) {
				Comment tmpComment = new Comment();
				tmpComment.setUserId(holderSet.getString("uid"));
				tmpComment.setPostId(holderSet.getString("pid"));
				tmpComment.setContext(holderSet.getString("text"));
				tmpComment.setCommentDate(holderSet.getString("time"));
				res.addComment(tmpComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Get user's Activities
		holderSet = getActivitiesByUser(res.getUserID());
		try {
			while (holderSet.next()) {
				Activity tmpAc = new Activity();
				tmpAc.setActivityId(holderSet.getString("aid"));
				tmpAc.setTitle(holderSet.getString("title"));
				tmpAc.setLocation(holderSet.getString("location"));
				tmpAc.setTime(holderSet.getString("time"));
				tmpAc.setTag(holderSet.getString("tag"));
				res.addActivity(tmpAc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public ResultSet getPosts(String userId){
		String sql = "select * from post where uid = ? order by id desc;";
		ResultSet rs = null;
		try {
			// get posts based on userID
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, userId);
			rs = innerCore.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getPosts(User user){
		return getPosts(user.getUserID());
	}
	
	// auxiliary method to get user posts
	private ResultSet getComments(String userId){
		String sql = "select * from comment where uid = ? order by time desc;";
		ResultSet rs = null;
		try {
			// get comments based on userID
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, userId);
			rs = innerCore.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// getComments by user
	public ResultSet getComments(User user){
		return getComments(user.getUserID());
	}
	
	// getComments by post
	public ResultSet getComments(Post post){
		String sql = "select * from comment where pid = ? order by time desc;";
		ResultSet rs = null;
		try {
			// get comments based on userID
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, post.getPostId());
			rs = innerCore.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// get user's Activities by userId
	public ResultSet getActivitiesByUser(String userID){
		String sql = "select uid, aid, title, location, time, tag from a_u, activity where a_u.aid = activity.id and uid = ?;";
		ResultSet rs = null;
		try {
			// get activities by userId
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, userID);
			rs = innerCore.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// return all activities in ResultSet
	public ResultSet getActivityList(){
		String sql = "select * from activity;";
		ResultSet rs = null;
		try {
			// get comments based on userID
			innerCore.setSQL(sql);
			rs = innerCore.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public LinkedList<Activity> getLinkedActivityList(){
		LinkedList<Activity> acList = new LinkedList<>();
		ResultSet rs = getActivityList();
		try {
			while(rs.next()){
				Activity tmpAc = new Activity();
				tmpAc.setActivityId(rs.getString("id"));
				tmpAc.setTitle(rs.getString("title"));
				tmpAc.setLocation(rs.getString("location"));
				tmpAc.setTime(rs.getString("time"));
				tmpAc.setTag(rs.getString("tag"));
				acList.add(tmpAc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acList;
	}
	
	public ResultSet getUserList(){
		String sql = "select * from user;";
		ResultSet rs = null;
		try {
			// get comments based on userID
			innerCore.setSQL(sql);
			rs = innerCore.pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public LinkedList<User> getLinkedUserList(String userID){
		LinkedList<User> userList = new LinkedList<>();
		ResultSet rs = getUserList();
		try {
			while(rs.next()){
				User tmpUser = new User();
				tmpUser.setNickName(rs.getString("Name"));
				tmpUser.setUserID(rs.getString("id"));
				tmpUser.setUserName(rs.getString("email"));
				tmpUser.setUserPhone(rs.getString("Phone"));
				if(!userID.equals(tmpUser.getUserID()))
					userList.add(tmpUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	// Add new user into user table
	public void add(User newUser){
		String sql = "insert into user(Name,Password,email,phone) values (?,?,?,?);";
		try {
			// Register User --> insert User information into User table
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, newUser.getNickName());
			innerCore.pst.setString(2, newUser.getUserPSW());
			innerCore.pst.setString(3, newUser.getUserName());
			innerCore.pst.setString(4, newUser.getUserPhone());
			innerCore.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Add new post -- posted by poster
	public void add(String posterID, Post newPost){
		String sql = "insert into post (uid,text,time,image,likenum) values (?,?,?,?,0);";
		try {
			// Register User --> insert User information into User table
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, posterID);
			innerCore.pst.setString(2, newPost.getContext());
			innerCore.pst.setString(3, newPost.getPostDate());
			innerCore.pst.setString(4, newPost.getImgPath());
			innerCore.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to add friendship between two users
	public void add(User thisUser, User targetUser, String date){
		// Add friendship relation
		String sql = "insert into friends (uid1,uid2,time) values (?,?,?);";
		try {
			// Register User --> insert User information into User table
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, thisUser.getUserID());
			innerCore.pst.setString(2, targetUser.getUserID());
			innerCore.pst.setString(3, date);
			innerCore.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to add new comment into database
	public void add(Comment newComment){
		String sql = "insert into comment (uid,pid,text,time) values (?,?,?,?);";
		try {
			// Add new Comment
			innerCore.setSQL(sql);
			innerCore.pst.setString(1, newComment.getUserId());
			innerCore.pst.setString(2, newComment.getPostId());
			innerCore.pst.setString(3, newComment.getContext());
			innerCore.pst.setString(4, newComment.getCommentDate());
			innerCore.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Add activity & activity relationship
	public void add(User target, Activity ac, Boolean joinType){
		String sql;
		ResultSet rs = null;
		if (joinType){
			// Creator
			sql = "insert into activity (title,location,time,tag) values(?,?,?,?);";
			try {
				innerCore.setSQL(sql);
				innerCore.pst.setString(1, ac.getTitle());
				innerCore.pst.setString(2, ac.getLocation());
				innerCore.pst.setString(3, ac.getTime());
				innerCore.pst.setString(4, ac.getTag());
				innerCore.pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			sql = "select id from activity where title = ? and location = ? and time = ? and tag = ?";
			try {
				innerCore.setSQL(sql);
				innerCore.pst.setString(1, ac.getTitle());
				innerCore.pst.setString(2, ac.getLocation());
				innerCore.pst.setString(3, ac.getTime());
				innerCore.pst.setString(4, ac.getTag());
				rs = innerCore.pst.executeQuery();
				rs.next();
				ac.setActivityId(rs.getString("id"));
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
		
			// Join activity Part
			sql = "insert into a_u (uid, aid) values(?,?);";
			try {
				innerCore.setSQL(sql);
				innerCore.pst.setString(1, target.getUserID());
				innerCore.pst.setString(2, ac.getActivityId());
				innerCore.pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Method to close database connection, must be call after each Servlet
	public void closeAll(){
		innerCore.close();
	}
}
