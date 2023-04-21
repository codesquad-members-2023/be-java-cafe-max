스프링 카페 4, 5단계 - 로그인 및 게시물 권한부여
=

## 1. 로그인 및 로그아웃 기능 구현

- [x] 로그인 및 로그아웃 시 각각의 상황에 맞게 상단바 출력
- 
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

- 기존 DTO 에서의 writer input 을 삭제했으니 현재 로그인 중인 사용자의 userId를 넣어준다.
- 현내 로그인 된 사용자는 HttpServletRequest 를 통해 세션의 userId 값으로 알 수 있다.

## 4. 게시글 수정하기

- [x] 수정하기 폼 과 수정하기 기능은 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능
- [x] 상황에 따라 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동
- [x] putmapping을 사용하여 매핑

### 수정 및 삭제 불가 메세지
![image](https://user-images.githubusercontent.com/118447769/233599013-57f77192-440c-4d3d-9f37-655ba316fc94.png)  

### 수정 시 기존에 작성된 제목 및 내용 로딩
![image](https://user-images.githubusercontent.com/118447769/233599284-2d2f33e8-0698-49c1-914d-220642a966ce.png)  



## 5. 게시글 삭제하기

- [x] 삭제하기는 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능
- [x] 상황에 따라 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동
- [x] deletemapping을 사용하여 매핑