package com.voidstrike.alanlin.logic;

import java.lang.StringBuilder;

public class Activity {
	private String activityId;
	private String title;
	private String location;
	private String acYear, acMonth, acDate;
	private String time;
	private String tag;
	
	public Activity(){
		setActivityId(null);
		setTitle(null);
		setLocation(null);
		setAcYear(null); setAcMonth(null); setAcDate(null);
		setTime(null);
		setTag(null);
	}
	
	public String getJSONObject(){
		StringBuilder aux = new StringBuilder();
		aux.append("{");
		aux.append("\"acid\":\"" + activityId + "\",");
		aux.append("\"title\":\"" + title + "\",");
		aux.append("\"location\":\"" + location + "\",");
		aux.append("\"date\":\"" + time + "\",");
		aux.append("\"tag\":\"" + tag + "\"}");
		return aux.toString();
	}

	// Get & Set Methods
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAcYear() {
		return acYear;
	}

	public void setAcYear(String acYear) {
		this.acYear = acYear;
	}

	public String getAcMonth() {
		return acMonth;
	}

	public void setAcMonth(String acMonth) {
		this.acMonth = acMonth;
	}

	public String getAcDate() {
		return acDate;
	}

	public void setAcDate(String acDate) {
		this.acDate = acDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}
