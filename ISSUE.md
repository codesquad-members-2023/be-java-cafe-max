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
