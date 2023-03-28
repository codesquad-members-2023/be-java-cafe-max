# be-java-cafe
마스터즈 2023 스프링 카페 

## 이번 주 목표
- 하루 1시간 산책하기
- 미션 구현에 급급해하지 말고 하나씩 공부하며 적용하기
- TIL 작성하기
- 백준 4문제 풀기
- Object 9장 읽고 정리
- 스프링 MVC1 강의 듣기
- Java 8 문법 적용해보기

## 2023.03.27(월)
- [x] 1단계 - 회원 가입 기능 구현
  - UserRepository는 추후 db가 변동될 수 있기 때문에 interface로 구현
  - UserRepository는 일단 HashMap으로 구현
  - UserService에서 이미 존재하는 유저 아이디라면 중복 예외 처리
- [x] 1단계 - 회원 목록 조회 기능 구현
- [x] 1시간 산책

### 학습
```
A problem occurred configuring root project 'cafe'.
> Could not resolve all files for configuration ':classpath'.
```
- 스프링부트 3 이상에서는 자바 17이 필요한데 나는 자바 11을 사용해서 일어난 오류
- 스프링부트 버전을 2.7.10을 선택해서 다시 실행했더니 해결    

__Controller import 안됨__    
- 클래스명과 컨트롤러 어노테이션 명이 같으면 생기는 오류
- 클래스명을 바꾸면 정상적으로 @Controller가 import 된다.

__사용자 목록 페이지가 두개?__    
회원가입 기능에서 사용자 추가 완료 후 사용자 목록 페이지(redirect:/users)로 이동한다라고 되어있고,    
회원목록 기능에서 회원 목록 페이지는 static/user/list.html을 사용한다고 되어있다.    
이 페이지들을 나눠야 할 필요성을 못 느껴서 users.html 이라는 페이지 하나만 만들었다.    

__Thymeleaf에서 어떤 요소 onclick 시 이동할 url에 pathvariable 추가하고 싶음__    
찾는중..

### 더 공부할 것
1. ResponseBody, RequestParam, RestController
2. 템플릿 엔진, 머스테치, 타임리프
3. GET, POST

## 2023.03.28(화)
