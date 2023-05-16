package com.diet.service;

import java.util.List;
import java.util.Map;

import com.diet.model.dto.Board;

public interface BoardService {
	public Map<String, Object> getBoard(int current);
	
	public Board getBoardById(int boardId);
	
	public int registBoard(Board board);
	
	public int modifyBoard(Board board);
	
	public int removeBoard(int boardId);
	
	public int increaseViewCnt(int boardId);
}
