package com.diet.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.diet.model.dto.User;
import com.diet.model.dto.UserImg;
import com.diet.service.FileService;
import com.diet.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;
	
	// Login method
	// 성공 시 header에 JWT + 로그인 유저 정보 반환 / 실패 시 false 반환
	@PostMapping("/login")
	@ApiOperation(value = "로그인 method입니다. 성공 시 Jwt가 Header로 발급됩니다.", notes = "필수 : userId, userPass")
	public ResponseEntity<?> login(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();

		String jwt = userService.login(user);
		
		// null인 경우 로그인 실패
		if(jwt == null) {
			return new ResponseEntity<Boolean> (false, HttpStatus.BAD_REQUEST);
		}
		
		headers.set("Authorization", jwt);
		headers.set("Access-Control-Expose-Headers", "Authorization, Content-type");

		// 개인정보를 제외한 민감하지 않은 정보만 가져옴
		User loginUser = userService.getUnSensitiveData(user.getUserId());
				
		// 로그인 성공 시 로그인 유저 정보 반환
		return new ResponseEntity<User>(loginUser, headers, HttpStatus.OK);
	}

	// Join method
	// 성공 시 true 반환 / 실패 시 false 반환
	@PostMapping("/join")
	@ApiOperation(value = "회원가입 method입니다. 가입 성공 시 true를 반환합니다.", notes = "필수 : userId, userName, userPass, userSex, userWeight, targetWeight")
	public ResponseEntity<?> join(@RequestBody User user) {
		Boolean joined = userService.join(user);

		if (joined) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	// 프로필 이미지 등록
	@PostMapping("/userImg/{userId}")
	@ApiOperation(value = "프로필 이미지를 등록하는 method입니다.", notes = "필수 : userImg")
	public ResponseEntity<?> joinImg(@PathVariable String userId, @RequestParam MultipartFile userImg) throws IOException {
		
		Boolean joinedImg = userService.joinImg(userId, userImg);
		
		if(joinedImg) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	// 프로필 이미지 가져오기
	@GetMapping("/userImg/{fileName}")
	@ApiOperation(value = "유저의 프로필 이미지를 가져오는 method입니다.", notes = "필수 : fileName")
	public ResponseEntity<?> getUserImg(@PathVariable String fileName) {
		UserImg imgdata = fileService.getFileData(fileName);
		
		if(imgdata == null) 
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
			
		byte[] userImg = fileService.download(fileName);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(imgdata.getType())).body(userImg);
	}
	
	// Update method
	// 성공 시 true 반환 / 실패 시 false 반환
	@PutMapping
	@ApiOperation(value = "회원정보 수정 method입니다.", notes = "필수 : userId, userPass, userName, userImg, remainkcal.")
	public ResponseEntity<?> update(@RequestBody User user) {
		Boolean updated = userService.update(user);
		
		if(updated) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	// Delete method
	// 성공 시 true 반환 / 실패 시 false 반환
	@DeleteMapping
	@ApiOperation(value = "회원 삭제 method입니다", notes="필수 : userId, userPass")
	public ResponseEntity<?> delete(@RequestBody User user) {
		
		Boolean deleted = userService.delete(user);
		
		if(deleted) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
}
