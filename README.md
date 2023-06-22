# HPPT_HealthyPacePersonalTrainer

## 0. 프로젝트 개요

- 프로젝트명 : HPPT
- 프로젝트컨셉 : 건강한 속도로 다이어트를 할 수 있도록 도와주는 식단관리 웹 서비스
- 개발기간 : 23.05.18 ~ 23.05.26 (9일)
- 팀원 : 김두희, 박승휘
- 사용기술스택 : SpringBoot, MyBatis, Vue.js, Git



## 1. 팀원 정보 및 업무 분담 내역

| 이름   | 역할                               | 설명                                                         |
| ------ | ---------------------------------- | ------------------------------------------------------------ |
| 김두희 | Main - BackEnd<br />Sub - FrontEnd | - DB 설계 및 구축 / Food API Data 가공 및 DB 저장<br />- 게시판, 팔로우, 게시글좋아요, 리뷰, 회원가입 및 로그인 기능 구현<br />- JWT를 이용한 인증 구현<br />- 게시글 Pagination 구현 및 File Upload 구현<br />- FrontEnd : 게시글 목록 및 상세, 마이페이지 View 구현 |
| 박승휘 | Main - FrontEnd<br />Sub - BackEnd | - Main화면 구현 및 음식 등록, 유튜브 목록 View 구현<br />- 로고 디자인 및 Fabicon 등록<br />- 전반적인 디자인 일치화 작업 <br />- 작업 내용 정리 및 발표자료 준비<br />- BackEnd : Youtube API 연동 및 식단 DATA 가공 |



## 2. 프로젝트 결과 

  (용량 관계로 일부 페이지만)

#### 	1) 식단등록

![2 -식단등록-_online-video-cutter com_](https://github.com/Bluuubery/Movie-Picker/assets/118238663/ec41aa3f-8285-498b-82d9-6e5db9440c0f)

#### 	2) 게시글 등록

![5 -게시글등록-_online-video-cutter com_](https://github.com/Bluuubery/Movie-Picker/assets/118238663/1482688b-f616-4db5-9088-6c570dfc9979)



#### 	3) 마이페이지

![6 -마이페이지-편집-_online-video-cutter com__2 (1)](https://github.com/Bluuubery/Movie-Picker/assets/118238663/b5575679-5c2d-44b1-a451-b9d9d1faaf1e)



#### 	4) 메인화면

![7 -메인화면-](https://github.com/Bluuubery/Movie-Picker/assets/118238663/e4ad28ff-d00e-4840-b693-4a6c3866937c)



#### 	5) 로그아웃

![8 -로그아웃-](https://github.com/Bluuubery/Movie-Picker/assets/118238663/71fcb363-ce55-486d-944f-100949a38fd2)



## 3. API

![API](https://github.com/Bluuubery/Movie-Picker/assets/118238663/6acddf70-3d39-4736-a693-ef2c3ff9ccf3)



## 4. ERD

![ERD](https://github.com/Bluuubery/Movie-Picker/assets/118238663/b08d57b2-b705-4225-a1c6-dec28caa06fb)

<br>

## 5. Swagger UI

![다운로드](https://github.com/Duhui-Kim/HPPT_HealthyPacePersonalTrainer/assets/118238663/5312490e-cde6-41c1-bb46-bb3458676567)

<br>

## 6. 느낀점 및 아쉬운점

<느낀점>

1. 협업을 할 때는 계획이 반이다.

   어떤 변수명을 쓸 것이고 어떤 요청을 보내서 어떻게 처리할 것인지 설계 과정에서 구체적으로 얘기하긴 했지만, 그래도 부족한 점이 있었다. 2학기에는 더 많은 사람이 한 팀이 되어서 개발을 하게 될테니 조금 더 설계에 공들여서 개발을 진행해야겠다고 생각했다.

2. 게시글을 올리고 이미지를 업로드하는 등의 과정에서 비동기에 대해 더욱 체감할 수 있었다. 프론트엔드에 대한 이해도가 조금은 높아진 것 같다.



<아쉬운 점>

1. 프로젝트 계획에서 생각했던 TDD를 진행하지 못했다. JUNIT과 POSTMAN을 방학동안 충분히 학습해서 2학기에 적용해봐야겠다.

2. 게시글 및 리뷰 작성 및 업데이트 시 XSS 공격에 취약했는데, FRONT가 생각보다 오래 걸려서 Server단에서 XSS 방지를 위한 Filter를 적용할 시간이 부족했다. 다른 기능들에 욕심을 너무 많이 부렸던 것 같다.

3. 반쪽자리 JWT였다. Refresh Token 기능도 없었고, JWT의 활용을 온전히 하지 못했던 것 같다. 이 부분도 방학동안 스스로에게 주는 숙제가 될 것이다. ( + CI/CD 및 배포도 방학숙제!)

   
