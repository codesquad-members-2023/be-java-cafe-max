# be-java-cafe  Step3

### 📌 4 / 10 학습계획

- [x] 쿠키와 세션 알아보기
- [x] 피드백 받은 내용으로 코드 수정하기

### 📌 4 / 11 학습계획

- [x] HttpSession 알아보기
- [x] 로그인 기능 구현하기

##### ✔️ 로그인 기능

- [x] 로그인 정보가 담기는 LoginRequest 클래스 작성
- [x] UserRepository 인터페이스에 동일한 ID가 있는지 확인하는 findUser() 메서드 생성
- [x] JdbcUserRepository 에 findUser() 구현체 작성. 만약 동일한 ID가 없다면 예외처리, 있다면 DB에 있는 ID와 PW를 User에 저장 후 반환.
- [x] JdbcUserRepository에서 반환된 ID와 PW를 UserService에서 LoginRequest에 저장된 PW와 비교, PW 두개가 동일하다면 true, 동일하지 않다면 false를 반환
- [x] UserService에서 반환된 값을 가지고 UserController 에서 true가 반환되었다면, "redirect"로 false가 반환 되었다면 "user/login_faild"로 이동하도록 매핑

##### 고민했던 내용

- ID, PW를 어떻게 둘다 확인 할 지? 쿼리문 안에 AND 조건으로 확인해야할지, 역할을 나눠줘야할지 고민..
- 자꾸 오류가 생기는데 왜 생기는지 이유를 못찾겠다<br>
  ➡️ LoginRequest에서 ID 변수의 이름을 잘못 적음 / spring-boot가 userId를 찾고 있는데 변수명을 id라고 적어서 문제가 됐었음

### 📌 4 / 12 학습계획

- [x] 개인정보 수정 기능 구현하기
    - [x] 에러페이지 만들기
- [x] 기본서 읽기
- [ ] 인프런 강의

##### ✔️ 개인정보 수정 기능

- [x] 개인정보 수정 내용이 담기는 ModifyRequest 생성
- [x] UserRepository 인터페이스에 수정된 개인정보를 DB에 update 시키는 modifyUser() 메서드 생성
- [x] JdbcUserRepository에 modifyUser()구현체 작성. 동일한 ID가 없으면 예외처리, 있다면 DB에 새로운 개인정보를 담은 update 쿼리를 날린다.
- [x] UserService에서 userModify() 메서드 생성. -> UserRepository에 있는 findUser() 메서드를 사용해 이미 저장된 userId가 없다면 예외처리,있다면 ID와 PW를
  담은 객체를 반환.
  반환된 객체를 이용해 기존 DB에 있던 PW와 새로 작성된 PW를 비교해, 동일하다면 새로운 user 인스턴스에 수정된 new PW, name, email, id를 저장 후
  UserRepository에 modifyUser()의 매개변수로 새로운 User 인스턴스 전달 return은 true. PW가 동일하지 않다면 false 반환
- [x] UserController modify() 메서드는 UserService에서 반환된 boolean값을 가지고 true가 반환되었다면 redirect:/user를 , false가 반환되었다면 다시
  user/modify 페이지를 반환.

##### 고민했던 내용

- PutMapping은 GET, POST랑 뭐가 다를까 ? 일단 사용했다
    - hidden으로 전송 해야하는 이유가 있나?
- 항상 닭이 먼저인지 달걀이 먼저인지 헷갈린다. controller에서 repository로 가는지 repository에서 controller로 가는지.. 왔다갔다 하는건가

### 📌 4 / 13 학습계획

- [x] 인프런 강의
- [x] 기본서 읽기
- [x] 게시글 작성하기 기능 구현
- [x] 게시글 수정하기 기능 구현

##### ✔️ 게시글 작성 권한 부여 기능 추가

- [x] PostingRequest의 writer 삭제
- [x] articleService의 articleSave() 메서드 매개변수에 userId를 같이 넘겨서 userId가 null이 아닌걸 확인 후,
  PostingRequest.getArticleEntity() 메서드에 userId를 매개변수로 넘겨서 비어있던 writer에 주입..?
- [x] articleService의 articleSave() 메서드 매개변수 userId가 null이라면 예외처리,
  null이 아니라면articleRepository.articleSave() 메서드 실행
- [x] ArticleController newPosting() 메서드는 session에 저장된 값이 없으면 로그인을 하지 않은 상태이기때문에 로그인 페이지로 이동,
  값이 있으면 로그인이 되었기때문에 게시글 작성 form으로 이동

##### ✔️ 게시글 수정 권한 부여 기능 추가

- [x] 게시글을 수정 할 수 있는 edit_form 생성
- [x] 남의 게시물은 수정 할 수 없는 에러페이지 access_error 생성
- [x] ArticleController editPost() 메서드는 게시글 작성자와 ID가 일치하지 않으면 에러페이지를, 일치하면 게시글 수정 form으로 이동
- [x] ArticleDto getWriter() 메서드 생성 : ArticleController editPost()메서드에서 사용

### 📌 4 / 14 학습계획

- [x] 로그아웃 기능 구현
- [x] 게시글 삭제하기 기능 구현
- [ ] PR 제시간에 맞춰서 보내기
- [ ] 인프런 강의 듣기
- [ ] 빌드 다시하기

##### ✔️ 로그아웃 기능 추가

- [x] UserController logout() 메서드 추가 removeAttribute 사용

##### ✔️ 게시글 삭제 권한 부여 기능 추가

- [x] ArticleController deletePost() 메서드는 게시글 작성자와 로그인 된 ID가 일치하지 않으면 에러페이지를,
  일치하면 UserService.deleteRequest()메서드 매개변수에 게시글 넘버인 id를 넘긴 후 redirect 시키기.
- [x] UserService.deleteRequest()메서드는 JdbcArticleRepository.delete() 메서드 매개변수에 id 값을 넘겨줌
- [x] JdbcArticleRepository.delete() 메서드는 id 값을 매개 변수로 받아 delete 쿼리에 id값을 담아 게시글 삭제

