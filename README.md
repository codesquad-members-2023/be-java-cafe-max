# be-java-cafe
마스터즈 2023 스프링 카페 

## 구현 내역
- [x] 로그인
- [x] 세션으로 권한 설정
- [x] 글쓰기/수정/삭제
- [ ] 검증 및 예외 처리

## 구현 과정
### 로그인
UserController.java
```java
@PostMapping("/signIn")
public String signIn (@RequestParam("userId")String id, @RequestParam("password")String password,
        HttpSession session){
        User user = userService.findById(id);
        userService.login(user,password);
        session.setAttribute("sessionId",new LoginSessionDto(user.getUserId(),user.getName()));
        return "redirect:/";
        }
```
UserService.java
```java
public boolean login(User loginUser,String password){
        if(!loginUser.checkPassword(password)){
            throw new LoginFailedException("로그인 실패");
        }
        return true;
    }
```
입력한 id를 통해 User 객체를 찾아오고 그 객체에서 입력한 패스워드와 비교

id가 존재 하지 않으면 로그인 실패 예외 전에 findById의 예외가 먼저 발생해 로그인 실패예외가 터지도록 수정필요합니다.

## 세션으로 권한 설정 - 게시판 부분 
### 글 수정,삭제 권한
```java 
    public boolean checkLogin(HttpSession session){
        if(session.getAttribute("sessionId") == null) {
            throw new DeniedAccessException("로그인 한 유저만 접근가능.");
        }
        return true;
    }
```
로그인 하지 않으면 게시글 접근막는 메서드 * true 값이 아니면 예외발생
```java
    public boolean checkIdentity(String id ,HttpSession session){ // 머스테치에서 버튼 숨기기 위해 반환값이 필요했음
        LoginSessionDto userSession = (LoginSessionDto) session.getAttribute("sessionId");
        return id.equals(userSession.getId());
    }
```
세션에 등록되어있는 아이디와 게시글의 아이디를 비교하는 메서드
머스테치에서 버튼을 숨기기 위해 값을 전달 할 필요가 있어서 예외 없이 사용
```java
    public boolean checkAuth(String id ,HttpSession session){
            if(checkLogin(session)&&checkIdentity(id,session)){
                return checkIdentity(id,session);
            }
        throw new DeniedAccessException("작성자만 수정 가능합니다.");
    }
```
로그인 여부와 게시글의 작성자 여부를 판단하는 메서드 * true 값이 아니면 예외발생

## 검증 및 예외 처리
### Valid 어노테이션 사용
```java
implementation 'org.springframework.boot:spring-boot-starter-validation'
```
@Valid 로 검증을 하는데 검증 식과 다르면 BindException 발생해서

GlobalExceptionHandler 클래스 에서 @ControllerAdvice 를 이용해 BindException 을 전역으로 처리

```java
@ExceptionHandler(BindException.class)
    public String BindException(BindException e, RedirectAttributes model , HttpServletRequest request){
            List<FieldError> fieldError = e.getBindingResult().getFieldErrors();
            String uri = request.getRequestURI();
            String[] uriSplit = uri.split("/");
                for (int i = 0; i<e.getBindingResult().getAllErrors().size() ; i ++){
                    model.addFlashAttribute(fieldError.get(i).getField(),(fieldError.get(i).getDefaultMessage()));
                }
               if(uriSplit[2].equals("create")) {
                   return "redirect:/user/signup";
               } else if (uriSplit[2].equals("write")) {
                   return "redirect:/article/question";
               } else if((uriSplit[1]+uriSplit[2]).equals("articleupdate")){
                   return "redirect:/article/update/"+uri.split("/")[3];
               }
               else{
                   return "redirect:/user/update/"+uri.split("/")[3];
               }
    }
```
BindException 이 발견되면 에러가 발견된 필드의 에러메시지를 가져와 머스테치에 출력시켜 줍니다.

처음에는 BindException 을 extends 하는 커스텀 예외를 만들고 싶었지만 BindException 의  BindingResult 를

Valid 어노테이션을 사용 한 Dto 에서 생성 해 줄 수  없어 커스텀 예외를 만들지 못하고 전역으로 처리하게 되었고 메서드를 분리하지 못해 

페이지의 uri 를 받아 uri 에 따라 동작하게 만들었습니다. 

오랜시간 고민을 했지만 하드코딩 식으로 밖에 구현하지 못해 아쉬운 부분입니다.

---
나머지 예외 처리는 에러 메시지와 함께 에러페이지를 불러오도록 하였습니다. 

## 기타 리팩토링

```java
    @Override
    public Article findByIdx(int idx) {
        return jdbcTemplate.queryForObject(
                "SELECT IDX , ID , WRITER , TITLE , CONTENTS , DATE FROM ARTICLES WHERE IDX = ?", rowMapper(), idx
        );
        
        
///////////////////////////아래는 변경 후////////////////////////////////////////////////////////////////////////////////

@Override
    public Optional<Article> findByIdx(int idx) {
        List<Article> articles = jdbcTemplate.query(
        "SELECT IDX , ID , WRITER , TITLE , CONTENTS , DATE FROM ARTICLES WHERE IDX = ?", rowMapper(), idx
        );
        return articles.stream().findFirst();
        }

```
Optional 을 사용하게 된 이유는 null 예외를 방지하고 값이 없을 때 생기는 예외를 처리하기 편하게 하기위해 사용했습니다.

queryForObject -> query 로 변경한 이유는 queryForObject 는 EmptyResultDataAccessException 가 발생하는 경우가 있어,

try & catch 로 해당 예외를 잡아주어야 했는데 try catch 를 쓰면 코드가 길어지고 catch 블록에서 Optional.empty 를 반환 하는것은 의미 없는 행위라고 생각되어 

EmptyResultDataAccessException 예외가 발생하지 않는 query 방식을 사용하게 되었습니다. 

## 소감 
이번 주 미션내용에 대한 구현은 어렵지 않았는데 그동안 하지 않았던 예외처리를 하느라 애를 많이 먹었다.

예외처리를 처음 해보다 보니깐 간단한 처리를 구현 하는데에도 오랜 시간이 걸렸고 깔끔하지 못했던것 같다.

구현하는데 시간이 부족해 항상 테스트 코드를 짜지 못해 숙제만 쌓여가는 기분이다.

다음주 부터는 진짜 간단한 테스트여도 조금이라도 테스트 코드를 짜 봐야겠다. 







