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

---

# 2차 피드백 정리

## 1. 왜 Map을 사용해서 MemoryRepository를 구현했는가?
- Map의 특성 상 임의로 Key를 지정할 수 있고 이를 통해 데이터를 탐색하는 과정에 있어서 다른 자료구조보다 유리할 것이라 판단했다.
- 나아가 ConcurrentHashMap을 사용함으로써 동시성 문제를 해결할 수 있는 장점이 있기 때문에 Map을 사용했다.

## 2. git-lfs
- Git Large File Storage의 약자로, git으로 관리하기에는 큰 파일(주로 바이너리 파일)을 효과적으로 관리하기 위한 확장 프로그램이다.
- git-lfs를 사용하면 git의 성능과 안정성을 유지하면서 대용량 파일을 쉽게 관리할 수 있다.
- git은 모든 파일을 직접 저장하지만, git-lfs를 사용하면 대용량 파일은 git 저장소에 직접 저장되지 않고 외부 서버에 저장되며, git 저장소는 해당 파일의 포인터만 가지고 있다.
- 따라서 git-lfs를 사용하면 대용량 파일을 효과적으로 관리하고, 로컬 저장소 크기를 줄일 수 있다.
- 또한 git 저장소에서 작업하는 동안에도 대용량 파일에 대한 작업이 원활하게 이루어진다.

## 3. @ModelAttribute
- 해당 메소드의 반환값을 모델(Model) 객체에 자동으로 추가한다.
- @ModelAttribute를 메소드의 매개변수에 사용하면, 해당 매개변수가 HTTP 요청 파라미터에서 추출되어 모델 객체에 추가된다.
  - 컨트롤러 메서드의 파라미터에 직접 값을 넣어주지 않아도 된다.
  - 대신 @ModelAttribute를 사용하여 객체를 생성하면 스프링이 자동으로 값을 바인딩해준다.
  - 이로 인해 컨트롤러 메서드의 파라미터 개수가 줄어들어 코드 가독성이 좋아진다.
- 해당 메소드가 처리하는 요청 URL에서 사용자가 전송한 데이터를 받아와 모델 객체에 추가할 수 있다.
- @ModelAttirbute를 통해 컨트롤러 메서드에서 해당 모델의 이름을 바꿀 수 있다.
  - 이를 통해 뷰에서 해당 모델을 참조할 때 사용할 이름을 변경할 수 있다.

## 4. UserDto에서 final을 사용한 이유?
- UserDto에서 final을 사용한 이유는 UserDto 객체가 생성된 후에는 해당 필드들의 값이 변경되지 않도록 하기 위함이다.
- 접근제한자 final을 사용하면 해당 필드들은 읽기 전용 상태가 되어서, 객체 생성 후에는 값이 변경될 수 없다.
- UserDto 객체가 한 번 생성된 이후에는, from() 메소드에서 전달된 User 객체를 통해 해당 필드들의 값을 초기화한 뒤에 해당 필드들의 값이 변경될 일이 없도록 보장할 수 있다.

## 5. properties 옵션
- spring.mustache.servlet.cache
  - Mustache 템플릿 캐싱을 활성화하는 데 사용된다.
  - Mustache는 템플릿 파일을 읽고 컴파일하는 데 시간이 오래 걸리므로 캐싱을 활성화하면 성능을 향상시킬 수 있다.

- server.servlet.encoding.force
  - 서버에서 전송하는 모든 HTTP 응답에 대해 강제로 인코딩을 설정한다.
  - 이 설정은 문자 집합 문제로 인해 응답이 깨지는 것을 방지할 수 있다.

- spring.mvc.hiddenmethod.filter.enabled
  - HTTP POST 요청의 숨겨진 메소드를 처리할지 여부를 설정한다.
  - 숨겨진 메소드는 HTML form에서 HTTP POST 요청을 보낼 때 사용된다.