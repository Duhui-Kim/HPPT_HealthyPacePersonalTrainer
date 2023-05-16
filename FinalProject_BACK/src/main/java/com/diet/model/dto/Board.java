package com.diet.model.dto;

import java.util.Date;

public class Board {
	private int boardId;
	
	private String userId;
	
	private String boardContent;
	
	private Date createTime;
	
	private Date updateTime;
	
	private int viewCnt;
	
	private String boardImg;

	public Board() {
	}

	public Board(int boardId, String userId, String boardContent, Date createTime, Date updateTime, int viewCnt,
			String boardImg) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.boardContent = boardContent;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.viewCnt = viewCnt;
		this.boardImg = boardImg;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getBoardImg() {
		return boardImg;
	}

	public void setBoardImg(String boardImg) {
		this.boardImg = boardImg;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", userId=" + userId + ", boardContent=" + boardContent + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", viewCnt=" + viewCnt + ", boardImg=" + boardImg + "]";
	}
	
}
