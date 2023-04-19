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

- [ ] 인프런 강의듣기
- [ ] 개인 공부
- [ ] 댓글 삭제 기능구현
