# be-java-cafe

## Step3

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

- [ ] 개인정보 수정 기능 구현하기
    - [ ] 에러페이지 만들기
- [ ] 인프런 강의
- [ ] 기본서 읽기
