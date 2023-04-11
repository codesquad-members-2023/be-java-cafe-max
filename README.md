# be-java-cafe
마스터즈 2023 스프링 카페 

## DAY01. 230410 월

- [x] 코드 리뷰 보고 코드 리팩토링


---
## DAY02. 230411 화

- 스프링 카페 3 4단계 - 로그인
    - 로그인 기능 구현
        - [ ] 로그인 상태이면 상단 메뉴에서 “로그아웃”, “개인정보수정” 표시
    - 로그아웃 기능 구현
        - [ ] 현재 상태가 로그인 상태가 아니라면 상단 메뉴에서 “로그인”, “회원가입”이 표시
    - 개인정보 수정 기능 구현
        - [ ] 비밀번호가 일치하는 경우에만 수정 가능
        - [ ] 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
        - [ ] 로그인한 사용자와 수정하는 계정의 id가 같은 경우만 수정
        - [ ] 다른 사용자의 정보를 수정하려는 경우 에러 페이지를 만든 후 에러 메시지를 출력


- 스프링 프레임워크
    - [ ] Dependency Injection
    - [ ] Bean Lifecycle


---
## DAY03. 230412 수

- 스프링 카페 3 5단계 - 게시글 권한부여
  - [ ] 로그인하지 않은 사용자는 게시글의 목록만 볼 수 있다.
  - [ ] 로그인한 사용자만 게시글의 세부내용을 볼 수 있다.
  - [ ] 로그인한 사용자만 게시글을 작성할 수 있다.
  - [ ] 로그인한 사용자는 자신의 글을 수정 및 삭제할 수 있다.
    - 게시글 작성하기
        - [ ] 글작성 화면에서 글쓴이 입력 필드를 삭제
        - [ ] 로그인하지 않은 사용자가 글쓰기 페이지에 접근할 경우 로그인 페이지로 이동
        - [ ] Article의 글쓴이 값은 User의 name 값을 가지는 것으로 구현
    - 게시글 수정하기
        - [ ] 수정하기 폼 과 수정하기 기능은 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능
        - [ ] 상황에 따라 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동하도록 구현
    - 게시글 삭제하기
        - [ ] 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능
        - [ ] 상황에 따라 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동하도록 구현
    - 사용자 정보 수정 기능 구현
        - [ ] 입력된 사용자 정보를 수정 가능
            - [ ] 비밀번호, 이름, 이메일만 수정 가능, 사용자 아이디는 수정 불가
            - [ ] 비밀번호가 일치하는 경우에만 수정 가능


- 스프링 프레임워크
    - [ ] AOP와 인터셉터
    - [ ] 학습자료 - 로깅 라이브러리


---
## DAY04. 230413 목

- [ ] Exception 공부


- 스프링 프레임워크
    - [ ] 학습자료 - DB 및 ORM
    - [ ] 학습자료: 스프링 부트 자동 배포 실습


---
## DAY05. 230414 금

- [ ] Exception 처리


- 네트워크와 HTTP
    - [ ] 학습자료 - 웹, HTTP, MVC
    - [ ] 학습자료 - HTTP


---
## 주말

- [ ] 이번 주 미션 리팩토링
- [ ] 오브젝트 책 chapter 7, 8, 9