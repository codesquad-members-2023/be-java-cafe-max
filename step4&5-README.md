스프링 카페 4, 5단계 - 로그인 및 게시물 권한부여
=

## 1. 로그인 및 로그아웃 기능 구현

- [x] 로그인 및 로그아웃 시 각각의 상황에 맞게 상단바 출력

### 로그인 하지 않았을 경우
![image](https://user-images.githubusercontent.com/118447769/233597991-48c842e1-b0ad-4a49-baf3-b2e1fb95bd70.png)  

### 로그인 했을 경우
![image](https://user-images.githubusercontent.com/118447769/233598219-244e0fbf-bc95-4fae-bb98-22fd13d6ee44.png)  


## 2. 개인정보 수정기능 추가

- [x] step3에 구현 완료

## 3. 게시글 작성하기

- [x] 글쓴이 입력필드 삭제
- [x] 로그인 하지 않은 사람은 글쓰기 기능을 이용할 수 없음 -> 버튼을 아예 없앴는데 로그인 페이지로 넘어가게 수정할 예정

![image](https://user-images.githubusercontent.com/118447769/233598483-32efd7dc-662d-452b-a7a8-24d5a83a86ae.png)  

### 글 작성 메서드

```java
public void post(PostRequest postRequest, HttpServletRequest httpRequest) {
    Article article = Article.from(getUserIdFromSession(httpRequest), postRequest);
    articleRepository.save(article);
}

public String getUserIdFromSession(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
        return (String) session.getAttribute("userId");
    }
    return null;
}
```

- writer input 을 삭제했으므로 기존 DTO 의 writer 현재 로그인 중인 사용자의 userId를 넣어준다.
- 현내 로그인 된 사용자는 HttpServletRequest 를 통해 세션의 userId 값으로 알 수 있다.

## 4. 게시글 수정하기

- [x] 수정하기 폼 과 수정하기 기능은 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능
- [x] 상황에 따라 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동
- [x] putmapping을 사용하여 매핑

### 수정 및 삭제 불가 메세지
![image](https://user-images.githubusercontent.com/118447769/233599013-57f77192-440c-4d3d-9f37-655ba316fc94.png)  

### ArticleController의 editArticle 메서드
```java
 @GetMapping("/articles/edit/{articleId}")
    public String editArticle(@PathVariable final Long articleId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ArticleDto article = articleService.findById(articleId);

        model.addAttribute("article", article);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());

        if (session.getAttribute("userId").equals(article.getWriter())) {
            return "qna/edit_form";
        }

        return "qna/failed";
    }
```
- 세션의 userId와 게시글 writer 의 일치여부 판단
- 일치할 경우 수정 폼으로, 일치하지 않을 경우 fail 페이지로 이동


### 수정 시 기존에 작성된 제목 및 내용 로딩
![image](https://user-images.githubusercontent.com/118447769/233599284-2d2f33e8-0698-49c1-914d-220642a966ce.png)  
### ArticleService 의 editArticle 메서드
```java
public void editArticle(final Long articleId, final PostEditRequest request){
        Article savedArticle = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
        savedArticle.editArticle(request.getNewTitle(), request.getNewContent());
        articleRepository.update(savedArticle);
        }
```
- articleId를 통해 수정 전 기존 글의 제목과 내용을 qna/edit_form 에 로딩
- 수정된 내용을 레파지토리에 update


## 5. 게시글 삭제하기

- [x] 삭제하기는 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능
- [x] 상황에 따라 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동 -> 게시물 수정 에러와 동일
- [x] deletemapping을 사용하여 매핑

### ArticleController 의 deleteArticle 메서드
```java
@RequestMapping(value = "/articles/delete/{articleId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    // get or post
    public String deleteArticle(@PathVariable final Long articleId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ArticleDto article = articleService.findById(articleId);

        if (session.getAttribute("userId").equals(article.getWriter())) {
            articleService.deleteArticle(articleId);
            return "redirect:/";
        }

        model.addAttribute("article", article);
        return "qna/failed";
    }
```

- 삭제가 불가능한 경우 get을 통한 에러 화면 출력, 삭제가 가능할경우 delete을 활용하기 위해 @RequestMapping 을 사용하였다.
- 게시물 수정과 동일하게 세션에서 로그인된 id를 가져와서 게시물 작성자와 동일 여부를 판단한다.
- 동일하다면 해당 id를 pk로 가지고있는 article 을 삭제한다.

---

# 4차 피드백 정리

## id를 제외한 나머지 필드에만 final 을 붙인 이유?
### 기존 코드
```java
public class Article {
    private Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
```
- 기존에는 메모리에 Map으로 데이터를 저장하는 형식이었는데 레파지토리에서 sequence를 증가시켜서 받은 id값을 set 하는 방식이었기에 final 을 사용하지 못했다.

### 변경 후 코드
```java
public class Article {
    private final Long id;
    private final String writer;
    private String title;
    private String content;
    private final LocalDateTime createdAt;
```
- 현재는 id를 도메인에서 set하지 않고 AUTOINCREMENT 를 통해 id 값을 넣어주므로 final을 추가했다.
- User 도메인의 경우에도 동일하다.

## Optional로 감싸서 리턴하는 이유?
### 기존 코드
```java
@Override
public Optional<Article> save(Article article) {
    jdbcTemplate.update("insert into articles(writer, title, content, created_at) values(?, ?, ?, ?)",
            article.getWriter(),
            article.getTitle(),
            article.getContent(),
            article.getCreatedAt()
    );
    return Optional.of(article);
}
```
- Optional 은 null-safe 하기에 save 시에도 optional 로 감싸서 리턴을 했었다.

### 변경 후 코드
```java
@Override
public Article save(Article article) {
    jdbcTemplate.update("insert into articles(writer, title, content, created_at) values(?, ?, ?, ?)",
            article.getWriter(),
            article.getTitle(),
            article.getContent(),
            article.getCreatedAt()
    );
    return article;
}
```
- Optional 은 반환값이 null 일 수 있는 상황에서 사용하는 것이 좋다.
- save 메서드에서는 항상 Article 객체를 반환하기 때문에 Optional 을 사용할 필요가 없으므로 제거했다.



## 그 외의 것들
- [x] question - article 네이밍 통일
- [x] 구동 오류 해결 -> 메모리 레파지토리를 삭제했는데 삭제한 내용에 대한 커밋을 하지 않았음
- [x] articles 테이블 pk를 userId 에서 id로 변경