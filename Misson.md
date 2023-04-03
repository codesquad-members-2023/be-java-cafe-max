미션을 하면서 고민한 흔적

#### 해결할 문제

페이지 별로 현재 페이지가 어디인지 나타내도록 수정. 현재는 모두 Posts에 bold 되어있음.

회원가입 시에 모든 필드의 값이 채워지지 않아도 회원가입이 완료되는 상황이다.

값을 찾는 방법으로 아이디와 이메일 정도만 해야한다. 왜냐? 중복되지 않는 값으로 처리할 것이기 때문이다.

HTML 중복제거.

profile.html 에서 uid를 통한 회원조회를 위해 ../../css/style.css 로 변경하기는 했는데, 이후에 절대경로로 바꿔야할듯?

#### 개인적 과제

ConcurrentHashMap.

MVC 패턴에 대한 이해가 부족하다.

#### 메모

회원등록 : signUp



---


#### - gradle에 타임리프도 추가했는데 왜 templates 의 html을 호출하지 못하지?

intelliJ 재부팅으로 해결..?

#### - 나는 @GetMapping("/")을 통해 첫페이지를 불러왔는데, 기본 index.html의 좌상단 로고를 누르면 index.html을 호출해서 화이트페이지가 나온다. / , /index, /index.html 들을 같이 매핑하는 방법은 없나?

    ```
    @GetMapping("/")
    @GetMapping("/index.html")
    public String...
    ```

위와 같이 하면 될 줄 알았는데, 다중 애노테이션으로 오류가 발생하고, `@GetMapping({"/", "/index.html"})` 의 형태로 하는 방법을 알았다.

좀 더 예쁘게 쓴다면 `@GetMapping(value = {"/", "/index.html"})` 가 되겠다.

#### - /user/profile 을 주소로 할때는 괜찮은데, 특정 회원의 프로필 조회를 위해 /user/profile/1 을 주소로 하면 왜 서식이 날아갈까?

css 파일의 경로때문이었다. ../css/styles.css 로 명시가 되어있는데, /user/profile 일때는 ..을 통해 templates 쪽으로 이동해서 css 폴더를 찾는다.

그런데 /user/profile/1 에서는 ..으로 하면 잘못된 경로를 탐색한다. ../../css/style.css 로 변경하기는 했는데, 이후에 절대경로로 바꿔야할듯?

