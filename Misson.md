미션을 하면서 고민한 흔적

해결할 문제

페이지 별로 현재 페이지가 어디인지 나타내도록 수정. 현재는 모두 Posts에 bold 되어있음.

회원가입 할건데 form이 GET 방식으로 되어있다. 여기부터 시작할것.


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

    

