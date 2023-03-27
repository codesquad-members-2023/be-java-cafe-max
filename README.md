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
    - [POST] users/
  - 회원 수정
    - [PATCH] users/id