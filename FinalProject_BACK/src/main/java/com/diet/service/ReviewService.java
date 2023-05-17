package com.diet.service;

import java.util.List;

import com.diet.model.dto.Review;

public interface ReviewService {
	public List<Review> getByBoardId(int boardId);
	
	public int regist(Review review);
	
	public int modify(Review review);
	
	public int remove(int reviewId);
}
