package com.diet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.diet.model.dao.UserDao;
import com.diet.model.dto.User;
import com.diet.utils.JwtUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Value("${jwt.secret}") // 보안 상 노출되면 안되므로 properties에 저장
	private String secretKey;
	
	private BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
	
	// 만료시간 30분으로 설정
	private Long expiredMs = 1000 * 60 * 30l;

	@Override
	public boolean join(User user) {
		User checkUser = userDao.selectById(user.getUserId());
		
		// ID가 일치하는 유저가 있다면 false 반환
		if(!(checkUser == null)) {
			return false;
		}
		// 비밀번호 암호화
		String secretPass = cryptPasswordEncoder.encode(user.getUserPass());
		user.setUserPass(secretPass);
		
		userDao.insert(user);
		
		return true;
	}

	@Override
	public String login(User user) {
		User loginUser = userDao.selectById(user.getUserId());
		
		// DB에 해당 유저가 없거나 비밀번호가 틀릴 경우 null 반환
		if(loginUser == null || !cryptPasswordEncoder.matches(user.getUserPass(), loginUser.getUserPass())) {
			return null;
		}
		// 있을 경우 Token 발행
		return JwtUtil.createJwt(user.getUserId(), secretKey, expiredMs);
	}

	// 민감하지 않은 정보 가져오기
	@Override
	public User getUser(String id) {
		return userDao.selectByIdNonSensitive(id);
	}	
}
