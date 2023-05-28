package com.diet.model.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "게시글 정보")
public class Board {
	private int boardId;
	
	private String userId;
	
	private String boardTitle;
	
	private String boardWriter;
	
	private String boardContent;
	
	private String createTime;
	
	private String updateTime;
	
	private int viewCnt;
	
	private String boardImg;

	public Board() {
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

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
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
		return "Board [boardId=" + boardId + ", userId=" + userId + ", boardTitle=" + boardTitle + ", boardWriter="
				+ boardWriter + ", boardContent=" + boardContent + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", viewCnt=" + viewCnt + ", boardImg=" + boardImg + "]";
	}
}
