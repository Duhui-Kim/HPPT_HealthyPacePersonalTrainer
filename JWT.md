인증 방식 종류

1. 쿠키 (클라이언트에 저장)
   - 보안에 취약 (요청 시 쿠키의 값을 그대로 보내기 때문)
   - 용량제한으로 많은 정보를 담을 수 없다.
   - 브라우저 간 공유가 불가능하다
   - 사이즈가 커질수록 네트워크에 부하가 심해진다
2. 세션 (서버에 저장)
   - 세션 ID 자체가 탈취당할 수 있다. (해결은 가능)
   - 요청이 많아질 경우 서버에 부하가 심해진다
3. 토큰
   - 웹에는 쿠키나 세션이 있지만 앱에는 없기 때문에 앱에서는 무조건 토큰!!
   - 세션과 달리 Stateless하다.
   - 단점 : 특정 토큰을 강제로 만료시키기 어렵다. 이를 개선하고자 만들어진 것이 refresh Token + access Token 방식



JWT

: Json Web Token의 약자로 유저를 식별하고 인증하기 위한 토큰 기반 인증.

토큰 자체에 사용자 권한 정보가 포함되어 있고, 클라이언트에 저장된다. 

JWT를 사용하면 RESTful과 같은 Stateless한 환경에서 사용자의 데이터를 주고 받을 수 있다.

```
1. 사용자가 ID, PASSWORD로 인증
2. 서버에서 Signed JWT를 생성하여 클라이언트에 돌려줌
3. 클라이언트가 서버에 Data를 추가적으로 요구할 때 JWT를 Http header에 첨부
4. 서버에서 클라이언트로부터 온 JWT를 검증하고 인가된 사용자일 경우 Data 전송
```



Header, Payload, Signature로 구성된다.

```java
Header는 JWT type과 해시 알고리즘 종류가 담겨있다.
Payload는 서버에서 첨부한 사용자 권한과 데이터가 담겨있다.
Signature는 header를 base64url로 암호화한 정보와 payload를 base64url로 암호화한 정보에 secretKey를 더한 String을 
    해시암호한 값을 가진다. 따라서 복호화가 불가능하며, 서버에서 JWT를 받았을 때 입력받은 header와 payload가 한 글자라도 변경	이 되었다면 서명 값이 달라지므로 위변조를 방지할 수 있다

    HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload) + "." + secretKey)
```





1. JWT를 이용한 로그인 절차 구현

   1) ` spring-boot-starter-security`와 ` jjwt(java json web token)` 의존성 주입

      ```xml
          <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security</artifactId>
          </dependency>
          <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
          <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt</artifactId>
              <version>0.9.1</version>
          </dependency>
      
      ```

      

   2) `WebMvcConfig`에서 `SecurityFilterChain` 정의

      `SpringSecurity`가 적용되면 자체적으로 `Authorizaion`이 잘못 들어온 것을 차단시키는데, 나는 jwt로 인가여부를 확인할 것이므로 JwtFilter로 통과시킬 예정

      ```java
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
      	            // 유저 로그인과 회원가입은 필터링 X
                      .antMatchers("/user/login", "/user/join", "/open").permitAll() 
                      .antMatchers(HttpMethod.POST, "/**").authenticated() // 그 외 요청에 대해서는 JWT 필수
                      .antMatchers("/admin/**").hasAnyRole("ADMIN") // 관리자 권한을 가진 사용자만 접근
                      .and()
                      .sessionManagement()
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                      .and()
                      .addFilterBefore(new JwtFilter(userService, secretKey),
                      					UsernamePasswordAuthenticationFilter.class) // JWT 체크 진행
                      .build();
          }	
      }
      ```

   

   3. `JwtFilter` 정의 (Jwt가 유효한지 체크)
   
      ```java
      package com.diet.config;
      
      import java.io.IOException;
      
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
      			System.out.println(authorization);
      			System.out.println("AUTHORIZATION을 잘못 보냈습니다");
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
      
      ```

      
   
   4. `JwtUtil` 생성 (Jwt 생성 및 Parsing해서 정보를 읽어오는 역할)
   
      ```java
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
      
      ```
   
      
   
   5. `UserService`에서 Login 요청 시 JwtUtil을 이용하여 Jwt 생성
   
      ```java
      package com.diet.service;
      
      import org.springframework.beans.factory.annotation.Autowired;
      
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
      
      ```
   
      
   
   6. Jwt를 Header로 전달 시 `Access-Control-Expose-Headers` 항목에 `Authorization`을 넣어야함.
   
      브라우저의 자체 보안 적용으로 `Authorization`이 보이지 않기 때문
   
      ```java
      
      	// Login method
      	// 성공 시 header에 JWT + 로그인 유저 정보 반환 / 실패 시 false 반환
      	@PostMapping("/login")
      	public ResponseEntity<?> login(@RequestBody User user) {
      		HttpHeaders headers = new HttpHeaders();
      
      		String jwt = userService.login(user);
      		
      		// null인 경우 로그인 실패
      		if(jwt == null) {
      			return new ResponseEntity<Boolean> (false, HttpStatus.BAD_REQUEST);
      		}
      		
      		// header에 JWT 담기
      		headers.set("Authorization", jwt);
      		// Vue에서 Authorization이 보이도록 전달
      		headers.set("Access-Control-Expose-Headers", "Authorization, Content-type");
      
      		// 로그인 성공 시 로그인 유저 정보 반환
      		return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
      	}
      ```
   
   7. 