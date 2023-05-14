package com.diet.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dto.User;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@PostMapping
	public ResponseEntity<String> writeReview(Authentication authentication) {
		return ResponseEntity.ok().body(authentication.getName() + " SUCCESS");
	}
}
