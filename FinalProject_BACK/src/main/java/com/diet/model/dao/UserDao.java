package com.diet.model.dao;

import com.diet.model.dto.User;

public interface UserDao {
	public User selectById(String id);

	public int insert(User user);

	public User selectByName(String name);
	
	public User selectByIdUnSensitive(String id);

	public int update(User user);

	public void delete(String userId);
}
