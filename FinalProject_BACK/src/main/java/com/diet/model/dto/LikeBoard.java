package com.diet.model.dto;

public class LikeBoard {
	private int likeId;
	private String userId;
	private int boardId;
	
	public LikeBoard() {
	}

	public LikeBoard(int likeId, String userId, int boardId) {
		super();
		this.likeId = likeId;
		this.userId = userId;
		this.boardId = boardId;
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
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

	@Override
	public String toString() {
		return "LikeBoard [likeId=" + likeId + ", userId=" + userId + ", boardId=" + boardId + "]";
	}
}
