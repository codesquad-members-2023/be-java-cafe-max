# be-java-cafe
마스터즈 2023 스프링 카페 


### 미션1
1. 스프링 부트 프로젝트 셋업    
   - 저장소 클론 (JDK 11, SpringBoot 2.7.9)

2. 템플릿 기반 MVC 페이지 구성 및 기능 구현  
   - 일반적인 스프링 MVC 는 컴포넌트 기반? 템플릿 기반은 다른가?

3. MVC 구성
   - Model: Entity, DTO, DB 등 데이터의 상태?
   - View: 템플릿 엔진 사용 (Mustache, Thymeleaf 등)
   - Controller: 템플릿 기반으로, Get 요청 시 템플릿 파일로 반환?

4. GET, POST 메서드 동작 방식 이해 및 처리 (스프링 부트는 간략화 됨)  
  - 웹 브라우저에서 GET 요청  
    &rarr; 내장 톰캣 서버에서 요청을 확인하여 해당하는 컨트롤러로  
    &rarr; 컨트롤러에서 모델 데이터를 생성하고 문자열 반환  
    &rarr; 뷰 리졸버가 문자열을 템플릿 파일로 변환
  - 웹 브라우저에서 POST 요청  
    &rarr; 내장 톰캣 서버에서 요청을 확인하여 해당하는 컨트롤러로  
    &rarr; @RequestBody 어노테이션을 사용하여 요청 바디를 객체로 변환  
    &rarr; 컨트롤러에서 모델 데이터를 생성    
    &rarr; 변환기를 통해 객체 변환 (문자 처리: StringHttpMessageConverter, 객체 처리: MappingJackson2HttpMessageConverter)  
    &rarr; HTTP 의 BODY 에 문자 내용 직접 반환  
    &rarr; 해당하는 페이지로 리다이렉트

5. MVC 구조에서 파생된 Controller, Repository, Service
   - Controller: 클라이언트와 맞닿는 부분, MVC 의 컨트롤러
   - Domain(Entity): DB 테이블과 매핑되는 객체
   - Repository: 데이터베이스와 맞닿는 부분, 엔티티 객체를 DB 에 저장하고 관리
   - Service: 핵심 비즈니스 로직 구현  
![img_1.png](img_1.png) 
   이런 느낌으로 구성할 예정

6. 기능 요구 사항
   - 회원가입 &rarr; POST
     - 회원가입 페이지(/user/form/html)에서 회원 가입 폼 표시 (회원가입 Dto)
     - 정보 입력하고 확인 누르면(/users, Post) 회원 목록 조회 페이지로 이동("redirect: /users")
   - 회원 목록 조회 &rarr; GET
     - /users, Get 요청
     - users 라는 이름으로 회원 목록을 /user/list.html 로 전달 (Dto 말하는 듯)
     - 회원 목록 출력(/user/list.html)
   - 회원 프로필 조회 &rarr; GET
     - /users/list.html 에서 개별 회원의 profile 페이지로 이동
     - /users/{userId}, Get 요청
     - userId 에 해당하는 회원 정보를 user 라는 이름으로 전달 (Dto 말하는 듯)
     - 회원 profile 출력

7. 제약 조건
   - Lombok 사용 X
   - 별도의 DB 사용 X
   - 웹 템플릿 엔진은 Mustache 추천 (Thymeleaf 사용 무방)

8. 웹 페이지 디자인
   - 주어진 자료 활용

9. 각 기능에 따른 URL 과 메서드 컨벤션
   - 회원 관련: /users
   - Get, /users: 회원 목록 조회
   - Post, /users: 회원 가입
   - Post, /users/login: 로그인
   - Get, /users/logout: 로그아웃 (Post 요청이 낫다고 하는데..)
   - Get, /users/{userId}: 개별 회원 프로필 조회
   - Put, /users/{userId}: 개별 회원 프로필 수정
   - Delete, /users/{userId}: 개별 회원 삭제

10. 기능 구현
    - 회원 가입 기능 구현
      - 회원 가입 페이지: static/user/form.html (template 로 이동)
      - UserController 추가
        - @Controller 매핑
      - 회원 가입 메서드 추가
        - @PostMapping, URL(/users) 매핑
      - 사용자가 전달한 값을 User 클래스를 생성하여 저장
        - 회원 가입에 사용될 Dto 클래스 추가하여 setter, getter 사용
      - 회원 목록 관리를 위해 JCF 클래스 활용
      - 완료 후, 회원 목록 페이지로 이동 ("redirect: /users")
      
    - 회원 목록 기능 구현
      - 회원 목록 페이지: static/user/list.html (template 로 이동)
      - UserController 그대로 사용
      - 회원 목록 조회 메서드 추가
        - @GetMapping, URL(/users) 매핑
      - Model 을 메서드의 인자로 받은 후, Model 에 사용자 목록을 users 라는 이름으로 전달
      - 회원 목록을 user/list.html 로 전달하기 위해 메서드 반환 값을 "user/list" 로 변경
      - user/list.html 에서 회원 목록 출력
        ```html
          {{#users}}
              // 데이터 조회
          {{/users}}
        ```

    - 회원 프로필 조회 기능 구현
      - 회원 프로필 조회 페이지: static/user/profile.html (template 로 이동)
      - 회원 목록인 user/list.html 에서 회원 닉네임 클릭하면 프로필 조회 페이지로 이동
        - html 에서 페이지 이동: `<a />` 태그 이용
        - `<a href="/users/{{userId}}" />` 와 같이 구현
      - UserController 그대로 사용
      - 회원 프로필 조회 메서드 추가
        - @GetMapping, URL(/users/{userId}) 매핑
      - URL 을 통해 전달한 아이디 값은 @PathVariable 활용해 받아 옴
      - ArrayList 에 저장되어 있는 사용자 중, 사용자 아이디와 일치하는 User 데이터를 찾아서 Model 에 전달
      - user/profile.html 에서는 Controller 에서 전달한 User 데이터를 활용해 사용자 정보 출력

11. HTML 의 중복 제거
    - index.html, /user/form.html, /qna/form.html 코드를 보면 header, navigation bar, footer 부분에 많은 중복 코드가 있다.
    - 중복 코드를 제거한다.

12. 추가 학습 거리
    - template engine &rarr; Mustache
    - html 중복 제거 &rarr; partial 을 이용하여 별도의 template 파일로 분리

### 질문 사항
- 미션1-2)  
템플릿 기반의 MVC 구조는 일반적인 MVC 구조와 다른 건지? 서버 사이드 렌더링 방식을 말하는 것 맞나요?
- 미션1-3)  
Model 이 엔티티를 의미하는 건줄 알았으나, 학습하다 보니 모든 객체 데이터를 의미하는 거 같은데 맞나요?  
Service 나 Repository 같은 계층들은 모델이라고 불러야 하는지? 컴포넌트? 레이어?
- 미션1-5)  
도메인과 엔티티의 차이에 대해 알아보니 상황에 따라 조금씩 의미가 달라지는데, 실제로도 이러한 상황을 반영하여 명칭하나요?

### 정리할 개념들
- 스프링과 스프링 부트, 스프링 MVC 와 스프링 부트 MVC?
- 스프링과 스프링 부트에서 HTTP 메서드 처리 방식
- CSR 과 SSR
- 도메인, 엔티티, 모델의 개념 차이
- @Controller 와 @RestController