# be-java-cafe
마스터즈 2023 스프링 카페 

## 부족한 점
- Spring Mvc는 대충 사용은 할수 있는데 왜 이렇게 사용할 수 있는지 아직 잘 몰라서 공식문서를 공부하면서 더 알아봐야 합니다.
- 커밋 메시지와 네이밍은 아직 힘들어요.
- 에러를 어떻게 던져야 하는지 아직 잘 모르겠어요.

## 아쉬운 점
- 전체 로직 실제 로직 조금 차이가 있어서 조금 아쉽다. 시간이 되면 Spring Security도 한번 적용하고 싶습니다.

## 궁금한 점
- 기능은 잘 돌아가는데 아직 무엇이 좋은 코드인지는 아직 잘 모르겠습니다.

## 계획
- 다음주 DB를 사용하면 Post와 User를 연결하고 tag등 기타 데이터를 추가하면 재밌 있을 것 같습니다.
- 5주동안 추가적인 요구사항을 추가하여 실제 카페처럼 한번 만들려고 합니다.

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

