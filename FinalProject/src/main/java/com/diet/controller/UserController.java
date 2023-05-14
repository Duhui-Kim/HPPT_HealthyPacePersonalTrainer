package com.diet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.User;
import com.diet.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// Login method
	// 성공 시 header에 JWT + 로그인 유저 정보 반환 / 실패 시 false 반환
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();

		String jwt = userService.login(user);
		
		// null인 경우 로그인 실패
		if(jwt == null) {
			return new ResponseEntity<Boolean> (false, HttpStatus.BAD_REQUEST);
		}
		
		// header에 JWT 담기
		headers.set("Authorization", jwt);
		// Vue에서 Authorization이 보이도록 전달
		headers.set("Access-Control-Expose-Headers", "Authorization, Content-type");

		// 로그인 성공 시 로그인 유저 정보 반환
		return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
	}

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody User user) {
		Boolean joined = userService.join(user);

		// 가입 실패했을 경우
		if (!joined) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		// 가입 성공했을 경우
		else {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	}
}
