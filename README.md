# be-java-cafe

> 제공받은 static 파일들을 사용하여 스프링 부트로 기본적인 웹 사이트를 구현하는 것이 목표

## 3월 27일 월요일 학습 계획

> 미션 순서에 따라 구현을 진행하면서 필요한 부분을 찾아 학습한다.

1. 회원 가입 기능 구현 (1H) ✔️  
    회원 가입 페이지에서 POST 요청을 보내면 회원 저장 후 회원 목록 조회 페이지로 이동한다.
2. 회원 가입 기능을 구현하면서 알게된 점 정리 (1H) ✔️ 
   - 쉽게 접근할 수 있는 불필요한 static 파일은 정리하는 것이 좋다.
   - static 파일에 template engine을 적용해 놓고 헤매지 않도록 잘 확인하기. 정적 파일에서 동적인 작업은 불가하다.
3. 회원 목록 조회 기능 구현 (1H) ✔️  
    회원 목록 페이지에서 GET 요청을 보내 정보를 받아오고 브라우저에 회원 목록을 출력한다.
4. 회원 목록 조회 기능을 구현하면서 알게된 점 정리 (1H) ✔️
    - userId의 getter가 getId로 되어있으니 정보를 가져오지 못하는 오류가 발생했다. getUserId로 수정하니 정상적으로 작동한다. 필드명과 동일한지 항상 확인하자.
    - 반복문 사용 시 `변수명 + Stat`를 사용하여 상태를 가져올 수 있다. 0부터 시작하는 인덱스 값인 `index`, 1부터 시작하는 `count` 등 몇 가지 기능을 제공한다.
5. 알고리즘 문제 풀기 (1H) ✔️
6. CS 공부하기 (1H)
7. Spring 강의 듣기 (2H) ✔️

### URL

1. 회원 가입
   - `GET` /users/join
   - `POST` /users/join
2. 회원 목록 조회
   - `GET` /users
3. 회원 프로필 정보 보기
   - `GET` /users/{userId}

## 3월 28일 화요일 학습 계획

> 기본을 학습하여 작동 원리를 이해한다.

어제 못한 계획을 우선순위에 두고 먼저 진행할 것 운동도 잊지 말자!

1. CS 공부하기 (2H) ✔️
2. Spring 강의 듣기 (4H) ✔️
3. 알고리즘 문제 풀기 (1H) ✔️
4. CS study (1H) ✔️

## 3월 29일 수요일 학습 계획

> 미션 순서에 따라 구현을 진행하면서 필요한 부분을 찾아 학습한다.

1. 회원 프로필 조회 기능 구현 (1H) ✔️  
    회원 목록 페이지에서 회원 이름을 클릭하면 해당하는 회원의 프로필 페이지로 이동한다.
2. 회원 프로필 조회 기능을 구현하면서 알게된 점 정리 (1H) ✔️
   - `@PathVariable`를 통해 요청 파라미터를 받아서 사용할 수 있다.
3. HTML의 중복 제거하기 (2H) ✔️
   - Thymeleaf의 `Layout`을 사용하여 공통된 부분을 관리할 수 있다.
4. 알고리즘 문제 풀기 (1H) ✔️
5. Spring 강의 듣기 (2H) ✔️

## 3월 30일 목요일 학습 계획

> 미션 순서에 따라 구현을 진행하면서 필요한 부분을 찾아 학습한다.

1. 글 작성 기능 구현 (1H) ✔️   
   `ArticleController`를 추가한다.
   - [WebMVCConfigurer](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html#addCorsMappings-org.springframework.web.servlet.config.annotation.CorsRegistry-)  
   Spring MVC의 `DispatcherServlet`은 전달받은 설정 파일을 바탕으로 스프링 컨테이너를 생성한다.  
   설정 클래스에는 HandlerMapping의 bean과 HandlerAdapter의 bean이 등록되어 있어야 한다.  
   `@EnableWebMvc`은 **RequestMappingHandlerMapping** 클래스와 **RequestMappingHandlerAdapter** 클래스를 포함한다.  
   `@EnableWebMvc`을 사용하면 `@Controller`를 붙인 컨트롤러를 위한 설정을 생성한다.  
   이 마저도 SpringBoot에서는 `@SpringBootApplication`이 갖고 있는 `@EnableAutoConfiguration`을 통해 자동화하고 있다.
   스프링에서는 `@Enable~`로 적용되는 인프라 bean에 대해 추가적인 설정을 할 수 있도록 `~Configurer`로 끝나는 인터페이스를 제공하고 있다.  
   이를 구현한 클래스를 만들어 bean으로 등록하면 `@Enable~` 전용 어노테이션을 처리하는 단계에서 설정용 bean을 활용해 인프라 bean의 설정을 마무리한다.  
   `@EnableWebMvc`의 빈 설정자는 `WebMvcConfigurer`이며, 이를 구현한 클래스를 만들고 `@Configuration`을 붙여 bean으로 등록하면 된다.
   - [redirect](https://ko.wikipedia.org/wiki/URL_%EB%A6%AC%EB%8B%A4%EC%9D%B4%EB%A0%89%EC%85%98)
2. 글 목록 조회 기능 구현 (1H) ✔️  
    글 목록은 메인 페이지로 사용한다.
3. 게시글 상세보기 기능 구현 (1H) ✔️  
    글 목록에서 글의 제목을 클릭하면 해당하는 글의 상세 페이지로 이동한다.
4. 지난 주 만들었던 페이지들을 사용하여 Front 단에서 유효성 검사 추가 (2H) ✔️
5. 알고리즘 문제 풀기 (1H) ✔️
6. Spring 강의 듣기 (2H)

### URL

1. 글 작성
   - `GET` /articles/write
   - `POST` /articles/write
2. 글 목록 조회
   - `GET` /
3. 글 상세 보기
   - `GET` /articles/{id}

## 3월 31일 금요일 학습 계획

> 지금까지 진행한 내용을 정리하여 오후 4시까지 PR을 보낸다.

1. 회원 정보 수정 기능 구현 (2H) ✔️
2. Mission 1단계 + 2단계 PR 작성하기 (1H)
3. Spring 강의 듣기 (3H)

### URL

1. 회원 가입
   - `GET` /users/join
   - `POST` /users/join
2. 회원 목록 조회
   - `GET` /users
3. 회원 프로필 정보 보기
   - `GET` /users/{userId}
4. 회원 정보 수정 **추가**
   - `GET` /users/{userId}/update
   - `POST` /users/{userId}/update

## 주말 학습 계획

1. Algorithm study
2. Spring MVC 강의 1편 학습하기 / 목표: **80%**
3. Lucas의 **Dependency Injection**, **Bean Lifecycle**을 학습하기
4. 변경하지 않은 페이지는 새로 만들어서 마저 대체하기
5. HTML 중복 제거하기
6. CS 공부하기
