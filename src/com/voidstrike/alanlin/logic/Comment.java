package com.voidstrike.alanlin.logic;

import java.lang.StringBuilder;

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
	
	public String getJSONObject(){
		StringBuilder aux = new StringBuilder();
		aux.append("{");
		aux.append("\"uid\":\"" + userId + "\",");
		aux.append("\"pid\":\"" + postId + "\",");
		aux.append("\"text\":\"" + context + "\",");
		aux.append("\"date\":\"" + commentDate + "\"}");
		return aux.toString();
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
