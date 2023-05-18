package com.diet.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "유저 정보")
public class User {
	private String userId;
	private String userName;
	private String userPass;
	private int userSex;
	private String exerciseType;
	private int userWeight;
	private int startKcal;
	private int remainKcal;
	private int targetWeight;
	private String userImg;
	private String userUpdatePass;

	public User() {
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

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public int getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(int userWeight) {
		this.userWeight = userWeight;
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

	public int getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(int targetWeight) {
		this.targetWeight = targetWeight;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserUpdatePass() {
		return userUpdatePass;
	}

	public void setUserUpdatePass(String userUpdatePass) {
		this.userUpdatePass = userUpdatePass;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPass=" + userPass + ", userSex=" + userSex
				+ ", exerciseType=" + exerciseType + ", userWeight=" + userWeight + ", startKcal=" + startKcal
				+ ", remainKcal=" + remainKcal + ", targetWeight=" + targetWeight + ", userImg=" + userImg
				+ ", userUpdatePass=" + userUpdatePass + "]";
	}
}
