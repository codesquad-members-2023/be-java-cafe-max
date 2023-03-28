# be-java-cafe
마스터즈 2023 스프링 카페 

## 이번 주 목표
- 하루 1시간 산책하기
- 미션 구현에 급급해하지 말고 하나씩 공부하며 적용하기
- TIL 작성하기
- 백준 4문제 풀기
- Object 9장 읽고 정리
- 스프링 MVC1 강의 듣기
- Java 8 문법 적용해보기

## 2023.03.27(월)
- [x] 1단계 - 회원 가입 기능 구현
  - UserRepository는 추후 db가 변동될 수 있기 때문에 interface로 구현
  - UserRepository는 일단 HashMap으로 구현
  - UserService에서 이미 존재하는 유저 아이디라면 중복 예외 처리
- [x] 1단계 - 회원 목록 조회 기능 구현
- [x] 1시간 산책

### 학습
```
A problem occurred configuring root project 'cafe'.
> Could not resolve all files for configuration ':classpath'.
```
- 스프링부트 3 이상에서는 자바 17이 필요한데 나는 자바 11을 사용해서 일어난 오류
- 스프링부트 버전을 2.7.10을 선택해서 다시 실행했더니 해결    

__Controller import 안됨__    
- 클래스명과 컨트롤러 어노테이션 명이 같으면 생기는 오류
- 클래스명을 바꾸면 정상적으로 @Controller가 import 된다.

__사용자 목록 페이지가 두개?__    
회원가입 기능에서 사용자 추가 완료 후 사용자 목록 페이지(redirect:/users)로 이동한다라고 되어있고,    
회원목록 기능에서 회원 목록 페이지는 static/user/list.html을 사용한다고 되어있다.    
이 페이지들을 나눠야 할 필요성을 못 느껴서 users.html 이라는 페이지 하나만 만들었다.    

__Thymeleaf에서 어떤 요소 onclick 시 이동할 url에 pathvariable 추가하고 싶음__    
찾는중..

### 더 공부할 것
1. ResponseBody, RequestParam, RestController
2. 템플릿 엔진, 머스테치, 타임리프
3. GET, POST

## 2023.03.28(화)
- [x] 회원 프로필 정보 보기 기능 구현
  - 회원이 아닌 사람을 프로필 조회하려고 하면 에러
- [x] 백트래킹 알고리즘 학습
- [x] BoJ 15649 구현
- [x] @ResponseBody, @RestController, @RequestParam, @PathVariable, @ModelAttribute 학습
- [x] GET과 POST의 차이 학습

### 학습
__타임리프를 사용해서 HTML 중복 코드 제거__    
애먹는중..    
    
__Thymeleaf에서 어떤 요소 onclick 시 이동할 url에 pathvariable 추가하고 싶음__    
```html
<td><a href="profile.html" th:href="@{/users/{userId}(userId=${user.id})}" th:text="${user.name}"></a></td>
```
a 태그 안에 이동할 url과 pathvariable, 보여줄 데이터를 명시하여 해결함    
    
__백트래킹__    
알고리즘 기법 중 하나로, 재귀적으로 문제를 풀어나가는데 현재 노드가 제한 조건에 위배되면 __해당 노드를 제외(가지치기, pruning)__ 하고 다음 단계로 나아가는 방식    
이 방법을 통해 모든 경우의 수를 체크하지 않고 필요한 것만 체크하여 전체 풀이시간을 단축할 수 있게 된다.    
백트래킹을 사용하는 알고리즘 중 대표적으로 __DFS__가 있다. DFS를 실행하면서 더 이상 탐색을 진행할 수 없는 상황이면 다시 되돌아가서 탐색을 진행한다.         
__시간복잡도가 보통 O(2^n)__ 이며, 대부분 DP나 그리디 알고리즘 등으로 더 빠르게 해결할 수 있다.

__기본 틀__    
```java
void backtracing(int cnt, int idx) {
    if(cnt == m) {
        return;
    }
    for(int i=idx; i<=n; i++) {
        if(!visited[i]) {
            visited[i] = true; // 상태변화
            backtracking(cnt+1, i);
            visited[i] = false; // 원상복구
        }
    }
}
```
위의 코드를 문제에 맞게 적절히 변경해서 사용하면 된다.    
    
__@ResponseBody__    
서버에서 클라이언트로 응답 데이터를 전송하기 위해 @ResponseBody 어노테이션을 사용하여 자바 객체를 HTTP 응답 
본문의 객체로 변환하여 Json 형태로 클라이언트에 전송한다.    
viewResolver를 무시하고 화면에 출력하겠다.    

__@RestController__    
리턴값에 자동으로 @ResponseBody가 붙게되어 별도 어노테이션을 명시해주지 않아도 HTTP 
응답 데이터(body)에 자바 객체가 매핑되어 전달된다.    
    
__@RequestParam__    
GET 방식으로 url이 전달될 때 담긴 쿼리 스트링(파라미터)를 받아올 수 있다.    
RequestParam에 key값이 존재하지 않을 경우 400대 에러(Bad Request)가 발생한다.    
이를 방지하기 위해서는 default로 "require=false"를 꼭 명시해 줘야 한다.    
생략 가능한 어노테이션이다. 보통 String이나 int같은 단순 타입을 사용할 때에 생략되었다고 생각하면 된다.    
    
__@PathVariable__    
url path로부터 값을 얻는다.    

__@ModelAttribute__    
Http Body 내용과 HTTP 파라미터의 값들을 getter, setter, 생성자를 통해 주입하기 위해 사용한다.    
일반 변수의 경우 전달이 불가능하기 때문에 model 객체를 통해서 전달해야 한다.    
보통 @RequestParam을 사용해서 값을 받고, set을 사용해서 값을 넣어주는 방식을 사용하는데, 이 과정을 자동화시켜준다.    
@ModelAttribute 어노테이션은 생략 가능하다.    

__GET과 POST의 차이__    
GET은 클라이언트에서 서버로 어떠한 리소스로부터 정보를 요청하기 위해 사용되는 메서드이다.    
URL 주소 끝에 파라미터(쿼리 스트링)로 값이 전송된다.    
GET 요청은 파라미터에 다 노출되어버리기 때문에 중요한 정보를 다루면 안된다.    
POST는 클라이언트에서 서버로 리소스를 생성하거나 업데이트하기 위해 데이터를 보낼 때 사용되는 메서드이다.    
전송할 데이터를 HTTP 메세지 BODY 부분에 담아서 서버로 보낸다.    
POST 방식도 데이터를 암호화하지 않으면 body의 데이터를 볼 수 있기 때문에 그렇게 보안이 좋은 방법은 아니다.    

