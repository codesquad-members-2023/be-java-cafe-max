# be-java-cafe  Step4

### 📌 4 / 17 학습계획

- [x] 인프런 강의 듣기
- [x] 데이터베이스 1:N 관계 찾아보기
- [x] 개인 공부

### 📌 4 / 18 학습계획

- [x] 인프런 강의듣기
- [x] 개인공부
- [x] 댓글 저장하는 기능 구현하기
- [x] 저장된 댓글 게시글에서 보여주는 기능 구현하기

##### ✔️ 댓글 저장하는 기능

- [x] 댓글의 내용을 받아올 CommentRequest 생성
- [x] CommentController에서 댓글의 내용을 가진 CommentRequest와, 댓글을 쓴 사용자 ID를 꺼내올 HttpSession,
  @PostMapping("articles/{id})에서 article의 ID를 꺼내올 PathVariable을 매개변수로 받아서 CommentService에 전달.
- [x] CommentService에서 commentSave() 메서드 생성 -> Controller에서 받은 정보로 CommentRepository에 넘길 Comment 객체 생성
- [x] CommentRepository 인터페이스 생성 후 댓글 내용을 저장할 save() 메서드 생성
- [x] JdbcCommentRepository에서 save() 메서드를 오버라이드 하고 매개변수로 받은 Comment 객체를 가지고 DB에 update 쿼리를 날려 댓글 내용 저장

##### ✔️ 게시글에서 저장된 댓글 보여주는 기능

- [x] JdbcCommentRepository에서 article_id를 조건으로 일치하는 article_id를 가진 데이터 가져온 후 CommentService에 List 형태로 전달
- [x] Repository에서 전달받은 Comment객체를 Service layer에서 CommentDto로 바꿔 **ArticleController**로 전달
- [x] ArticleController에서 게시글 세부내용 + 댓글을 출력하기 위해 Model로 CommentDto를 전달

### 📌 4 / 19 학습계획

- [x] 인프런 강의듣기
- [x] 개인 공부
- [x] 댓글 삭제 기능구현
- [ ] MySQL 설치

##### ✔️ 오늘의 문제❓

- 댓글 삭제 기능을 완성하긴 했지만, 완성해놓고 보니 얼렁뚱땅 그 자체,, 삭제하려는 댓글의 userId와 로그인 된 Id를 비교하고 id가 같으면 삭제를,
  같지 않다면 error 페이지를 보여주게 했는데 error페이지가 뜨면 url에 매핑해놓은 정보가 다 드러난다,, (매핑해놓은,,?이 맞나?)

> 해결내용
> form에서 input hidden으로 userId와, comment의 Id를 가져오려면 url에 드러내야 하는줄 알았는데 아니었음! 매개변수에 담아주면 된다.<br>
> 그래서 필요했던 id와 userId, articleId 까지 매개변수로 받아와서 사용했다! form 태그에 대한 이해도가 부족해 생긴 일 이었다..! 정신 바짝 채리야지

##### ✔️ 소감

- form 태그를 완전히 잘못 생각하고 있었다 action 부분을 제대로 이해하지 못하고 사용했는데 여지껏 어떻게 미션을 해왔을까^^.. 이제라도 알아서 다행이라고 생각하자..!
- 오늘 설리와 페어리뷰 하면서 좋은 영향력을 받은거같다! 더 열심히 불태워보고 싶은 욕심이 생겼고, 나도 누군가에게 도움이 될 수 있는 사람이 되고싶다 생각했다
- 오늘 하려했던 MySQL을 해내지 못했다..아직 시간 관리가 잘 안되는거같다. 는 핑계로 스터디 플래너를 사왔다 다음주 부터 열심히 적어봐야지 ~,,

##### ✔️ 댓글 삭제하기 기능

- [x] CommentController에서 로그인 된 userId와 삭제하려는 댓글의 Id가 일치하는지 확인 후 일치하면 CommentService의 deleteComment메서드에
  commentId를 매개변수로 넘겨주고, 일치하지 않으면 error페이지 반환
- [x] CommentService의 deleteComment메서드에 매개변수로 받은 id를 JdbcCommentRepository.delete()메서드에 id값 전달
- [x] JdbcCommentRepository.delete메서드는 매개변수로 받은 id값을 조건으로 설정, 일치하는 조건의 댓글을 삭제하는 쿼리를 날림.

### 📌 4 / 20 학습계획

- [x] MySQL 설치
- [x] 인프런 강의 듣기
- [x] 개인 공부

##### ✔️ 오늘의 문제❓

- 오늘 인프런 강의를 보다보니 내가 잘못한 점들이 보였다 CommentController에서 `"redirect:/~"+ articleId` 이런식으로 넣어줬는데 이런식으로 사용하는건
  URL 인코딩이 안되서 예를들어 한글이거나, 띄어쓰기가 있거나 하면 굉장히 위험하단 얘기를 들었다 ! 개선할 수 있는 방법이 있는지 찾아봐야겠다
- MySQL을 설치하고 인텔리제이랑 연결하는데 계속 비밀번호가 틀린다는 오류가 생겼다 터미널에서는 잘 되는데 왜그런지 이해가 안가서 성질이 잔뜩 났다.
  또 grant로 접근 권한을 줄 때도 자꾸 `grant all privileges on cafe.* to 'pie'@'%' identified by '1234';` 여기서 syntax 오류가 나는데
  이유를 못찾아서 오늘 하루를 다 날렸다 CS 때도 그러더니,, MySQL 진짜 밉고 싫다 흑흑,,

### 📌 4 / 21 학습계획

- [ ] 댓글 수 count 하는 기능 구현
- [ ] 강의 듣기
- [ ] 개인 공부하기

