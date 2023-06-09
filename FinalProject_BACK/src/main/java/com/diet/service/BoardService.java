package com.diet.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dto.Board;

public interface BoardService {
	public Map<String, Object> getBoard(int current);
	
	public Board getBoardById(int boardId);
	
	public Map<String, Object> searchBoardByTitle(String title, int current);
	
	public int registBoard(Board board);
	
	public int modifyBoard(Board board);
	
	public int removeBoard(int boardId);
	
	public int increaseViewCnt(int boardId);

	public boolean registImgFile(int boardId, MultipartFile imgFile);

	public String getFileNameById(int boardId);

}
