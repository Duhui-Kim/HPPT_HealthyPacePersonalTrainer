package com.diet.service;

import com.diet.model.dto.User;

public interface UserService {
	// 회원가입
	public boolean join(User user);
	// 로그인
	public String login(User user);
	// 유저 정보 주기
	public User getUser(String id);
}
