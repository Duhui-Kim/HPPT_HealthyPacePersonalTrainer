package com.diet.model.dao;

import java.util.List;

import com.diet.model.dto.Friend;

public interface FriendDao {
	public List<String> selectIdols(String userId);

	public List<String> selectFans(String userId);

	public List<Friend> selectByName(String userName, String userId);

	public Friend selectFriend(String userId);

	public int insert(String userId, String friendId);

	public int delete(String userId, String friendId);

}
