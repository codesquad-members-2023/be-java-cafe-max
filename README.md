# be-java-cafe

마스터즈 2023 스프링 카페

## 학습계획

### 03. 27. 학습 계획

* GET/POST 메서드의 차이 학습하기
* 스프링 빈 라이프 사이클 학습하기
* mustache template engine 사용법 익히기
* 회원가입 기능 구현하기
    * mustache template engine 의존성 추가후 관련 폴더 이동
    * .html 파일에서 공통되는 부분 추출하기 (header, footer..)
    * UserController 클래스 작성하기
        * `GET /user/create` : user/form.html 뷰 반환
        * `POST /user/create` : 회원가입 수행 후 redirection하기
    * UserRepository 클래스 작성하기
        * DB를 사용하지 않아야하기 때문에 추후 DB도입을 고려해 interface로 분리하기
    * UserService 클래스 작성하기
        * 회원가입을 수행하는 로직 작성하기
        * 발생하는 예외 생각
            * 이미 존재하는 유저 아이디라면 중복예외 발생

### 03. 28. 학습 계획

* 회원 목록 조회 기능 구현하기
    * `/users`로 요청이 들어오면 `UserMemoryRepository`에 존재하는 모든 유저 정보를 반환
* 회원 프로필 조회 기능 구현하기
    * `/users/{userId}`로 요청이 들어오면 `UserMemoryRepository`에서 `userId`를 키로 하는 `User` 정보 반환
    * `userId`가 없는 경우 에러 페이지 반환하기
* 인프런 강의 듣기
    * 토비의 스프링 부트
* 운영체제 스터디 준비

### 03. 29. 학습 계획

* 게시글 생성 기능 구현하기
    * `/quetion`으로 POST 요청이 들어오면 게시글을 생성
* 게시글 목록 조회 기능 구현하기
    * `/articles`로 GET 요청이 들어오면 `ArticleMemoryRepository`에 존재하는 모든 게시글 정보를 반환
* 게시글 상세내용 조회 기능 구현하기
    * `/articles/{aritcleId}`로 GET 요청이 들어오면 `ArticleMemoryRepository`에서 `articleId`를 키로 하는 `Article` 정보 반환
    * `articleId`가 없는 경우 에러 페이지 반환하기
* 회원 정보 수정 기능 구현하기
    * `/users/{userId}/form`로 GET 요청이 들어오면 회원 정보 수정 폼 보여주기
        * 이때 기존 회원 정보 보여주기
    * `/users/{userId}`로 PUT 요청이 들어오면 회원 정보 수정하기
* 인프런 강의 듣기 (토비의 스프링 부트)
* 알고리즘 1개 풀기

### 03. 30. 학습 계획

* 페어리뷰
* 인프런 강의 듣기 (토비의 스프링 부트)
* 알고리즘 1개 풀기
* 운영체제 강의 듣기 (가상 메모리)

## 학습내용

<details>
<summary>HTTP method : GET / POST</summary>

### Http method : GET / POST

* GET
    * 서버의 리소스를 가져오는 메서드입니다.
    * `http request message`의 바디는 작성하지 않고 쿼리파라미터를 통해 정보를 전달할 수 있습니다.
    * 동일한 `GET` 요청에 대해서는 동일한 데이터를 반환해야하는 멱등성을 띄고 있습니다.
        * 이로 인해 `GET` 요청은 캐싱에 사용될 수 있습니다.
* POST
    * 서버의 리소스를 생성 / 변경하는 메서드입니다.
    * `GET`과는 다르게 `http request message`의 바디를 통해 정보를 전달할 수 있습니다.
    * 서버의 리소스를 변경시키기 때문에 멱등성이 유지되지 않습니다.

</details>

<details>
<summary>스프링 빈의 라이프 사이클</summary>

### 스프링 빈의 라이프 사이클

스프링 빈의 라이프 사이클은 크게 아래와 같습니다.

```
Spring container(Application Context) 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백-> 빈 사용 -> 소멸 전 콜백 -> 애플리케이션 종료   
```

위에서 초기화 콜백 과 소멸 전 콜백은 콜백 메서드를 통해 작업을 구현하게 됩니다. <br/>
스프링은 아래 세 가지 방법을 통해 콜백을 지원하게 됩니다.

* 특정 인터페이스를 구현 (`InitializingBean`, `DisposableBean`)
* 설정 파일에서 초기화, 소멸 메서드 지정
* 애노테이션을 통한 콜백 구현 (`@PostConstruct`, `@PreDistory`)

</details>
