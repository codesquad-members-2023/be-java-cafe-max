# be-java-cafe
마스터즈 2023 스프링 카페 

## [Commit Log Guidelines](https://github.com/naver/egjs/wiki/Commit-Log-Guidelines)
- feat: A new feature
- fix: A bug fix
- docs: Documentation only changes
- style: Changes that do not affect the meaning of the code. Such as white-space, formatting, missing semi-colons, etc... It also possible to change JSHint, JSCS rules of the code.
- refactor: A code change that neither fixes a bug nor adds a feature
- test: Adding missing tests. Changing tests.
- demo: Adding missing demos. Changing demos.
- chore: Changes to the build process or tools and libraries such as documentation generation

## 사용 기술
- Spring MVC
- Spring Validation
- Thymeleaf

## URL Convention

| url                    | method | 기능                    |
|------------------------|--------|-----------------------|
| /users/login           | get    | 로그인 페이지 요청            |
| /users/login           | post   | 로그인 요청                |
| /users/join            | get    | 회원 가입 페이지 요청          |
| /users/join            | post   | 회원 가입 요청              |
| /users/{userId}        | get    | 회원 프로필 페이지 요청         |
| /users/{userId}/update | get    | 회원 프로필 설정 페이지 요청      |
| /users/{userId}/update | put    | 회월 프로필 설정 요청          |
| /users                 | get    | 모든 회원 맴버 페이지 요청       |
| /post                  | get    | 게시글 작성 페이지 요청         |
| /post                  | post   | 게시글 추가 요청             |
| /                      | get    | 모든 게스글 표시되는 메인 페이지 요청 |                    
| /post/{postId}         | get    | 게시글의 페이지 요청           |

## 구현 결과
### 로그인 페이지
- 이메일 및 password 형식 확인
    - validation으로 LoginForm에서 확인
- 존재하는 이메일인지 확인 기능
    - repository에서 이메일을 찾고 확인
- password 확인 기능
    - repository에서 찾고 password 비교

![login](https://user-images.githubusercontent.com/99056666/228836810-1df5964b-bb79-4675-b7b0-2e7953123748.gif)

### 회원 가입 페이지
- 이메일,nickname,password 형식 확인
    - validation으로 JoinForm에서 확인
- 중복 이메일인 확인 기능
    - repository에서 이메일을 찾고 확인 
  
![join](https://user-images.githubusercontent.com/99056666/228843592-91a6ebab-da67-458a-8da7-fc97428e26ec.gif)

### 회원 프로필 페이지
- 수정 버튼
    - 회원 수정페이지로 이동 
  
![profile](https://user-images.githubusercontent.com/99056666/228843600-94a0939c-3a06-47ad-b1bc-6ee947a91611.gif)

### 회원 프로필 수정 페이지
- nickname, email, password 형식 확인
    - validation으로 profileSettingForm에서 확인
- password 체크
    - pathId로 조회하여 비교
  
![profileSetting](https://user-images.githubusercontent.com/99056666/228843602-73613089-9669-49cc-9723-c12ddf569bc9.gif)

### 맴버리스트 페이지
- 모든 맴버의 나열한다.
   - Repository에서 모든 User정보를 조회하고 UsersForm에 담아 웹페이지 보낸다.
- 페이제에서 각 User 링크 삽입돼여 있다.

![memberList](https://user-images.githubusercontent.com/99056666/228843598-8d6ee89a-3010-4890-98e4-d3e02a787838.gif)


### 글쓰기 페이지
- 제목,닉네임,본문의 형식을 확인
    - validation으로 PostForm에서 확인

![post](https://user-images.githubusercontent.com/99056666/228844562-f03c4728-fdc1-4245-b2cc-c5e98dbf0f7a.gif)


### 홈 페이지
- post를 나열한다.
  - Repository에서 모든 Post정보를 조회하고 SimpleForm에 담아 웹페이지 보낸다.
- 페이제에서 각 post 링크 삽입돼여 있다.

![main](https://user-images.githubusercontent.com/99056666/228843595-3867aeaa-3dba-4d2e-8e5c-f045a7381ab2.gif)

