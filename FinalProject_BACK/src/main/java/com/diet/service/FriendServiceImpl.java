package com.diet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.model.dao.FriendDao;
import com.diet.model.dao.UserDao;
import com.diet.model.dto.Friend;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private UserDao userDao;

	// userId가 팔로잉한 친구 목록 가져오는 method
	@Override
	public List<Friend> findIdols(String userId) {
		List<String> idolIds = friendDao.selectIdols(userId);
		List<Friend> friends = new ArrayList<>();

		for (String id : idolIds) {
			friends.add(friendDao.selectFriend(id));
		}
		return friends;
	}
	
	// userId를 팔로우하는 친구 목록 가져오는 method
	@Override
	public List<Friend> findFans(String userId) {
		List<String> fanIds = friendDao.selectFans(userId);
		List<Friend> friends = new ArrayList<>();

		for (String id : fanIds) {
			friends.add(friendDao.selectFriend(id));
		}
		return friends;
	}

	// 이름으로 친구 검색
	// userName : 검색하려는 이름 / userId : 검색하는 사람
	@Override
	public List<Friend> findByName(String userName, String userId) {
		return friendDao.selectByName(userName, userId);
	}

	// 친구 등록
	@Override
	public int registFriend(String userId, String friendId) {
		return friendDao.insert(userId, friendId);
	}

	// 친구 삭제
	@Override
	public int removeFriend(String userId, String friendId) {
		return friendDao.delete(userId, friendId);
	}
}
