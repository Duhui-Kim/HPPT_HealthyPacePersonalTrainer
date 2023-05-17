package com.diet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.Review;
import com.diet.service.ReviewService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/{boardId}")
	@ApiOperation(value = "{boardId}에 해당하는 review를 반환하는 method입니다.", notes = "해당하는 리뷰가 없을 시 false를 반환합니다.")
	public ResponseEntity<?> getReviews(@PathVariable int boardId) {
		List<Review> reviews = reviewService.getByBoardId(boardId);
		
		if(reviews == null || reviews.size() <= 0) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<List<Review>> (reviews, HttpStatus.OK);
		}
	}

	@PostMapping
	@ApiOperation(value = "review를 등록하는 method입니다.", notes = "필수 : userId, boardId, writer, reviewContent")
	public ResponseEntity<?> registReview(@RequestBody Review review) {
		int registCnt = reviewService.regist(review);
		
		if(registCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	@ApiOperation(value = "review를 수정하는 method입니다.", notes = "필수 : reviewId, writer, reviewContent")
	public ResponseEntity<?> updateReview(@RequestBody Review review) {
		int updateCnt = reviewService.modify(review);
		
		if(updateCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{reviewId}")
	@ApiOperation(value = "review를 삭제하는 method입니다.", notes = "필수 : reviewId")
	public ResponseEntity<?> deleteReview(@PathVariable int reviewId) {
		int removeCnt = reviewService.remove(reviewId);
		
		if(removeCnt > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
}
