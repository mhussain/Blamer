package com.mhussain.blamer;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Color;
import android.text.method.DateTimeKeyListener;

public class Build implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	private Statii status;
	
	private String description;
	private Date lastCommitDateTime;

	private String lastCommitBy;
	private String lastCommitId;
	

	private enum Statii {
		Success,
		Failure,
		Building
	};
	
	public Build(String name, String url, String status) {
		this.name = name;
		this.url = url;
		
		if (status.equalsIgnoreCase("success")) {
			this.status = Statii.Success;
		}
		else if(status.equalsIgnoreCase("failure")) {
			this.status = Statii.Failure;
		}
		else {
			this.status = Statii.Building;
		}
		this.description = "This is a build";
		this.lastCommitBy = "Clint Eastwood";
		this.lastCommitId = "987678967896iyugjyghfytr65r567";
		this.lastCommitDateTime = new Date();
	}
		
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getLastCommitDateTime() {
		return lastCommitDateTime;
	}
	
	public void setLastCommitDateTime(Date lastCommitDateTime) {
		this.lastCommitDateTime = lastCommitDateTime;
	}
	
	public String getLastCommitBy() {
		return lastCommitBy;
	}
	
	public void setLastCommitBy(String lastCommitBy) {
		this.lastCommitBy = lastCommitBy;
	}
	
	public String getLastCommitId() {
		return lastCommitId;
	}
	
	public void setLastCommitId(String lastCommitId) {
		this.lastCommitId = lastCommitId;
	}
	
	public boolean isBuilding() {
		return this.status == Statii.Building;
	}
	
	public boolean wasSuccessful() {
		return this.status == Statii.Success;
	}
	
	public boolean failed() {
		return this.status == Statii.Failure;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getBackgroundColor() {
		if (this.status == Statii.Success) {
			return Color.rgb(0,102,51);
		}
		else if(this.status == Statii.Failure) {
			return Color.RED;
		}
		else {
			return Color.BLUE;
		}
	}

}
