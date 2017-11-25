package com.voidstrike.alanlin.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.lang.StringBuilder;
import com.voidstrike.alanlin.logic.Comment;

public class Post {
	private String postId;
	private String context;
	private String imgPath;
	private String postDate;
	private String sid;
	private int likenum;
	private LinkedList<Comment> commentList;
	
	// Constructors
	public Post(){
		setPostId(null);
		setContext(null);
		setImgPath(null);
		setPostDate(null);
		setSid(null);
		setLikenum(0);
		commentList  = new LinkedList<>();
	}
	public Post(String text, String img, String date){
		this();
		setContext(text);
		setImgPath(img);
		setPostDate(date);
	}
	public Post(String text, String img, String date, String sid, int likenum){
		this(text, img, date);
		this.setSid(sid);
		this.setLikenum(likenum);
	}

	// Methods
	public void addComment(Comment newComment){
		commentList.add(newComment);
	}
	
	public String getJSONObject(){
		StringBuilder aux = new StringBuilder();
		aux.append("{");
		aux.append("\"pid\":\"" + postId + "\",");
		aux.append("\"text\":\"" + context + "\",");
		aux.append("\"img\":\"" + imgPath + "\",");
		aux.append("\"date\":\"" + postDate + "\",");
		aux.append("\"likes\":\"" + likenum + "\",");
		
		aux.append("\"comments\":[");
		Iterator<Comment> auxIter = commentList.iterator();
		Comment tmp;
		while(auxIter.hasNext()){
			tmp = auxIter.next();
			aux.append(tmp.getJSONObject());
			if(auxIter.hasNext())
				aux.append(",");
		}
		aux.append("]}");
		return aux.toString();
	}
	
	// Get & Set methods
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public int getLikenum() {
		return likenum;
	}

	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
}
