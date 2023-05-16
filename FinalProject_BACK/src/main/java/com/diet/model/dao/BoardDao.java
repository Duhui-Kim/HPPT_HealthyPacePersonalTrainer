package com.diet.model.dao;

import java.util.List;

import com.diet.model.dto.Board;

public interface BoardDao {
	public List<Board> selectByPage(int limit, int offset);
	
	public int selectAllCount();
	
	public int insertBoard(Board board);
	
	public int updateBoard(Board board);
	
	public int deleteBoard(int boardId);
	
	public Board selectOne(int boardId);

	public int updateViewCnt(int boardId, int viewCnt);
}
