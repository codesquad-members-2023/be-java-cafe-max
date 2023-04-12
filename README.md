# be-java-cafe

마스터즈 2023 스프링 카페

# TODO

## 230411 tue.

- [ ] H2 데이터베이스 의존성 추가 및 연동

## 230407 fri.

### Dion 피드백 반영

- [x] 일관성 있는 url 디자인(/user를 /users로 통일) // PR에 url 추가하기
- [x] UserController#post()
    - [x] @RequestParam 중복 제거
    - [x] 어울리는 메서드 명으로 바꾸기
    - [x] User 인스턴스 생성 부분 제거 // 이후 MVC 패턴 및 계층 분리 학습예정 📌
- [x] joinSuccess() 역할에 맞는 메서드 명 고민해보기

#### 학습 후 반영할 내용

- [ ] User 클래스에서 동일성, 동등성 판단
- [ ] 타임리프를 사용해 Model 객체 사용하지 않는 형태로 시도해보기
- [ ] List 동기 문제 수정하고 정리하기
- [ ] UserRepository 인터페이스에서 찾고자 하는 User가 없을 때 생기는 버그 생각해보기
- [ ] 의미 있는 로그 고민해보기. 로그가 어떻게 동작하는지 살펴보기.

### 추가로 구현할 것들

- [x] 게시글 작성 시 작성 일자와 시간 저장
- [x] 메인로고 클릭 시 "/"로 이동하도록 매핑
- [ ] index.html, detail.html에서 작성자 클릭 시 profile.html 연결
- [ ] 비밀번호 일치 확인 후 alert로 메시지 출력

### 고민한 내용

- 컬렉션 생성자 초기화와 필드 선언

## 230406 thu.

### 게시글 상세보기 기능

- [x] Article 객체에 id 인스턴스 변수 추가
- [x] ArticleController에 상세 페이지 접근 메서드 구현
    - [x] URL은 '/articles/{index}'로 매핑
    - [x] 상세 페이지에 article 인스턴스의 내용 띄워주기

### 회원정보 수정 화면 출력 기능

- [x] 회원 목록(/users)에서 개인정보수정 화면(/users/{id}/form)으로 GET 요청 // 수정할 user 인스턴스 전달
- [x] 개인정보 수정 화면 출력(/user/updateForm.html) // value를 사용해서 컨트롤러에서 전달한 값 출력

### 회원정보 수정 기능

- [x] 사용자 아이디를 제외한 비밀번호, 이름, 이메일만 수정 가능
- [x] 개인정보 수정 화면에서 개인정보 수정(/users/{userId}/update)으로 POST 요청
- [x] 수정된 정보 저장 후 사용자 목록 조회(/users)로 리다이렉트해서 업데이트 된 사용자 정보 띄워주기
    - [x] 수정된 정보는 UserRepository#save() 사용해 업데이트
- [x] 수정 시 비밀번호가 일치하는 경우만 수정 가능
    - [x] 비밀번호 일치 확인 페이지 구현 및 매핑

### 고민한 내용

- 상세 페이지 메서드 구현 중 content가 null로 저장됨
    - 본문의 id태그가 contents였으나 ArticleController에서는 content로 선언해 생긴 문제였던 것 같다.
- 회원정보 수정 시 setter를 사용하지 않도록 하고 시퀀스도 유지하는 방법
- 회원정보 수정 전 비밀번호 일치 확인을 어떻게 처리할까?
    - 비밀번호 일치 확인 페이지를 추가해보자.
    - 기능은 구현했지만 model에 저장한 에러 메세지를 출력하는 방법을 모르겠다.

## 230405 wed.

### 글 목록 조회 기능

- [x] 리다이렉트 된 메인 페이지에서 저장한 article 인스턴스의 정보(articleId, title, writer) 띄워주기

## 230403 mon.

### 글쓰기 기능

- [x] 사용자가 작성한 글을 저장하는 Article 클래스 생성
- [x] Article 인스턴스를 저장, 관리하는 컬렉션 클래스 생성
- [x] 게시글 기능 구현을 담당하는 ArticleController 추가 후 애노테이션 매핑
    - [x] 메인 페이지에서 '질문하기' 버튼을 누르면 'qna/form.html'로 이동하도록 매핑
    - [x] 이동한 form에서 게시글 작성 후 '질문하기' 버튼을 누르면 게시글 작성 요청(POST)
    - [x] 입력된 내용으로 article 객체 생성
    - [x] 생성된 article 객체를 articles에 저장
    - [x] 게시글 저장을 완료하면 메인 페이지로 리다이렉트 // MvcConfig

### 고민한 내용

- user, article 인스턴스를 어떤 디렉토리(패키지)에 둬야 할까?
    - 일단 클래스 명에 해당하는 디렉토리를 만들어 두었다.
- config에서 POST 요청, 리다이렉트를 처리할 수 있을까?
    - WebMVCConfigurer는 HTTP 리퀘스트 중 GET만 처리 가능하다. // controller의 메서드를 config에서 호출해 처리하는 방법은 없을까?
    - 리다이렉트는 처리할 수 있다.
- article 인스턴스를 저장하는 컬렉션으로 어떤 것을 사용할까?
    - user는 ArrayList를 사용했는데 불변을 보장하는 리스트 자료구조에 대해 더 알아봐야겠다. 📌
        - 유저나 아티클이 불변이어야만 하는 이유가 있을까? // `동기화`였다..
        - 이 자료구조를 사용한다고 무조건 불변인건 아니다. 내부 객체의 필드 또한 final 키워드가 붙어 있어야 한다. // `final`은 재할당만 금지한다,,!
    - `Optional`에 대해 알아봐야겠다. 📌
    - 구현이 늦어졌으니 이전에 사용했던 ArrayList를 사용하도록 하자. // 차후 더 고민해보기
- 오전에 config에 대해 잠깐 학습한 후 controller 역할에 혼란이 왔는데, 호눅스의 미션 설명을 듣고 나서 개념이 좀 더 명확해졌다.
- Article 클래스의 contents(본문)은 어떤 타입으로 선언해야 할까?
    - String의 범위는 어느정도였지? 더 긴 문자열을 저장할 수 있는 타입이 있었나? // 스트링빌더, 스트링버퍼를 사용할 수도 있다.
    - 꼭 아티클이 불변성을 띄도록 만들어야 할까?
- 게시글 작성 form 제출 시 405 에러 발생
    - form action 태그에 매핑을 안해줬다..
    - addViewControllers()에서 '/qna/form'을 최우선순위로 매핑해준 걸 모르고 action 태그과 컨트롤러 메서드에서 매핑한 것만 생각했다.. // 다른 방법 있을지 생각해보기

# 기술 키워드

️▶️ [[Spring] WebMVCConfigurer](#spring-webmvcconfigurer)   
️▶️ [[Thymeleaf] Fragment Expressions](#thymeleaf-fragment-expressions)   
️▶️ [[Thymeleaf] onClick 이벤트](#thymeleaf-onclick-이벤트)   
️▶️ [[Spring] Annotation](#spring-annotation)

## [Java] 동기화 & 병렬처리 컬렉션

// '이것이 자바다'에서 찾아보자 📌

### 동기화된 컬렉션

- Vector, HashTable은 동기화된(synchronized) 메서드로 구성되어 멀티스레트 환경에서 안전하지만,
- ArrayList, HashSet, HashMap은 synchronized 메서드로 구성되지 않아 멀티 스레드 환경에서 안전하지 않다.

- 컬렉션을 Thread-safe하게 만드려면 `Collections.synchronizedXxx()` 메서드를 이용하면 된다.
- 매개값으로 컬렉션을 대입하면 동기화된 컬렉션을 리턴한다.

```
  List<String> list = Collections.synchronizedList(new ArrayList<>());
```

### 병렬처리된 컬렉션 (동기화 컬렉션의 한계)

- 동기화된(synchronized) 컬렉션은 스레드 환경에서 안전은 보장하지만, 스레드가 하나의 요소를 처리할 때 잠금이 발생하기 때문에
  대기시간이 발행하고 그 때문에 빠른 속도를 보장하지는 못한다.

- 이러한 문제를 해결하기 위해 자바에서는 멀티 스레드가 컬렉션의 요소를 병렬적으로 처리할 수 있도록 특별한 컬렉션을 제공한다.

#### ConcurrentHashMap, ConCurrentLinkedQueue

위 컬렉션은 세그먼트 잠금을 이용해 처리하는 요소가 포함된 부분만 잠금하고 나머지 부분은 다른 스레드가 변경할 수 있도록 한다.

```
  Map<K, V> map = new ConcurrentHashMap<K, V>();
  Queue<E> queue = new ConcurrenntLinkedQueue<E>();
```

//참고: [이것이 자바다 정리 #15 컬렉션 프레임워크](https://velog.io/@jakeseo_me/%EC%9D%B4%EA%B2%83%EC%9D%B4-%EC%9E%90%EB%B0%94%EB%8B%A4-%EC%A0%95%EB%A6%AC-15-%EC%BB%AC%EB%A0%89%EC%85%98-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AChttps://velog.io/@jakeseo_me/%EC%9D%B4%EA%B2%83%EC%9D%B4-%EC%9E%90%EB%B0%94%EB%8B%A4-%EC%A0%95%EB%A6%AC-15-%EC%BB%AC%EB%A0%89%EC%85%98-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC)

## [Spring] HiddenHttpMethodFilter

//
참고: [Spring의 HiddenHttpMethodFilter에 관한 이슈](https://imbf.github.io/spring/2020/05/03/Spring-HiddenHttpMethodFilter.html)

## [Spring] WebMVCConfigurer

WebMVCConfigurer는 Spring MVC 프레임워크에서 사용하는 인터페이스 중 하나다.   
다양한 메서드들을 오버라이드해 사용할 수 있는데, 그 중 미션에서 언급된 `addViewControllers(`) 메서드를 중점으로 학습했다.

### addViewControllers()

- URL 경로와 View 이름을 매칭해 컨트롤러 없이 간단한 view를 반환한다.
- HTTP 메서드 중 GET만 다룬다.(리다이렉트도 가능하다.)
    - 이 메서드와 같이 view 매핑과 관련된 메서드들은 GET 요청에 대한 처리를 담당한다.

```java

@Configuration
public class MvcConfig extends WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);  // 이 메서드를 매핑 설정 중 가장 높은 우선순위로 설정

        // addViewController("") <- 인자로 들어온 URL에 대해서
        // setViewName("") <- 파라미터로 작성된 view를 반환하도록 매핑한다.
        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/questions/form").setViewName("qna/form");
    }
}
```

### WebMvcConfigurerAdapter가 deprecated 된 이유

WebMvcConfigurerAdapter는 Spring 5, SpringBoot 2 부터 deprecated 되었다고 한다.   
WebMvcConfigurerAdapter는 상속인 반면, WebMvcConfigurer는 인터페이스이다.   
WebMvcConfigurerAdapter가 deprecated 된 이유는 상속과 인터페이스 차이점 때문인 것 같은데.. 이 부분을 더 공부해봐야겠다. 📌 // 디폴트 메서드를 찾아보자

## [Thymeleaf] Fragment Expressions

각 파일마다 중복되는 HTML 코드를 타임리프 fragment를 이용해 간소화 할 수 있다.

### Fragment 나누기

중복되는 HTML 코드는 templates>fragments 디렉토리로 옮기고, 각 페이지에서 fragment 파일을 불러와서 사용한다.

- 먼저, 타임리프 사용 선언으로 html 태그에 `xmlns:th="http://www.thymeleaf.org"`를 추가한다.

- head 부분을 fragment로 선언 시, \<head> 태그에 `th:fragment="fragment명"`을 작성한다.
  > **th:fragment**는 해당 부분을 fragment로 선언한다는 의미

- 선언한 fragment를 가져올 때는 `th:replace="fragment 위치 :: fragment명"` 로 호출한다.
  > **th:replace**는 해당 태그에 fragment로 선언한 코드로 치환한다는 의미

// 참고: [Thymeleaf Fragment - 1 (Fragment 나누기)](https://sgoho01.tistory.com/24)

## [Thymeleaf] onClick 이벤트

### HTML onClick 이벤트

모든 속성의 태그에 onClick 이벤트를 발생시킬 수 있다.

- 현재 창에서 이동
    - `<td onClick="location.href='링크주소'">링크로 이동\</td>`

- 새 창으로 이동
    - `<td onClick="window.open('링크주소','','')">링크로 이동\</td>`

//
참고: [table tr td에 onClick 이벤트로 링크 걸기](https://bbirukim.tistory.com/entry/table-tr-td%EC%97%90-onClick-%EC%9D%B4%EB%B2%A4%ED%8A%B8%EB%A1%9C-%EB%A7%81%ED%81%AC-%EA%B1%B8%EA%B8%B0)

### 타임리프로 onClick 이벤트 발생시키기

- `th:onclick="|location.href='@{/}'|"`

// 참고: [[Thymeleaf] 타임리프 th:onclick 사용하기](https://zoetechlog.tistory.com/112)

## [Spring] Annotation

### Annotation이란?

Annotation은 Java5부터 추가된 문법요소이다.
사전적으로는 "주석"이라는 의미를 가지고 있고, 자바 코드에 @를 이용해 주석처럼 달아 사용하는 메타데이터의 일종이다.

Annotation은 클래스와 메서드에 추가하여 다양한 역할이나 기능을 부여하는 역할을 한다.   
Annotation을 활용해 Spring Framework는 해당 클래스가 어떤 역할인지 정하기도 하고, Bean을 주입하기도 하며, 자동으로 getter나 setter를 생성하기도 한다.

> **자바 리플렉션**
> > 컴파일 시간이 아닌 실행 시간에 동적으로 특정 클래스의 정보를 객체를 통해 분석 및 추출해내는 프로그래밍 기법

![스프링 레퍼런스 메뉴얼](https://velog.velcdn.com/images/esgibtnureins/post/20c71e1c-9975-4887-9c38-6068608505bb/image.png)

### @Controller

Spring에게 해당 Class가 Controller의 역할을 한다고 명시하기 위해 사용한다.

```java

@Controller                   // 이 Class는 Controller 역할을 합니다.
@RequestMapping("/user")      // 이 Class는 /user로 들어오는 요청을 모두 처리합니다.
public class UserController {
    @RequestMapping(method = RequestMethod.GET)
    public String getUser(Model model) {
        //  GET method, /user 요청을 처리
    }
}
```

### 📕 Mapping

### @RequestMapping

`@RequestMapping(value="")`와 같은 형태로 작성하며, 요청 들어온 URI의 요청과 Annotation value 값이 일치하면 해당 클래스나 메서드가 실행된다.
Controller 객체 안의 메서드와 클래스에 적용 가능하다.

- 기본값은 GET 방식이다.
- Class 단위에 사용하면 하위 메서드에 모두 적용된다.
- 메서드에 적용되면 해당 메서드에서 지정한 방식으로 URI를 처리한다.

```java

@Controller                   // 이 Class는 Controller 역할을 합니다.
@RequestMapping("/user")      // 이 Class는 /user로 들어오는 요청을 모두 처리합니다.
public class UserController {
    @RequestMapping(method = RequestMethod.GET)
    public String getUser(Model model) {
        //  GET method, /user 요청을 처리
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addUser(Model model) {
        //  POST method, /user 요청을 처리
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String addUser(Model model) {
        //  GET method, /user/info 요청을 처리
    }
}
```

Spring 4.3부터 Spring MVC Controller Method를 위한 어노테이션이 추가되었다.
각각의 어노테이션들은 HttpMethods에 매칭되며, method 단에서 사용 가능하다.

- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping
- @PatchMapping

### @GetMapping

- `@GetMapping(value = {"a", "b"}`처럼 value를 이용하면 다중 매핑이 가능하다.

```java

@Controller
public class Controller {

    @GetMapping(value = {"/", "/index"})
    public String home() {
        return "index";
    }


    @GetMapping("/users")
    public String joinSuccess(Model model) {
        model.addAttribute("users", memoryUserRepository.findAll());
        return "user/list";
    }
}
```

### @PostMapping ✔️

```java

@Controller
public class Controller {
    @PostMapping("/user/form") // HTTP POST 요청이 "/user/form"으로 들어왔을 때, 이 클래스의 post() 메소드가 실행된다.
    public String post(@RequestParam("userId") String userId,
                       @RequestParam String password,
                       @RequestParam String name,
                       @RequestParam String email) {

        User user = new User(userId, password, name, email);
        memoryUserRepository.save(user);
        logger.info(user.toString());

        return "redirect:/users";
    }
}
```

---

### 📕 스프링 controller에서 파라미터를 받는 방법

### @RequestParam

URI에 전달되는 파라미터를 메서드의 인자와 매칭시켜 처리한다.

- `@RequestParam("key값")`을 붙이면 URI의 파라미터(value)를 받아올 수 있다.

```java

@Controller
public class UserController {
    @PostMapping("/user/form")
    public String post(@RequestParam("userId") String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email) {

        User user = new User(userId, password, name, email);
        memoryUserRepository.save(user);
        logger.info(user.toString());

        return "redirect:/users";
    }
}
```

### @PathVariable

메서드 파라미터 안에 @PathVariable을 붙이고 변수를 선언하면, 경로의 파라미터가 선언한 변수에 담긴다.

```java

@Controller
public class UserController {
    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        User user = memoryUserRepository.findById(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }
}
```

// 참고:

- [스프링(Spring)에서 자주 사용하는 Annotation 개념 및 예제 정리](https://melonicedlatte.com/2021/07/18/182600.html)
- [[Java / Spring ] 어노테이션(@, annotation)의 정의와 종류](https://prinha.tistory.com/entry/%EC%9E%90%EB%B0%94-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-annotation%EC%9D%98-%EC%A0%95%EC%9D%98%EC%99%80-%EC%A2%85%EB%A5%98)
- [스프링 controller에서 파라미터를 받는 다양한 방법 ( @RequestParam, @RequestBody, @PathVariable)](https://velog.io/@shson/%EC%8A%A4%ED%94%84%EB%A7%81-controller%EC%97%90%EC%84%9C-%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0%EB%A5%BC-%EB%B0%9B%EB%8A%94-%EB%8B%A4%EC%96%91%ED%95%9C-%EB%B0%A9%EB%B2%95-RequestParam-RequestBody-PathVariable)
