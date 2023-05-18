package com.diet.controller;

import javax.websocket.server.PathParam;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.LikeBoard;
import com.diet.service.LikeBoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/likeBoard")
public class LikeBoardController {
	
	@Autowired
	private LikeBoardService likeBoardService;
	
	@GetMapping("/{userId}/{boardId}")
	@ApiOperation(value = "{userId}가 {boardId}를 좋아요 눌렀는지 체크하는 method", notes = "0이면 누르지 않았고, 1 이상이면 눌렀음")
	public ResponseEntity<?> getLikeId(@PathVariable String userId, @PathVariable int boardId) {
		int likeId = likeBoardService.getLikeId(new LikeBoard(0, userId, boardId));
		
		return new ResponseEntity<Integer> (likeId, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "게시글 좋아요 누르는 method", notes = "userId와 boardId를 입력받아야 합니다")
	public ResponseEntity<?> likeBoard(@RequestBody LikeBoard likeBoard) {
		int like = likeBoardService.likeBoard(likeBoard);
		
		if(like > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping
	@ApiOperation(value = "게시글 좋아요 취소하는 method", notes = "userId와 boardId를 입력받아야 합니다")
	public ResponseEntity<?> dislikeBoard(@RequestBody LikeBoard likeBoard) {
		int dislike = likeBoardService.dislikeBoard(likeBoard);
		
		if(dislike > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> likeBoardException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String> ("LikeBoardError", HttpStatus.CONFLICT);
	}
}
