package com.diet.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.diet.service.UserService;
import com.diet.utils.JwtUtil;

import io.jsonwebtoken.Jwts;


public class JwtFilter extends OncePerRequestFilter {

	private String secretKey;
	private UserService userService;
	
	public JwtFilter(UserService userService, String secretKey) {
		this.userService = userService;
		this.secretKey = secretKey;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 인증이 함께 왔는지 체크
		final String authorization = request.getHeader("authorization"); 
		
		// token 없거나 잘못된 토큰이면 Block
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			System.out.println("사용자 인증이 추가되지 않았습니다");
			filterChain.doFilter(request, response);
			return;
		}
		
		// Token 꺼내기
		String token = authorization.split(" ")[1];
				
		// Token Expired 되었는지 여부
		if (JwtUtil.isExpired(token, secretKey)) {
			System.out.println("Token이 만료되었습니다"); 
			filterChain.doFilter(request, response);
			return;
		}

		// userId Token에서 꺼내기
		String userId = JwtUtil.getUserId(token, secretKey);
		
		// userId의 권한을 체크해서 권한을 부여
		String role = "USER";
		
		// 권한 부여
		UsernamePasswordAuthenticationToken authenticationToken 
		= new UsernamePasswordAuthenticationToken(userId, null, Arrays.asList(new SimpleGrantedAuthority(role)));
		
		// Detail 넣어주기
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}
}
