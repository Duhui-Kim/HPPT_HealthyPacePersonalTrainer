package com.diet.model.dao;

import java.util.List;

import com.diet.model.dto.Review;

public interface ReviewDao {
	public List<Review> selectByBoardId(int boardId);

	public int update(Review review);
	
	public int insert(Review review);
	
	public int delete(int reviewId);
}
