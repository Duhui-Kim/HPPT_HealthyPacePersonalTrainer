package com.diet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diet.model.dao.ReviewDao;
import com.diet.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewDao reviewDao;

	// 게시글에 해당하는 리뷰 목록 가져오기
	@Override
	public List<Review> getByBoardId(int boardId) {
		return reviewDao.selectByBoardId(boardId);
	}

	// Review 등록하기
	@Override
	@Transactional
	public int regist(Review review) {
		return reviewDao.insert(review);
	}

	// Review 수정하기
	@Override
	@Transactional
	public int modify(Review review) {
		return reviewDao.update(review);
	}

	// Review 삭제하기
	@Override
	@Transactional
	public int remove(int reviewId) {
		return reviewDao.delete(reviewId);
	}

}
