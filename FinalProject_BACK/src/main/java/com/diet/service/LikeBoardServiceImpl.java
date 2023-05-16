package com.diet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diet.model.dao.LikeBoardDao;
import com.diet.model.dto.LikeBoard;

@Service
public class LikeBoardServiceImpl implements LikeBoardService {
	
	@Autowired
	private LikeBoardDao likeBoardDao;

	// userId와 boardId가 일치하는 likeId 반환
	@Override
	public int getLikeId(LikeBoard likeBoard) {
		String likeId = likeBoardDao.selectLike(likeBoard);
		
		if(likeId == null) {
			return 0;
		} else {
			return Integer.parseInt(likeId);			
		}
	}
	
	// 좋아요 적용
	@Override
	@Transactional
	public int likeBoard(LikeBoard likeBoard) {
		return likeBoardDao.insertLike(likeBoard);
	}

	// 좋아요 취소
	@Override
	@Transactional
	public int dislikeBoard(LikeBoard likeBoard) {
		return likeBoardDao.deleteLike(likeBoard);
	}
}
