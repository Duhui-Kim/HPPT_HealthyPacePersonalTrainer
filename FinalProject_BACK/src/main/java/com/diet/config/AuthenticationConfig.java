package com.diet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diet.service.UserService;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {
	
	@Autowired
	private UserService userService;
	
	@Value("${jwt.secret}")
	private String secretKey;
	
    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and() // CORS 설정 활성화
                .authorizeRequests()
                // 유저 로그인과 회원가입은 필터링 X / Swagger도 필터링 안되게 만듦
                
                .antMatchers("/user/join/**", "/user/login", "/user/join", "/user/img/**", "/init",
                		"/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs/**", "/swagger-resources/**"
                		).permitAll()
                .antMatchers(HttpMethod.GET, "/board/img/**", "/mealPlan/img/**", "/youtube/**").permitAll()

                .antMatchers("/**").authenticated() // 그 외 요청에 대해서는 JWT 필수
                .antMatchers("/admin/**").hasAnyRole("ADMIN") // 관리자 권한을 가진 사용자만 접근 (지금은 관리자 기능 따로 없음)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class) // JWT 체크 진행
                .build();
    }	
}
