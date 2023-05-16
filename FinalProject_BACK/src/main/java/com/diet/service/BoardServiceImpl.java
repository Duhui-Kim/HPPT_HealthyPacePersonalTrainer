package com.diet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diet.model.dao.BoardDao;
import com.diet.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {

	// 10개씩 반환
	private final int LIMIT = 10;
	
	@Autowired
	private BoardDao boardDao;
	
	// current Page에 표시할 게시글 10개를 가져오는 method
	@Override
	public Map<String, Object> getBoard(int current) {
		Map<String, Object> map = new HashMap<>();
		
		int maxSize = boardDao.selectAllCount();
		int maxPage = maxSize / LIMIT;

		if(maxSize % LIMIT != 0) 
			maxPage++;
		
		// 최대 페이지 이상의 번호를 입력할 경우 null 반환
		if(maxPage < current) return null;
				
		int OFFSET = (current-1) * LIMIT;
		
		List<Board> boardList = boardDao.selectByPage(LIMIT, OFFSET);
		
		map.put("maxPage", maxPage);
		map.put("boardlist", boardList);
		
		return map;
	}

	// 게시글 등록
	@Override
	@Transactional
	public int registBoard(Board board) {
		return boardDao.insertBoard(board);
	}

	// 게시글 수정
	@Override
	@Transactional
	public int modifyBoard(Board board) {
		return boardDao.updateBoard(board);
	}

	// 게시글 삭제
	@Override
	@Transactional
	public int removeBoard(int boardId) {
		return boardDao.deleteBoard(boardId);
	}

	// 조회수 증가
	@Override
	@Transactional
	public int increaseViewCnt(int boardId) {
		Board board = boardDao.selectOne(boardId);
		int viewCnt = board.getViewCnt() + 1;
		
		return 	boardDao.updateViewCnt(boardId, viewCnt);
	}

	// 게시글 하나 가져오기
	// addCnt가 1일 경우 조회수도 증가, 0일 경우 조회수 증가하지 않음
	@Override
	public Board getBoardById(int boardId) {
		Board board = boardDao.selectOne(boardId);
		return board;
	}

}