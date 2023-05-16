package com.diet.model.dao;

import com.diet.model.dto.UserImg;

public interface UserImgDao {
	public int insertFile(UserImg userImg);
	
	public UserImg selectFile(String fileName);
}
