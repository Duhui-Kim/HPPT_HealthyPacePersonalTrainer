package com.diet.utils;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	// 유효시간 검사
	public static boolean isExpired(String token, String secretKey) {
		return Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token).getBody().getExpiration().before(new Date());
	}
	
	// JWT에서 UserId Parsing
	public static String getUserId(String token, String secretKey) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
				.getBody().get("userId", String.class);
	}
	
	// JWT 생성
	public static String createJwt(String userId, String secretKey, Long expiredMs) {		
		// userId만 저장해도 괜찮을 것 같다
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		
		return Jwts.builder()
				.setClaims(claims) // 내용
				.setIssuedAt(new Date(System.currentTimeMillis())) // 만든시간
				.setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료시간
				.signWith(SignatureAlgorithm.HS256, secretKey) // 사인
				.compact();
	}
	
}
