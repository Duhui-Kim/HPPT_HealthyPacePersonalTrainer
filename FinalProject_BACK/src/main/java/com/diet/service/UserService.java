package com.diet.service;

import com.diet.model.dto.User;

public interface UserService {
	// 회원가입
	public boolean join(User user);
	// 로그인
	public String login(User user);
	// 유저 정보 주기
	public User getUnSensitiveData(String id);
	// 유저 정보 수정
	public Boolean update(User user);
	// 유저 삭제
	public Boolean delete(User user);
}
