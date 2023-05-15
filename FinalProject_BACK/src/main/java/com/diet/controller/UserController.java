package com.diet.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.User;
import com.diet.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// Login method
	// 성공 시 header에 JWT + 로그인 유저 정보 반환 / 실패 시 false 반환
	@PostMapping("/login")
	@ApiOperation(value = "로그인 method입니다. 성공 시 Jwt가 Header로 발급됩니다.", notes = "DB에 해당 유저가 존재해야합니다.")
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
	@ApiOperation(value = "회원가입 method입니다. 가입 성공 시 true를 반환합니다.", notes = "id가 중복되지 않아야합니다.")
	public ResponseEntity<?> join(@RequestBody User user) {
		Boolean joined = userService.join(user);

		if (joined) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	// Update method
	// 성공 시 true 반환 / 실패 시 false 반환
	@PutMapping
	@ApiOperation(value = "회원정보 수정 method입니다.", notes = "기존 비밀번호가 일치할 경우에만 수정됩니다. 비밀번호 / 이름 / img / remainkcal만 수정됩니다.")
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
	@ApiOperation(value = "회원 삭제 method입니다", notes="비밀번호가 일치할 경우 해당 유저를 삭제합니다.")
	public ResponseEntity<?> delete(@RequestBody User user) {
		
		Boolean deleted = userService.delete(user);
		
		if(deleted) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
}
