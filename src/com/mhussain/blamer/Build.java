package com.mhussain.blamer;

import android.graphics.Color;

public class Build {

	private String name;
	private String url;
	private Statii status;
	
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
