package com.diet.model.dao;

import com.diet.model.dto.LikeBoard;

public interface LikeBoardDao {
	public String selectLike(LikeBoard likeBoard);
	
	public int insertLike(LikeBoard likeBoard);
	
	public int deleteLike(LikeBoard likeBoard);

	public String selectByBoardId(int boardId);
}
