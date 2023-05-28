package com.diet.model.dao;

import java.util.List;
import java.util.Map;

import com.diet.model.dto.Board;

public interface BoardDao {
	public List<Board> selectByPage(int limit, int offset);
	
	public List<Board> searchByTitle(Map<String, Object> map);
	
	public int selectAllCount();
	
	public int searchCount(String title);
	
	public int insertBoard(Board board);
	
	public int updateBoard(Board board);
	
	public int deleteBoard(int boardId);
	
	public Board selectOne(int boardId);

	public int updateViewCnt(int boardId, int viewCnt);

	public String selectFileName(int boardId);
	
	public int updateBoardImg(Board board);
}
