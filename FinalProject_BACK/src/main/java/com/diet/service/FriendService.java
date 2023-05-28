package com.diet.service;

import java.util.List;

import com.diet.model.dto.Friend;

public interface FriendService {
	public List<Friend> findIdols(String userId);
	
	public List<Friend> findFans(String userId);
	
	public List<Friend> findByName(String userName, String userId);
	
	public int registFriend(String userId, String friendId);
	
	public int removeFriend(String userId, String friendId);
}
