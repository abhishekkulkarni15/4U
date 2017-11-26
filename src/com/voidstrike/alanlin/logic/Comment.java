package com.voidstrike.alanlin.logic;

import net.sf.json.JSONObject;

public class Comment {
	private String userId;
	private String postId;
	private String context;
	private String commentDate;
	
	public Comment(){
		//Constructor for null Comment
		setUserId(null);
		setPostId(null);
		setContext(null);
		setCommentDate(null);
	}
	
	public JSONObject getJSONObject(){
		JSONObject res = new JSONObject();
		res.put("uid", userId);
		res.put("pid", postId);
		res.put("text", context);
		res.put("date", commentDate);
		return res;
	}
	

	// Get & Set Methods
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
}
