package com.diet.model.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "리뷰 정보")
public class Review {
	private int reviewId;
	private String userId;
	private int boardId;
	private String reviewTitle;
	private String reviewContent;
	private String createTime;
	private String updateTime;

	public Review() {
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", userId=" + userId + ", boardId=" + boardId + ", reviewTitle="
				+ reviewTitle + ", reviewContent=" + reviewContent + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
}
