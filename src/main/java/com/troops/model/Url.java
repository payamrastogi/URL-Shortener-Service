package com.troops.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Url 
{
	@JsonIgnore
	private int id;
	private String url;
	@JsonIgnore
	private String fCountCode;
	@JsonIgnore
	private Timestamp createdOn;
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
	public String getfCountCode() {
		return fCountCode;
	}
	public void setfCountCode(String fCountCode) {
		this.fCountCode = fCountCode;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
}
