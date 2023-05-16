package com.diet.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dto.User;
import com.diet.model.dto.UserImg;

public interface UserService {
	// 회원가입
	public boolean join(User user);
	// 로그인
	public String login(User user);
	// 유저 정보 주기
	public User getUnSensitiveData(String id);
	// 유저 정보 수정
	public boolean update(User user);
	// 유저 삭제
	public boolean delete(User user);
	// 프로필 이미지 등록
	public boolean joinImg(String userId, MultipartFile userImg) throws IOException;
	
}
