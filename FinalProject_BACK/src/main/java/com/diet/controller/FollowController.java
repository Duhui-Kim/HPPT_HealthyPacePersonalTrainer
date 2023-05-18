package com.diet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.Friend;
import com.diet.service.FriendService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	private FriendService friendService;

	@GetMapping("/{userId}")
	@ApiOperation(value = "{userId}가 팔로잉한 친구 목록 불러오는 method", notes = "팔로잉한 친구가 없을 시 null 반환")
	public ResponseEntity<?> getIdolList(@PathVariable String userId) {
		List<Friend> friendList = friendService.findIdols(userId);

		if(friendList == null || friendList.size() == 0) {
			return new ResponseEntity<String> (HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Friend>>(friendList, HttpStatus.OK);			
		}
	}
	
	@GetMapping("/followed/{userId}")
	@ApiOperation(value = "{userId}를 팔로우하는 친구 목록 불러오는 method", notes = "팔로우하는 친구가 없을 시 null 반환")
	public ResponseEntity<?> getFanList(@PathVariable String userId) {
		List<Friend> friendList = friendService.findFans(userId);

		if(friendList == null || friendList.size() == 0) {
			return new ResponseEntity<String> (HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Friend>>(friendList, HttpStatus.OK);			
		}
	}
	
	@GetMapping("/info/{userName}")
	@ApiOperation(value = "{userName}을 포함하는 친구의 정보를 가져오는 method", notes = "해당하는 친구가 없을 시 null 반환")
	public ResponseEntity<?> getFriendByName(@PathVariable String userName) {
		List<Friend> friendList = friendService.findByName(userName);

		if(friendList == null || friendList.size() == 0) {
			return new ResponseEntity<String> (HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Friend>>(friendList, HttpStatus.OK);			
		}
	}

	@PostMapping
	@ApiOperation(value = "팔로잉하는 method", notes = "필수 : userId, friendId")
	public ResponseEntity<?> makeFriend(@RequestParam String userId, @RequestParam String friendId) {
		int made = friendService.registFriend(userId, friendId);

		if (made > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping
	@ApiOperation(value = "팔로잉 취소하는 method", notes = "필수 : userId, friendId")
	public ResponseEntity<?> removeFriend(@RequestParam String userId, @RequestParam String friendId) {
		int removed = friendService.removeFriend(userId, friendId);

		if (removed > 0) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> followException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String> ("FollowingError", HttpStatus.CONFLICT);
	}
}
