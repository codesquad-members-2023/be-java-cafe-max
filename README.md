# be-java-cafe

마스터즈 2023 스프링 카페

# 기술 키워드
️▶️ [[Thymeleaf] Fragment Expressions](#thymeleaf-fragment-expressions)   
️▶️ [[Thymeleaf] onClick 이벤트](#thymeleaf-onclick-이벤트)   
️▶️ [[Spring] Annotation](#spring-annotation)


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
