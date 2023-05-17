package com.diet.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dto.Board;
import com.diet.model.dto.ImgFile;
import com.diet.service.BoardService;
import com.diet.service.FileService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/{current}")
	@ApiOperation(value = "page번호를 입력하면 해당 페이지의 10개의 게시글을 가져오는 method", notes = "현재 페이지의 게시글(최대 10개)과 총 페이지 수를 반환합니다")
	public ResponseEntity<?> getList(@PathVariable int current) {
		
		Map<String, Object> map = boardService.getBoard(current);
		
		if(map != null) {
			return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);			
		} else {
			return new ResponseEntity<Boolean> (false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	@ApiOperation(value = "board를 등록하는 method", notes = "필수 : userId, boardTitle, boardWriter, boardContent")
	public ResponseEntity<?> regist(@RequestBody Board board) {
		int registCnt = boardService.registBoard(board);
		
		if (registCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	@ApiOperation(value = "board를 수정하는 method", notes = "boardTitle, boardWriter, boardContent, updateTime 수정 가능")
	public ResponseEntity<?> update(@RequestBody Board board) {
		int updateCnt = boardService.modifyBoard(board);
		
		if (updateCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{boardId}")
	@ApiOperation(value = "board를 삭제하는 method", notes = "필수 : boardId")
	public ResponseEntity<?> delete(@PathVariable int boardId) {
		int deleteCnt = boardService.removeBoard(boardId);		
		
		if (deleteCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/detail/{boardId}")
	@ApiOperation(value = "board 상세정보를 가져오는 method", notes = "해당하는 board가 없을 시 false 반환")
	public ResponseEntity<?> getBoard(@PathVariable int boardId) {
		Board board = boardService.getBoardById(boardId);
		
		if (board != null) {			
			return new ResponseEntity<Board>(board, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/detail")
	@ApiOperation(value = "viewCnt 증가시키는 method")
	public ResponseEntity<?> increaseViewCnt(@RequestParam int boardId) {
		int viewCnt = boardService.increaseViewCnt(boardId);
		
		if (viewCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/img/{boardId}")
	@ApiOperation(value = "게시판에 사진 올리는 method", notes = "이미지 파일만 업로드해야한다")
	public ResponseEntity<?> uploadImgFile(@PathVariable int boardId, @RequestParam MultipartFile imgFile) {
		boolean registed = boardService.registImgFile(boardId, imgFile);
		
		if (registed) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/img/{fileName}")
	@ApiOperation(value = "게시판 사진 가져오는 method", notes = "게시글에 사진이 있을 경우에만 요청해야한다")
	public ResponseEntity<?> downloadImgFile(@PathVariable String fileName) {
		ImgFile imgdata = fileService.getFileData(fileName);
		
		if(imgdata == null) 
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		
		byte[] imgFile = fileService.download(fileName);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(imgdata.getType())).body(imgFile);		
	}
}
