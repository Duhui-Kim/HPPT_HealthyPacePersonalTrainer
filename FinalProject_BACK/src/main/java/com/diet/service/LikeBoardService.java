package com.diet.service;

import com.diet.model.dto.LikeBoard;

public interface LikeBoardService {
	public int getLikeId(LikeBoard likeBoard);
	
	public int likeBoard(LikeBoard likeBoard);
	
	public int dislikeBoard(LikeBoard likeBoard);

	public int getCnt(int boardId);
}
