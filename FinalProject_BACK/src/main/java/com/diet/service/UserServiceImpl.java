package com.diet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	// 토큰 만료시간 30분으로 설정
	private Long expiredMs = 1000 * 60 * 30l;

	// 회원가입 method
	@Override
	@Transactional
	public boolean join(User user) {
		User checkUser = userDao.selectById(user.getUserId());
		
		// ID가 일치하는 유저가 있다면 false 반환
		if(!(checkUser == null)) {
			return false;
		}
		// 비밀번호 암호화
		String secretPass = cryptPasswordEncoder.encode(user.getUserPass());
		user.setUserPass(secretPass);
		
		// 현재 몸무게와 목표 몸무게를 이용하여 몇 kcal를 감량해야하는지 계산
		int remainKcal = (user.getUserWeight() - user.getTargetWeight()) * 7000;
		
		user.setStartKcal(remainKcal);
		user.setRemainKcal(remainKcal);
		
		userDao.insert(user);
		
		return true;
	}

	// 로그인 method
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

	// 유저 정보 가져오기
	@Override
	public User getUnSensitiveData(String id) {
		return userDao.selectByIdUnSensitive(id);
	}

	// 유저 정보 업데이트
	@Override
	@Transactional
	public Boolean update(User user) {
		User updateUser = userDao.selectById(user.getUserId());
		
		// 비밀번호가 같을 경우에 유저 정보 수정
		if(cryptPasswordEncoder.matches(user.getUserPass(), updateUser.getUserPass())) {
			// 변경할 비밀번호가 입력되었다면 비밀번호 바꿔주기
			if(user.getUserUpdatePass() != null || !user.getUserUpdatePass().trim().equals("")) {
				user.setUserPass(cryptPasswordEncoder.encode(user.getUserUpdatePass()));
			}

			userDao.update(user);
			return true;
		}
		return false;
	}

	// 유저 삭제
	@Override
	@Transactional
	public Boolean delete(User user) {
		User deleteUser = userDao.selectById(user.getUserId());
		
		// 유저의 비밀번호가 일치할 경우에만 삭제
		if(cryptPasswordEncoder.matches(user.getUserPass(), deleteUser.getUserPass())) {
			userDao.delete(user.getUserId());
			return true;
		}
		return false;
	}
}
