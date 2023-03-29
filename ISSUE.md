# 문제해결

## Controller에서 templates 디렉토리에 있는 html 파일 경로를 탐색하지 못하는 문제

resources/static 디렉토리에 있는 html 파일들을 resources/templates 디렉토리로 이동하였고 컨트롤러를 생성하여

url 매핑이 되는지 확인하는 중이었습니다. 그러나 thymeleaf 라이브러리가 templates 디렉토리에 있는 html 파일을

탐색하지 못하는 문제가 발생하였습니다.

```text
@Controller
public class UserController {

    @GetMapping("/users/form")
    public String form() {
        return "user/form";
    }
}
```

문제의 원인은 thymeleaf 의존성 라이브러리를 추가할때 잘못된 라이브러리를 추가하여서 문제가 발생한 것 같았습니다.

다음 의존성 라이브러리는 기존 추가한 thymeleaf 라이브러리입니다.

```text
implementation 'org.thymeleaf:thymeleaf:2.0.21'
```

위 thymeleaf 라이브러리를 지우고 다음 라이브러리를 추가하여 문제를 해결하였습니다.

```text
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
```

## Dto 객체가 공백 생성자가 필요한 이유

단위 테스트 도중 회원가입 POST 요청을 받을 URL 매핑을 다음과 같이 정의하였습니다.

```java

@Controller
public class UserController {

    // ...
    @PostMapping("/user/create")
    public String newUser(UserSavedRequestDto requestDto, Model model) {
        UserResponseDto userResponseDto = userService.save(requestDto);
        model.addAttribute("user", userResponseDto);
        return "redirect:/users";
    }
}
```

위 매개변수의 UserSavedRequestDto 객체애 대한 정의는 다음과 같았습니다.(공백 생성자가 없는 상태였습니다.)

```java
public class UserSavedRequestDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserSavedRequestDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
```

위와 같은 상태에서 회원가입 요청을 하였는데 다음과 같은 에러가 발생하였습니다.

```
com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `kr.codesqaud.cafe.web.dto.UserSavedRequestDto` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
```

위 에러의 내용은 Dto 객체를 생성하려고 하는데 공백 생성자가 없어서 만들수 없었다는 내용이었습니다.

제가 실수했던 부분은 스프링 프레임워크가 Dto 객체의 생성자를 통해서 객체를 만들 줄 알았으나 실제로는

공백 생성자를 통하여 빈 객체를 하나 생성하고 setter 메소드를 통하여 객체의 값을 저장한다는 것을 발견하였습니다.

즉, **정리하면 Dto 객체가 공백 생성자가 필요한 이유는 매개변수에 dto 객체로 클라이언트가 제출한 데이터 정보를 받을때 공백 생성자를 생성한 다음에 setter 메소드를
통하여 받기 때문입니다.**

## TestRestTemplate 자동 주입을 해주기 위해서 랜덤 포트를 설정해야 하는 이유

```java

@SpringBootTest
class UserControllerTest {

    // ...
    @Autowired
    private TestRestTemplate restTemplate;
}
```

위 코드와 같이 TestRestTemplate 객체를 이용하여 회원가입을 테스트하고자 하였는데 TestRestTemplate 객체를 자동 주입할 수 없다고

컴파일 에러를 날려주었습니다. 실행시 에러의 내용은 자동 주입할때 등록된 빈이 없다는 내용이었습니다.

자동 주입할 수 없었던 원인은 **TestRestTemplate 객체의 빈을 자동주입하기 위해서는 스프링 부트가 열려 있는 포트 중 하나를**

**등록을 해야 하는데 현재 단위 테스트에서는 열려 있는 포트가 없어서 자동 주입에 실패** 했던 것이었습니다.

따라서 다음과 같이 단위 테스트시 스프링 부트에 랜덤한 포트를 열수 있도록 설정하였습니다.

```java

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    // ...
}
```

## 회원가입 처리 중 중복된 아이디나 중복된 이메일이 발생한 경우 어떻게 다중 예외 처리를 할것인가

1. 커스텀 예외의 기반이 되는 BaseException을 정의합니다.

```java
public abstract class BaseException extends RuntimeException {

    public abstract BaseExceptionType getExceptionType();
}
```

2. 사용자와 관련된 예외를 저장하는 UserException을 정의합니다. UserException 클래스는 BaseException의 구현체입니다.

```java
public class UserException extends BaseException {

    private final BaseExceptionType exceptionType;

    public UserException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
```

3. BaseExceptionType 인터페이스를 정의합니다. 이 인터페이스는 외부로부터 에러 코드, HTTP 상태 코드, 에러 메시지를
   응답하는 인터페이스입니다.

```java
public interface BaseExceptionType {

    int getErrorCode();

    HttpStatus getHttpStatus();

    String getErrorMessage();
}
```

4. 사용자에 대한 BaseExceptionType 구현체인 UserExceptionType enum 클래스를 구현합니다.

```java
public enum UserExceptionType implements BaseExceptionType {
    // 회원가입 또는 로그인시 발생할 수 있는 예외
    ALREADY_EXIST_USERID(600, HttpStatus.OK, "이미 존재하는 아이디입니다."),
    ALREADY_EXIST_EMAIL(601, HttpStatus.OK, "이미 존재하는 이메일입니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    UserExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
```

5. BaseException 예외를 다루는 핸들러 클래스인 ExceptionAdvice 클래스를 정의합니다.

@ControllerAdvice 애노테이션을 붙인 클래스는 애플리케이션 실행 중 BaseException 예외가 발생하면 ExceptionDto 객체를 생성하여

ResponseEntity 객체를 만들어 클라이언트에게 응답합니다.

```java

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    private static final Logger logger = Logger.getLogger("ExceptionAdvice");

    // BaseException을 구현한 예외 클래스들을 처리하고 응답합니다.
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
            ex.getExceptionType().getErrorCode(),
            ex.getExceptionType().getHttpStatus(),
            ex.getExceptionType().getErrorMessage());
        logger.info(exceptionDto.toString());
        return new ResponseEntity<>(exceptionDto, exceptionDto.httpStatus);
    }

    // 서버에서 예외가 발생하더라도 상태코드 200을 반환합니다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    static class ExceptionDto {

        private final int errorCode;
        private final HttpStatus httpStatus;
        private final String errorMessage;

        public ExceptionDto(int errorCode, HttpStatus httpStatus, String errorMessage) {
            this.errorCode = errorCode;
            this.httpStatus = httpStatus;
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return String.format("{errorCode: %d, httpStatus: %s, errorMessage: %s}",
                errorCode, httpStatus, errorMessage);
        }
    }
}
```

6. 회원가입 서비스를 처리하는 UserService 클래스에서 입력에 따른 중복이 발생하면 UserException 예외를 발생시킵니다.

```java

@Service
public class UserService {

    // ...
    public UserResponseDto save(UserSavedRequestDto requestDto) {
        validateDuplicatedUserId(requestDto);
        validateDuplicateUserEmail(requestDto);
        User saveUser = userRepository.save(requestDto.toEntity());
        return new UserResponseDto(saveUser);
    }

    private void validateDuplicatedUserId(UserSavedRequestDto requestDto) {
        if (userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_USERID);
        }
    }

    private void validateDuplicateUserEmail(UserSavedRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_EMAIL);
        }
    }
}
```

## 중복된 아이디나 이메일을 제출하고 리다이렉트가 아닌 200 ok를 받은 경우 어떻게 페이지를 유지하고 에러를 UI에 표시할 것인가?

## TODO: 패스워드를 어떻게 암호화해서 저장할 것인가?

## References

- [\[일지\] TestRestTemplate 로 테스트 하면서 궁금했던 점(feat :NoSuchBeanDefinitionException)](https://onejunu.tistory.com/67)
- [Difference between MockMvc and RestTemplate in integration tests](https://stackoverflow.com/questions/25901985/difference-between-mockmvc-and-resttemplate-in-integration-tests)
- [회원 예외처리, 공통 예외처리 클래스 만들기](https://ttl-blog.tistory.com/290)
- [\[포토그램\] 1. 회원가입 - 전처리 & 후처리](https://velog.io/@kiwonkim/%ED%8F%AC%ED%86%A0%EA%B7%B8%EB%9E%A8-1.-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EC%A0%84%EC%B2%98%EB%A6%AC-%ED%9B%84%EC%B2%98%EB%A6%AC)
