package com.diet.model.dao;

import com.diet.model.dto.User;

public interface UserDao {
	public User selectById(String userId);

	public int insert(User user);

	public User selectByName(String userName, String userId);
	
	public User selectByIdUnSensitive(String userId);

	public int update(User user);

	public void delete(String userId);

	public String selectFileName(String userId);
}
