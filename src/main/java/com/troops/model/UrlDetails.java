package com.troops.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UrlDetails 
{
	@JsonIgnore
	private int id;
	private String url;
	@JsonIgnore
	private String fCountCode;
	private long clicks;
	private Timestamp createdOn;
	private Timestamp lastAccessedOn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getClicks() {
		return clicks;
	}
	public void setClicks(long clicks) {
		this.clicks = clicks;
	}
	public String getCreatedOn() {
		return createdOn.toString();
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getLastAccessedOn() {
		return lastAccessedOn.toString();
	}
	public void setLastAccessedOn(Timestamp lastAccessedOn) {
		this.lastAccessedOn = lastAccessedOn;
	}
	public String getfCountCode() {
		return fCountCode;
	}
	public void setfCountCode(String fCountCode) {
		this.fCountCode = fCountCode;
	}
}
