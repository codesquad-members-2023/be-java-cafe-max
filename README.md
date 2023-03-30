# be-java-cafe
마스터즈 2023 스프링 카페 


# 1단계

---

<br>

- 요구 사항
  - 회원가입 폼으로부터 입력받은 데이터로 회원가입
  - 회원 목록 페이지에서 전체 회원목록을 조회

- 회원
  - 아이디
    - 영어 대소문자 최소 1문자 이상 + 숫자로만 가능하다.
    - 5자리 이상 20자리 이하의 길이로 가능하다.
    - 아이디는 중복될 수 없다.
  - 비밀번호
    - 영어, 숫자, 특수문자로 가능하다.
    - 8자리 이상 20자리 이하의 길이로 가능하다.
  - 이름
    - 2 글자 이상 4글자 이하로 가능하다.
  - 이메일
    - 이메일 형식으로 가능하다.
    - 이메일은 중복될 수 없다.
- User Class
  - id
    - Long
    - Key
  - userId
    - String
  - password
    - String
  - name
    - String
  - email
    - String
  - createdAt
    - LocalDateTime
  - updatedAt
    - LocalDateTime
- URL
  - 회원 조회
    - [GET] users/id
  - 회원 리스트 조회
    - [GET] users
  - 회원 가입
    - [POST] users

<br>

## 구현 순서

---

[x] 회원 가입  
[x] 회원 조회  
[x] 회원 리스트 조회  
[x] 컨트롤러, 템플릿 엔진 작성  
[ ] 회원 가입 Dto 검증, 예외 처리  
[ ] 프로필 조회

<br>

## 학습 리스트 및 생각

---

**생각**
  
- 템플릿 엔진을 만들기 전에 데이터가 의도한 대로 생성 되는지 확인을 해야 되는데 RestController를 만들까 하다가 테스트 코드를 작성하기로 했다.  
- 회원 가입의 경우 UserController에서 @Valid 애노테이션을 사용해서 UserJoinDto에서 검증을 하려고 하는데 이 부분을 어떻게 테스트 할까 고민이 되었다.  
- 생각해보니 UserService에서 Dto 테스트를 하는 건 아닌 것 같아서 나중에 Controller에서 테스트를 해야겠다.  
- ConcurrentHashMap을 더 학습해봐야 알겠지만 ConcurentHashMap과는 별개로 키를 Long 값으로 사용하고 메서드에서 ++ 연산자를 사용해서 id 값을 늘리고 있어서 동시성 문제가 생길거 같은데 
테스트를 해보니 역시 제대로 생성이 안 되고 있었다. ~~해결을 하긴 했는데 이런거 신경쓸 때가 아닌거 같은데..ㅠㅠ~~ 구현 너무 신경쓰지말고 궁금한거 파고 들면서 공부하자  
- URL을 설계할때 GET, POST 같은 메서드로 행위를 표현하려면 회원 가입은 POST + "/users"로 하는게 좋은데 회원 가입 폼을 주기 위한 GetMapping을 "/users"로 만들면 회원 리스트를 받는 "/users"와 url이 겹치게 된다. 
그러면 회원 가입 url을 "/users/join"으로 하게 되는데 애매한 것 같다.


**학습 리스트**
  
[ ] ConcurrentHashMap에 대해 학습 및 정리  
[ ] DispatcherServlet 학습 및 정리