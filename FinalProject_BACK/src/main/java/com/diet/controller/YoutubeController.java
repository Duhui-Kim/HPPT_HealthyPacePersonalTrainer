package com.diet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.YoutubeVideo;
import com.diet.service.YoutubeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/youtube")
public class YoutubeController {

	@Autowired
	private YoutubeService youtubeService;

	@GetMapping
	@ApiOperation(value = "query로 검색한 youtube 영상을 maxResults의 개수만큼 가져오는 method입니다.", notes = "필수 : query, maxResults는 default 10")
	public ResponseEntity<?> searchVideos(@RequestParam String query, @RequestParam(defaultValue = "10") int maxResults) {
		List<YoutubeVideo> videos = youtubeService.searchVideos(query, maxResults);

		if (videos != null && videos.size() > 0) {
			return new ResponseEntity<List<YoutubeVideo>>(videos, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> videoException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("videoError", HttpStatus.CONFLICT);
	}
}