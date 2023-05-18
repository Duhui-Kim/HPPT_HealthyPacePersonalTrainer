package com.diet.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "팔로워 정보")
public class Friend {
	private String userId;
	private String userName;
	private int startKcal;
	private int remainKcal;
	private String userImg;

	public Friend() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStartKcal() {
		return startKcal;
	}

	public void setStartKcal(int startKcal) {
		this.startKcal = startKcal;
	}

	public int getRemainKcal() {
		return remainKcal;
	}

	public void setRemainKcal(int remainKcal) {
		this.remainKcal = remainKcal;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	@Override
	public String toString() {
		return "Friend [userId=" + userId + ", userName=" + userName + ", startKcal=" + startKcal + ", remainKcal="
				+ remainKcal + ", userImg=" + userImg + "]";
	}
}
