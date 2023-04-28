스프링 카페 6,7 단계 - 댓글 구현
=
## 1. 댓글 조회 기능

![image](https://user-images.githubusercontent.com/118447769/235068144-902dec89-bef9-4233-82c2-73e3403deba7.png)

- 비로그인 상태에서는 세션에 "isLogin" 을 false로 두고 로그인에 성공하면 true 로 전환한다.

```html
{{#isLogin}}
    {{> qna/reply}}
{{/isLogin}}
```

- 세션을 조회하여 로그인된 상태일 경우에만 댓글을 노출한다.

## 2. 댓글 추가 기능

### 댓글 추가

![image](https://user-images.githubusercontent.com/118447769/235070239-3dc9d880-d992-45c1-9a3b-e3ef1e2ca609.png)  

```java
@PostMapping("/{articleId}/reply")
    public String reply(@PathVariable Long articleId, @ModelAttribute final ReplyRequest request, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null){
                replyService.reply(articleId, request, httpRequest);
            }
        }
        return "redirect:/articles/" + articleId;
    }
```
- 세션이 null이 아니고 로그인 된 상태이면 댓글 권한이 생긴다.
- 조회하고있는 게시글의 articleId와 입력한 ReplyRequest를 서비스에서 처리한다.
- 서비스 클래스를 통해 DB의 replies 테이블에 데이터를 저장한다.



### 게시물 내 댓글 개수 조회 기능
```java
@Override
    public Optional<Integer> countReply(Long articleId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM replies WHERE article_id = ?", Integer.class, articleId));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
```
- 임의의 articleId 를 가지고 replies 테이블의 article_id 컬럼을 조회하여 카운트 한 뒤 갯수를 리턴한다.
- 댓글이 없을 경우 0이 출력된다.

### 메인 화면 댓글 개수 조회 기능
![image](https://user-images.githubusercontent.com/118447769/235072085-ed328bb6-4615-4835-af56-bb5cfa905490.png)  

- 메인 홈에서 게시물 리스트의 좌측에 댓글 개수를 출력
- 게시물 내에서 댓글 개수를 카운트 하는 방식으로는 model 에 게시물과 댓글 개수 정보를 한 번에 보낼 수 없었다. (배열로 출력되는 오류)
```java
@Override
    public List<ArticleWithReplyCount> findAllArticlesWithReplyCount() {
        return jdbcTemplate.query("SELECT a.id, a.writer, a.title, a.created_at, COUNT(r.reply_id) AS reply_count "
                        + "FROM articles AS a "
                        + "LEFT JOIN replies AS r ON a.id = r.article_id "
                        + "GROUP BY a.id, a.writer, a.title, a.created_at ",
                (rs, rowNum) -> new ArticleWithReplyCount(rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getLong("reply_count")));
    }
```
- Left join 방식으로 댓글 개수를 구한 뒤, ArticleWithReplyCount 클래스를 통해 Article과 댓글 개수를 묶어서 전달하였다.

## 3. 댓글 삭제 및 수정 기능
```java
 if (session != null) {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser != null && loginUser.getUserId().equals(reply.getUserId())) {
        
        --- 수정 및 삭제 메서드 ---
        
        return "redirect:/articles/" + articleId;
    }
}
```
- 세션의 loginUser 아이디와 댓글 작성자의 아이디가 일치할 경우에만 수정 및 삭제가 가능하다.
- 수정 및 삭제는 기존에 Article 을 삭제하는 방식과 동일하다.
- 수정은 댓글창에서 바로 텍스트 수정 폼을 띄워서 처리하고 싶었으나 방법을 찾지 못하여 게시글 수정 처럼 특정 페이지로 넘어가서 로직을 처리하게 하였다.
- 수정 및 삭제가 끝나면 기존의 게시글로 redirect 한다. 


## 추가
### 회원 정보 조회 시 수정 버튼 노출
![image](https://user-images.githubusercontent.com/118447769/235078632-61879f54-197d-495f-a745-f39266221a56.png)  
```java
@GetMapping("/users")
    public String listAllUsers(HttpServletRequest httpRequest, Model model) {
        List<UserDto> users = userService.getUsers();
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null) {
                String loginUserId = loginUser.getUserId();
                for (UserDto user : users) {
                    user.setAuth(loginUserId != null && loginUserId.equals(user.getUserId()));
                }
            }
        }
        model.addAttribute("users", users);
        return "user/list";
    }
```
- 기존에는 로그인 여부에 상관없이 모든 계정의 회원정보를 수정할 수 있었다. (모든 줄에 버튼 활성화)
- userDto 필드에 접근권한 (auth)을 추가하였다.
- 회원 정보 전체를 리스트로 반환하여 이를 모델에 전달하기 전에 리스트를 탐색한다.
- setAuth 메서드를 통해 현재 세션에 올라가있는(로그인 되어있는) UserId와 일치하는 User일 경우 true, 이외에는 false 를 지정한다.
```html
{{#auth}}
    <td><a href="/profile-edit-form/{{userId}}" class="btn btn-success" role="button">수정</a></td>
{{/auth}}
```
- 세션에 올라가있는 loginUser의 auth 가 true 인 경우에만(본인일 경우에만) 수정 버튼을 활성화한다.

### 에러페이지
![image](https://user-images.githubusercontent.com/118447769/235076033-53e54fa8-7231-4309-b87e-a518ecba5173.png)  
- 게시글이나 댓글의 수정 및 삭제를 시도했을 때 권한이 없을 경우 상단부분에 에러메세지를 출력한다.
- 그러나 에러페이지를 이미 띄우고 나서 접근하는 하는 것이면 redirect 가 가능하지만 언제 어느부분에서 처음으로 에러메세지를 출력할 지는 알 수 없었다.
- 결국 매번 에러 페이지로 넘어갈 때 마다 failed 페이지에 필요한 article, articleId, reply, replyCount 가 필요했고 이를 매번 모델로 전달해주어야 했다.
- 이를 전반적으로 다루기 위해 컨트롤러에 ModelBuilder 클래스를 추가하였다.

- <U>굉장히 비효율적으로 에러 페이지를 띄우고 있음. ajax 를 적용해보면서 리팩토링할 것</U>
- <U>추가로 exception 패키지를 생성하여 전반적인 예외 및 에러 처리할것</U>


---

# RESTful api 설계 규칙 
참고 : https://velog.io/@pjh612/REST-API-URI-%EA%B7%9C%EC%B9%99

## REST란?
- Representational State Transfer의 약자
- 자원(URI), 행위(HTTP Method), 표현(Representations)로 구성되어 있다.

## REST API 설계시 유의점
- URI는 정보의 장원을 표현해야 한다.
- 자원에 대한 행위는 GET, POST, PUT, DELETE 등 HTTP Method로 표현한다.

## REST API URI를 결정하는 몇가지 규칙
### 1. 소문자를 사용한다
- 대문자는 때로 문제를 일으키는 경우가 있기 때문에 URI를 작성할 때는 소문자를 선호한다.
- RFC3986은 체계 및 호스트 구성요소를 제외하고 URI를 대소문자를 구분하여 정의한다.
- BAD
  - http://api.example.com/firstProject
- GOOD
  - http://api.example.com/first-project

### 2. 언더바대신 하이픈을 사용한다.
- 가독성을 위해 긴 Path를 표현하는 단어는 하이픈으로 구분하는 것이 좋다.
- 프로그램의 글자 폰트에 따라서 언더바 문자는 문자가 부분적으로 가려지거나 숨겨질 수 있다. 혼란을 야기할 수 있으므로 하이픈을 사용한다.
- BAD
  - http://api.example.com/blogs/guy-levin/posts/this_is_my_first_post
- GOOD
  - http://api.example.com/blogs/guy-levin/posts/this-is-my-first-post

### 3. URI의 마지막에는 슬래시를 포함하지 않는다.
- 후행 슬래쉬는 의미가 전혀 없고 혼란을 야기할 수 있다.
- 많은 웹 구성 요소와 프레임워크는 다음 두 URI를 동등하게 취급한다.  
  - http://api.canvas.com/shapes/  
  - http://api.canvas.com/shapes
- 그러나 URI내의 모든 문자는 리소스의 고유 ID에 포함된다.
- 두 개의 다른 URI는 두개의 다른 리소스에 매핑된다. URI가 다르면 리소스도 다르고 그 반대도 마찬가지다. 그러므로 REST API는 명확한 URI를 생성해야한다.
- BAD
  - http://api.canvas.com/shapes/
- Good
  - http://api.canvas.com/shapes

### 4. 계층관계를 나타낼 때는 슬래시 구분자를 사용해야한다.
- 슬래시 문자는 URI의 경로 부분에서 자원 간의 계층적 관계를 나타내기 위해 사용한다.
- 행위는 포함하지 않는다.
- 행위는 URL대신 Method를 사용하여 전달한다.
- Bad
  - http://api.college.com/get-students
- Good
  - http://api.college.com/students/

### 5. 파일 확장자는 URI에 포함시키지 않는다.
- 파일 확장자는 URI에 포함하지 말아야한다.
- 대신에 Content-Type 이라는 헤더를 통해 전달되는대로 미디어 타입을 사용하여 body의 콘텐츠를 처리하는 방법을 결정한다.
- Rest API클라이언트는 HTTP에서 제공하는 형식 선택 메커니즘인 Aceept 요청 헤더를 활용하도록 권장해야 한다.
- Bad
  - http://api.college.com/students/3248234/courses/2005/fall.json
- Good
  - http://api.college.com/students/3248234/courses/2005/fall

### 6. 전달하고자 하는 자원의 명사를 사용하되, 컨트롤 자원을 의미하는 경우 예외적으로 동사를 허용한다.
- BAD
  - http://api.college.com/course/writing
- GOOD
  - http://api.college.com/course/write

### 7. URI에 작성되는 영어를 복수형으로 작성한다.
- 하나의 인스턴스를 복수형으로 표시하는게 영어 문법적으로 맞지 않겟다고 생각할 수도 있지만 URI의 형식을 복수형으로 사용하는 것이 실무에서 많이 사용되고 있다.
- 관계가 다른 리소스 내에서만 존재할 경우 RESTFUL 원칙은 다음과 같은 지침을 제공한다.
- http://api.college.com/students/3248234/courses
  - ID가 3248234인 학생이 학습한 모든 과정 목록 검색
- http://api.college.com/students/3248234/courses/physics
  - ID가 3248234인 학생을 위한 과정 물리학을 검색한다.

---

# 5차 피드백 정리

- [x] post 메서드 url 수정
- [x] showArticleForm 과 deleteArticle 메서드 분기 처리 개선
- [x] editArticle 메서드가 수정한 article을 리턴하도록 수정
- [x] delete 메서드 url 수정 -> 자원만 표시하도록 수정
- [x] ArticleService 에 위치한 getUserIdFromSession 메서드 util 클래스로 분리
- [ ] User 도메인 필드 변수명 수정
- [ ] jdbc 전역적으로 예외처리
- [ ] login_fail 예외 처리 (위와 동일한 방법으로)