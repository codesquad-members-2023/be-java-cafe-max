# 마스터즈 2023 스프링 카페

## 금주 학습 계획

- CS 공부
- 매일 오브젝트 30분 읽기
- 월, 수 코딩 테스트 문제 풀기
- spring mvc 1,2 강의 듣기
- 금주 공부한 내용으로 블로그 글 작성하기
- 1,2 단계 미션 끝내기

## 1단계 미션

### 학습 계획

- thymeleaf
- template layout
- spring mvc
- url convention
- http method

### 구현할 기능 목록

- 루트 경로일 때 홈 화면을 출력한다. ✅
- 회원 목록을 조회한다. ✅
- 이메일, 패스워드,닉네임을 입력 받아서 회원 가입을 한다. ✅
  - 회원 가입 후 회원 목록 조회로 이동한다. ✅
- 회원 프로필을 조회한다. ✅
- 회원 가입된 회원의 프로필을 수정한다. ✅
  - 프로필 수정 후 해당 회원을 프로필 조회로 이동한다. ✅

### URL

| HTTP Method |        URL         |          기능           |
| :---------: | :----------------: | :---------------------: |
|     GET     |         /          | /posts url로 리다이렉트 |
|     GET     |      /members      |     회원 목록 조회      |
|    POST     |      /members      |        회원 가입        |
|     GET     |  /members/sign-up  |      회원 가입 폼       |
|     GET     |   /members/{id}    |    회원 프로필 조회     |
|     PUT     |   /members/{id}    |    회원 프로필 수정     |
|     GET     | /members/{id}/edit |   회원 프로필 수정 폼   |
|     GET     |       /posts       |    게시글 목록 조회     |
|    POST     |       /posts       |       게시글 생성       |
|     GET     |    /posts/{id}     |    게시글 상세 조회     |
|     GET     |    /posts/write    |     게시글 작성 폼      |

## 2단계 미션

### 구현할 기능 목록

- 게시글 목록을 조회한다.✅
- 제목, 내용을 입력 받아서 게시글을 작성한다. ✅
  - 게시글 작성 후 게시글 목록으로 이동한다. ✅
- 게시글을 상세 조회한다. ✅

## 3단계 미션

### 학습 계획

- transaction
- spring mvc
- unit test
- BindingResult

### 구현할 기능 목록

- 회원 저장, 조회, 수정 JDBC를 이용하여 DB에서  데이터를 관리한다.✅
- 게시글 저장, 조회  JDBC를 이용하여 DB에서  데이터를 관리한다.✅
- 게시글 조회시 조회수 증가한다.✅
- 회원 프로필 수정시 기존 비밀번호를 확인한다. ✅
- Controller, Service, Repository에 대하여 테스트 코드를 작성한다.✅
- 배포 스크립트 작성한다.✅
- aws를 이용하여 배포를 한다 .✅

## 4단계 미션

### 학습 계획

- cookie
- session
- interceptor

### 구현할 기능 목록

- 이메일, 패스워드를 입력하여 로그인을 한다. ✅

  - 로그인 상태인 경우 상단 메뉴에 로그아웃, 개인정보 수정이 표시되어야 한다. ✅

- 로그인이 된 상태에서 로그아웃을 한다. ✅

  - 로그인 상태가 아닌 경우에는 로그인, 회원가입이 표시되어야 한다. ✅

## 5단계 미션

### 학습 계획

- argumentResolver

### 구현할 기능 목록

- 로그인한 사용자만 게시글을 작성한다. ✅
- 로그인한 사용자만 게시글을 세부내용을 조회한다. ✅
- 게시글을 수정한다.✅
  - 작성자와 로그인한 회원이 같은 경우에만 가능하다.✅
  - 작성자가 아닌 경우 에러페이지로 이동한다.✅
- 게시글을 삭제한다.✅
  - 작성자와 로그인한 회원이 같은 경우에만 가능하다.✅
  - 작성자가 아닌 경우 에러페이지로 이동한다.✅

### URL

| HTTP Method |        URL         |        기능         |
| :---------: | :----------------: | :-----------------: |
|     GET     |      /members      |   회원 목록 조회    |
|     GET     |   /members/{id}    |     프로필 조회     |
|     PUT     |   /members/{id}    |  회원 프로필 수정   |
|     GET     | /members/{id}/edit | 회원 프로필 수정 폼 |
|     GET     |  /members/sign-up  |     회원가입 폼     |
|    POST     |  /members/sign-up  |      회원 가입      |
|     GET     |  /members/sign-in  |      로그인 폼      |
|    POST     |  /members/sign-in  |       로그인        |
|    POST     | /members/sign-out  |      로그아웃       |
|     GET     |         /          |  게시글 목록 조회   |
|     GET     |       /posts       |  게시글 목록 조회   |
|     GET     |    /posts/{id}     |  게시글 상세 조회   |
|     PUT     |    /posts/{id}     |     게시글 수정     |
|   DELETE    |    /posts/{id}     |     게시글 삭제     |
|     GET     |    /posts/write    |   게시글 작성 폼    |
|     GET     | /posts/{id}/modify |   게시글 수정 폼    |

## ERD

![](https://lh3.googleusercontent.com/pw/AJFCJaX1UoZ5jdJMJwDiDhO9EiosHEKQFmXe7iRDzTr86gQ_o3uTDbf8guc501C8vvIFAThz7pBGQrYaE4dZZWVQP1OVZw7-25k3nmmOcSvGBtgdEsEw_3peBtlGsrtb28ecTIoapRc2YYcz0CAaZyKnPGnF=w714-h507-s-no?authuser=0)

## 6단계 미션

### 구현할 기능 목록

- 로그인한 사용자는 게시글 상세보기 화면에서 댓글들을 볼 수 있다.✅
- 로그인한 사용자만 게시글에 댓글을 추가할 수 있다.✅
- 게시글 삭제시 데이터의 상태를 삭제 상태로 변경한다.✅
  - 댓글이 없는 경우 삭제가 가능하다.✅
  - 게시글 작성자와 댓글 작성자가 다를 경우 삭제는 불가능하다.✅
  - 게시글 작성자와 댓글 작성자가 모두 같은 경우는 삭제가 가능하다.✅
- 댓글 삭제도 데이터의 상태를 삭제 상태로 변경한다.✅
  - 로그인 사용자와 댓글 작성자가 같은 경우에만 댓글을 삭제할 수 있다.✅

### URL

| HTTP Method |                URL                |         기능         |
| :---------: | :-------------------------------: | :------------------: |
|     GET     |             /members              |    회원 목록 조회    |
|     GET     |           /members/{id}           |     프로필 조회      |
|     PUT     |           /members/{id}           |   회원 프로필 수정   |
|     GET     |        /members/{id}/form         | 회원 프로필 수정 폼  |
|     GET     |         /members/sign-up          |     회원가입 폼      |
|    POST     |         /members/sign-up          |      회원 가입       |
|     GET     |         /members/sign-in          |      로그인 폼       |
|    POST     |         /members/sign-in          |        로그인        |
|    POST     |         /members/sign-out         |       로그아웃       |
|     GET     |                 /                 | 홈 화면(게시글 목록) |
|    POST     |              /posts               |     게시글 생성      |
|     GET     |            /posts/{id}            |   게시글 상세 조회   |
|     PUT     |            /posts/{id}            |     게시글 수정      |
|   DELETE    |            /posts/{id}            |     게시글 삭제      |
|     GET     |            /posts/form            |    게시글 작성 폼    |
|     GET     |         /posts/{id}/form          |    게시글 수정 폼    |
|    POST     |   /api/posts/{postId}/comments    |      댓글 작성       |
|   DELETE    | /api/posts/{postId}/comments/{id} |      댓글 삭제       |

### ERD

![](https://lh3.googleusercontent.com/pw/AJFCJaWlkMzu68-V2V-eY0o0fm332TG4h3egUnN-rDhEsJc3m-4cJglqwF-zXC67lAw2D3WKNhooU94-xGJCRMQrNozUY3UoQ2-gfFB_8MC8tk7WJSU7K4w_uybbI0HIh8qNxY5i-e8Y8lvDuOeeCSXXUHhl=w1327-h1071-s-no?authuser=0)

## 7단계 미션

### 학습 계획

- Mysql 설치
- ajax

### 구현할 기능목록

- 댓글 기능들을 AJAX와 RestController로 구현한다.✅
- 데이터베이스는 MySQL.8.0으로 변경한다.✅
