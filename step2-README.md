스프링 카페 2단계 - 글 쓰기 기능 구현
=

## 1. 글 쓰기 기능
- [x] POST요청 담당하는 메서드 추가 및 매핑
- [x] Article 클래스 생성해서 저장
- [x] Article 인스턴스를 저장하는 Repository 구현
- [x] 게시글 추가 후 메인 화면으로 redirect

![image](https://user-images.githubusercontent.com/118447769/231078485-a74fe1c7-fe8b-4705-9213-fb891efaa250.png)  

- Posts 탭을 누르면 홈 화면에 접근한다.
- 질문하기 탭을 누르면 ArticleController에서 /question url로 이동하여 qna/form 을 매핑을 통해 GET 한다.
- 글쓴이, 제목, 내용 에 입력을 하고 질문하기를 누르면 qna/form 에 입력한 세 가지 값을 post 메서드는 통해 PostRequest DTO로 넘겨받는다.
- POST 방식으로 request 를 articleRepository에 저장한다.
- 이후 최종적으로 글 목록을 포함한 메인 화면을 redirect 한다.


## 2. 글 목록 조회 기능
- [x] 메인 컨트롤러에서 게시글 목록 조회
- [x] 게시글 목록을 Model에 저장한 후 View에 전달
- [x] View에서 Model을 통해 전달한 게시글 목록을 출력

![image](https://user-images.githubusercontent.com/118447769/231075792-ae071666-71eb-4bf7-9ac4-e10522fbb16a.png)  

- Posts 탭을 누르면 작성된 글들을 포함한 메인 화면을 조회할 수 있다.
- 글 제목 왼쪽에 숫자는 point로 article 작성 시 repository에 저장된 id 이다.


## 3. 게시물 상세 보기 기능
- [x] 제목 클릭 시 게시글 상세 페이지로 이동
- [x] 게시글 객체에 Id 인스턴스 변수 추가
- [x] 새로운 게시글 쓸 때마다 id 증가

![image](https://user-images.githubusercontent.com/118447769/231076883-2e16de16-535c-403b-b01b-5ccb8fecd798.png)  

- 컨트롤러의 showArticleDetail 메서드를 통해 articleId로 찾은 ArticleDto 를 GET 방식으로 가져온다.
- qna/show 를 리턴함으로써 작성된 글의 제목을 누르면 a 태그를 통해 /articles/{articleId} url로 이동한다.


## 4. 회원정보 수정
- [x] 아이디는 수정 불가
- [x] PUT 메서드 사용

![image](https://user-images.githubusercontent.com/118447769/231079937-04cf562a-e34c-4c5f-ba9a-edf160191f61.png)  

- 회원 목록에서 수정 버튼을 누르면 /profile-edit-form/{{userId}} 를 GET 방식으로 가져와서 showProfileEditPage 메서드를 통해 수정 폼을 출력한다.
- edit_form에서 입력한 값들을 ProfileEditRequest DTO를 통해서 UserService의 editUserProfile 메서드로 전달한다.
- 비밀번호를 검증하여 일치할 경우 기존에 저장된 user의 정보 중 request DTO에 들어있는 세가지 값으로 PUT방식으로 업데이트 한다.
- 이후 회원 리스트로 돌아간다.(/users를 redirect한다.)