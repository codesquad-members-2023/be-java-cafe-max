# be-java-cafe
### 마스터즈 2023 스프링 카페 프로젝트

💻 2023.03.27 ~ing

<br>

## Step 1. 회원가입 및 회원 목록 조회 기능 (Done)

----

### 기능요구사항
- [x] 사용자는 회원가입 폼으로부터 입력받은 데이터로 회원가입을 할 수 있어야 한다.
  - [x] 가입하기 페이지에서 회원 가입 폼을 표시한다.
  - [x] 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.
- [x] 사용자는 회원 목록 페이지에서 현재 가입된 전체 회원목록을 조회할 수 있어야 한다.
  - [x] 가입한 회원들의 목록을 출력한다.
- [x] 사용자는 회원 프로필 페이지에서 개별 회원 프로필 정보를 확인할 수 있어야 한다.

<br>

### 프로그래밍 요구사항
- [x] Lombok은 사용하지 않는다.
- [x] 별도의 데이터베이스는 사용하지 않는다.
- [x] 웹 템플릿은 Mustache를 추천한다. 원할 경우 Thymeleaf를 사용해도 무방하다.


<br>

### 기능별 구현요구사항
#### 1. 회원가입 기능
- [x] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션(@Controller)을 매핑한다.
- [x] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가(@PostMapping)하고 URL을 매핑한다.
- [x] User 클래스를 생성해 사용자가 전달한 값을 저장한다.
  - [x] 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메서드를 생성한다.
- [x] 사용자 목록을 관리하기 위해 JCF의 클래스를 활용한다.
- [x] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

#### 2. 회원 목록 기능
- [x] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
- [x] 회원목록 요청(GET 요청)을 처리할 메서드를 추가하고 URL 매핑(@GetMapping)한다.
- [x] Model을 메서드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
- [x] 사용자 목록을 user/list.html로 전달하기 위해 메서드 반환 값을 "user/list"로 한다.
- [x] user/list.html에서 사용자 목록을 출력한다.

<br>

#### 3. 회원 프로필 정보 기능
- [x] user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동한다.
- [x] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
- [x] 회원프로필 요청(Get 요청)을 처리할 메서드를 추가하고 URL 매핑(@GetMapping)한다.
- [x] URL을 통해 전달한 사용자 아이디 값은 @PathVarialbe 애노테이션을 활용해 전달받는다.
- [x] List에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
- [x] user/profile.html에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

<br>

#### 4. HTML 중복 제거
- [x] 각 html의 header, navigation bar, footer 부분에서 중복 코드를 제거한다.
  - [x] index.html 중복 코드 제거
  - [x] /user/form.html 중복 코드 제거

<br>

## Step 2. 글쓰기 기능 

---

### 기능요구사항
- [x] 모든 사용자는 글 쓰기 기능으로 게시글을 작성할 수 있어야 한다.
- [x] 모든 사용자는 글 목록 조회 기능으로 게시글 목록을 볼 수 있어야 한다.
- [x] 모든 사용자는 게시글 상세 내용을 볼 수 있어야 한다.
- [x] (선택) 입력된 게시글을 수정할 수 있어야 한다.

<br>

### 프로그래밍 요구사항
- [ ] WebMVCConfigurer 적용
  - [ ] Adapter가 deprecated 된 이유와 해결책에 대해 생각 및 해결

<br>

### 기능별 구현요구사항
#### 1. 글 쓰기 기능
- [x] 게시글 기능 구현을 담당할 ArticleController를 추가하고 애노테이션(@Contoller)을 매핑한다.
- [x] 게시글 작성 요청(POST 요청)을 처리할 메서드를 추가하고 매핑(@PostMapping)한다.
- [x] Article 클래스를 생성해 사용자가 전달한 값을 저장한다.
- [x] 게시글 목록을 관리하는 컬렉션 클래스를 생성한 후 Article 인스턴스를 저장한다.
- [x] 게시글 추가 완료 후 메인페이지("redirect:/")로 이동한다.

<br>

#### 2. 글 목록 조회 기능
- [x] 메인 페이지(요청 URL : "/")를 담당하는 Controller의 메서드에서 게시글 목록을 조회한다.
- [x] 조회한 게시글 목록을 Model에 저장한 후 View에 전달한다.
  - [x] 게시글 목록은 게시글 작성시 생성한 컬렉션을 그대로 전달한다.
- [x] View에서 Model을 통해 전달한 게시글 목록을 출력한다.
  - [x] 게시글 목록 구현 과정은 사용자 목록을 구현하는 html 코드를 참고한다.

<br>

#### 3. 게시글 상세보기 기능
- [x] 게시글 목록의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.
  - [x] 게시글 상세 페이지 접근 URL은 "/post/{index}"와 같이 구현한다.
  - [x] 게시글 객체에 Id 인스턴스 변수를 추가하고 새로운 게시글마다 증가하도록 구현한다.
- [x] Controller에 상세 페이지 접근 메서드를 추가하고 URL은 /article/{index}로 매핑(@GetMapping)한다. 
- [x] 해당하는 데이터를 조회한 후 Model에 저장해 /post/show에 전달한다.
- [x] /post/show에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.
- [x] 게시글 수정 후 상세보기 페이지("redirect:/post/{index}")로 이동한다.

<br>

#### 4. 회원정보 수정 기능
- [ ] 회원목록에서 회원가입한 사용자의 정보를 수정할 수 있다.
  - [ ] 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
  - [ ] 비밀번호가 일치하는 경우에만 수정 가능하다.
- [x] 개인정보수정 페이지는 "/user/{id}/form"와 같이 구현한다.
  - [x] URL 매핑 시 @PathVarialbe 애노테이션을 활용해 인자 값을 얻는다.
  - [x] {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
- [x] UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.
- [x] DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.

<br>

Step 3.


<br>

## Step 4. 로그인
---

### 기능요구사항
- [x] 로그인, 로그아웃 기능 정상적으로 동작
- [x] 로그인 상태 : 상단메뉴 로그아웃, 개인정보수정 표시
- [x] 로그아웃 상태 : 상단메뉴 로그인, 회원가입 표시

<br>

### 프로그래밍 요구사항
- [x] Spring MVC에서 메서드 인자로 HttpSession을 이용해서 로그인 구현
- [x] 로그인 성공하는 경우 HttpSession에 로그인 정보 추가
  ```java
    session.setAttribute(“sessionedUser", user);

- [x] Spring Security 같은 별도 라이브러리 사용 금지
- [ ] API가 아닌 템플릿 기반으로 구현

<br>

### 추가 구현요구사항
#### 개인정보 수정 기능 추가
- [ ] 로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다
  - [ ] HttpSession에 저장된 User 데이터를 가져온다
  - [ ] 로그인한 사용자와 수정하는 계정의 id가 같은 경우에만 수정하도록 한다
  - [ ] 다른 사용자의 정보를 수정하려는 경우 에러 페이지를 출력한다
- [] 이름, 이메일만 수정할 수 있으며 사용자 아이디는 수정할 수 없다
- [x] 비밀번호가 일치하는 경우에만 수정 가능하다


<br>

## What I learn & Trouble-Shooting

---

### ✍ What I learn

(1) resources : `resources` 폴더에서는 Application에 필요한 데이터나 파일을 관리하며 HTML에서 불러와 사용할 수 있다. Spring boot Application이 시작될 때 클래스 경로 상의 Resource가 로드된다.
- static : SpringMVC에서 정적 파일을 저장하는 기본적인 디렉토리. `static` 폴더에는 HTML에서 사용하는 정적 파일을 포함하며 서버에서 이를 직접 제공한다. static 폴더의 내용은 브라우저에서 캐시될 수 있으며, 동적으로 생성되지 않는 콘텐츠를 저장하는 데 적합하다.
- templates : `templates` 폴더는 동적인 콘텐츠를 생성하는 데 사용된다. HTML 템플릿을 포함하며, 서버에서 템플릿 엔진을 사용하여 템플릿 렌더링하여 동적 HTML 페이지를 생성한다. 예를 들어, 브라우저에서 요청한 데이터를 기반으로 동적으로 HTML 페이지를 생성하려면 템플릿 폴더에서 해당 HTML 파일을 찾아 렌더링 한다.
>static 폴더 : 정적 자원(파일) 제공
>
>templates 폴더 : 동적 HTML 페이지 생성

<br>

(2) 템플릿 엔진 : 서버측에서 HTML 등의 마크업 언어를 동적으로 생성하기 위한 도구. 서버에서 데이터를 받아와서 동적으로 마크업을 생성하며, 이를 클라이언트에게 응답으로 전달. 자바에서는 주로 Thymeleaf, JSP, Mustache 등을 사용한다.

- ResourceHttpRequestHandler : SpringMVC의 내부 클래스로, 요청된 URL과 정적 리소스 파일의 매핑을 처리. 즉, 요청된 URL이 정적 리소스 파일과 매핑되면 ResourceHttpRequestHandler가 이를 처리하여 해당 파일을 반환한다.


- WebMvcConfigurer : SpringMVC 구성을 변경하거나 확장하기 위한 인터페이스. 이 인터페이스를 구현하여 addResourceHandlers 메서드를 오버라이드하면 ResourceHttpRequestHandler를 구성할 수 있다. 따라서 WebMvcConfigurer를 사용하여 정적 파일의 경로나 동작 방식을 수정할 수 있다.

<br>

(3) 어노테이션
- `@Controller` : 해당 클래스가 Controller라는 걸 Spring이 알 수 있도록 어노테이션 설정한다.

- `@GetMapping` : HTTP GET 요청 메서드를 정의할 때 사용. 요청 URL을 지정하여 해당 URL로 GET 요청이 오면 메서드가 실행되어 응답을 반환한다.
  `@GetMapping` 어노테이션에서 반환하는 문자열은 뷰 이름이 되며, 이 이름은 ViewResolver에 의해 해석된다.
  * ViewResolver : Spring MVC에서 뷰를 찾는 역할. Controller에서 return한 뷰 이름을 실제 뷰 경로로 변환한다.


- `@PostMapping` : HTTP POST 요청 메서드를 정의할 때 사용. 클라이언트로부터 전송된 데이터를 서버로 전송하고 처리할 수 있다.


- `@PathVariable` : 요청 URL에서 경로 변수를 추출하여 해당 값을 컨트롤러 메서드의 파라미터에 매핑. 즉, URL 경로에 있는 변수를 가져와서 사용한다.
  <URL 파라미터 넘기는 방법>
  1. @PathVariable>> th:href="@{/forum/modify/{id}(id=${article.id})} : /뒤에 '숫자'만 붙음
  ex)localhost:8080/forum/modify/1
  2. QueryString>> th:href="@{/forum/view(id=${forum.id})} : 뒤에 '?id=숫자'가 붙음
  ex)localhost:8080/forum/modify?id=1


- `@ModelAttribute` : `Controller`에서 `View`로 데이터를 전달할 때, 해당 메서드의 리턴값을 `Model` 속성으로 자동으로 추가. 코드량을 줄일 수 있고 코드의 가독성을 높일 수 있다.

<br>

(4) JCF : Java Collection Framework의 약자. 자바가 제공하는 도구로, 데이터 구조를 다루는 데 필요한 클래스들의 라이브러리. List, Set, Map 등이 포함되어 있다.

<br>

(5) Optional.ofNullable() : 일반적으로 Map에서 Key를 기반으로 검색한 결과를 Optional 객체로 반환할 때 사용되는 메서드. 만약 해당 key를 가진 값이 Map에 존재한다면 해당 값을 담은 Optional 객체를 반환하고, 그렇지 않은 경우 빈 Optional 객체(null)를 반환한다.
- .get() : Optional 객체 값 반환할 때 쓰이는 메서드. Optional 객체에 값이 있을 때 해당 값을 반환하고 값이 없는 경우 'NoSuchElementException'을 발생시킨다. 사용 시 따로 예외 처리를 해줘야하므로 주의 필요.
- .orElse(null) : Optional 객체 값 반환할 때 쓰이는 메서드. Optional에 값이 있으면 해당 값을 반환하고, 없으면 null을 반환한다. 따로 예외 처리를 해주지 않아도 된다.

<br>

(6) 동시성 문제 : 2개 이상의 스레드, 혹은 세션에서 동일한 가변 데이터를 동시에 제어할 때 나타나는 문제. 예를 들어 동시에 회원 가입을 하는 경우, 동시에 글을 작성하는 경우 id를 부여하는 과정에서 발생할 수 있다.  
- ConcurrentHashMap<>() : HashMap<>()과 다르게 thread-safe
- AtomicLong : long 자료형을 가지고 있는 Wrapping class로 thread-safe

<br>

(7) SimpleJdbcInsert : Spring JDBC에서 제공하는 클래스 중 하나. SQL INSERT 문을 간단하게 실행할 수 있도록 도와주는 유틸리티 클래스다.  지정된 테이블에 데이터를 삽입할 때 사용한다.

SimpleJdbcInsert를 사용하면 쿼리문을 직접 작성하지 않고도 다음과 같은 기능을 수행할 수 있다.

1. 삽입할 데이터를 Java 객체로 전달하여 INSERT 문 실행
2. 테이블 이름과 자동 생성된 key 컬럼 설정
3. 자동 생성된 key 값 반환

SimpleJdbcInsert 객체 생성 시 JdbcTemplate 객체를 생성자 파라미터로 전달해야 한다. 그 다음 withTableName() 메서드를 이용하여 테이블 이름을 설정하고, usingGeneratedKeyColumns() 메서드를 이용하여 자동 생성된 key 컬럼을 설정한다.

```java
@Override
    public void join(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USER_TB").usingGeneratedKeyColumns("ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USERID", user.getUserId());
        parameters.put("PASSWORD", user.getPassword());
        parameters.put("NAME", user.getName());
        parameters.put("EMAIL", user.getEmail());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
    }
```

이후 execute() 메서드를 호출하여 INSERT 문을 실행하면 해당 테이블에 새로운 레코드가 삽입되며 자동 생성된 key 값을 반환받을 수 있다.

- withTableName() : SimpleJdbcInsert 객체가 삽입할 데이터가 저장될 테이블의 이름을 설정.


- usingGeneratedKeyColumns(key column name) : key column을 설정하면 새로운 레코드가 삽입될 때마다 해당 key column에 자동으로 증가되는 값이 부여되며, 이 값을 반환 받아서 자동으로 생성된 key 값을 사용할 수 있다.
  - 단, AUTO_INCREMENT 처럼 key-column이 자동으로 증가되도록 설정되어 있어야 한다. 만약 usingGeneratedKeyColumns()를 사용하여 key-column을 설정하였는데 해당 컬럼이 AUTO_INCREMENT 같은 속성이 없다면, executeAndReturnKey() 메서드를 실행하는 과정에서 **`org.springframework.dao.DataIntegrityViolationException`**예외가 발생한다.

  ⇒ usingGeneratedKeyColumns() 를 사용하여 자동 생성된 key 값을 반환 받으려면 해당 key-column이 자동으로 증가되도록 설정되어 있어야 하며, 이를 위해 일반적으로 AUTO_INCREMENT 속성을 사용한다.


- jdbcInsert.withTableName("USER_TB").usingGeneratedKeyColumns("ID") : jdbcInsert 객체에 USER_TB 테이블을 사용하고, ID 컬럼을 자동 생성 키로 사용하도록 설정.


- Map<String, Object> parameters : Map<column name type, column value type>으로 구성되며 comumn value type에 상관 없이 모두 저장하기 위해 두번째 인자 타입을 Object로 설정한다. Map을 생성한 이유는 아래 MapSqlParameterSource를 사용하기 위해서다.


- MapSqlParameterSource :  Map과 SqlParameterSource 인터페이스를 구현한 클래스.  Map에 저장된 필드 값들을 매개변수로 전달받아 SQL 쿼리에서 사용될 수 있는 형식으로 변환한다.
  - SqlParameterSource : Spring 프레임워크에서 제공하는 인터페이스로, SQL 쿼리의 매개변수 값을 지정하는 데 사용한다.


- executeAndReturnKey() : 매개변수로 전달된 객체의 저장 값을 이용해 INSERT 문을 실행하고, 자동으로 생성된 Primary Key 값을 반환해주는 메서드. 이때 Primary Key 값은 usingGeneratedKeyColumns()에서 지정한 컬럼 값으로 반환한다.


- user.setId(key.longValue()) : 반환된 key 값을 long type으로 변환하여 article 객체의 ID 값으로 갱신한다.

<br>

(8) query(), ResultSet, RowMapper

```java
@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

		...

		@Override
    public Optional<Article> findById(long id) {
        List<Article> wantedPost = jdbcTemplate.query("SELECT * FROM ARTICLE_TB WHERE ID = ?", articleRowMapper(), id);
        return wantedPost.stream().findAny();
    }

		public RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("ID"));
            article.setTitle(rs.getString("TITLE"));
            article.setAuthor(rs.getString("AUTHOR"));
            return article;
        };
    }

		...

}
```

- qeury(SQL String, callback function)
  - SQL String : 해당 SQL문을 실행하여 ResultSet 객체을 생성하고, 두 번째 인자에 해당하는 callback 함수의 매개변수로 ResultSet을 전달
  - callback function : ResultSet 데이터를 전달받아 원하는 객체(위의 경우 Article 객체)로 매핑하는 역할
- ResultSet : SELECT문의 결과를 저장하는 객체.
- RowMapper<T> :  jdbcTemplate에서 ResultSet을 행별로 매핑하는 데 사용하는 인터페이스.
- resultSet.getXXX(int columnIndex or String coumnName)
  - getXXX : 가져오려는 열(column) 데이터의 Type
  - int columnIndex or String coumnName : 가져올 열(column)의 인덱스 혹은 이름.
- rowNum : query() 메서드의 SQL문을 실행했을 때 ResultSet 객체의 행(row) 개수만큼 callback function이 호출되는데, 이때 행의 인덱스로 사용되는 변수. ResultSet의 각 행을 반복하면서 callback function이 호출될 때마다 rowNum의 값이 1씩 증가한다.

<br>

❓ ~ing

Q. @RequestMapping과 @GetMapping의 차이




<br>

### 🛠 Trouble-Shooting
🚫TroubleShooting 1. Cannot resolve MVC view `user/form`

```java
package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/create")
    public String signIn() {
        return "user/form";
    }
}
```

위 코드는 내가 UserController에 작성했던 코드다. 이전에 간단한 게시글 목록 페이지를 만들 때 return 값으로 html의 경로를 반환했던 것이 기억나 이렇게 작성했다. 이때 나는 템플릿 엔진을 사용하지 않은 채 return 값을 `user/form`으로 반환하였는데 이 부분이 오류를 일으켰다.

🧐 예상치 못했던 오류가 발생한 이유는 템플릿 엔진의 역할을 제대로 알지 못했기 때문이다.  템플릿 엔진, 예를 들어 Thymeleaf를 사용할 경우 ViewResolver는 자동으로 뷰 이름 앞에 `templates/`를, 뒤에 `.html`을 추가하여 실제 뷰 파일의 경로를 결정한다. 다시 말해서, “user/form”만 반환해도 경로가 `/templates/user/form.html`로 변환된다.

이 원리를 모른 채 템플릿 엔진을 사용하지 않고 냅다 `user/form`을 반환했으니 당연히 잘못된 경로로 인식되어 오류가 난 것이다.

🔨 템플릿 엔진을 사용하는 방법은 아래와 같다.

1. 템플릿 엔진 선택 및 설치
2. 템플릿 엔진 의존성 추가 : 사용 중인 빌드 도구의 설정 파일에 의존성 추가

   -Grade : build.gradle

   -Maven : pom.xml

3. 템플릿 엔진 설정 : `.properties` 파일이나 `.yml` 파일 등을 사용하여 설정 추가

내 경우 템플릿 엔진으로 Thymeleaf를 사용했고 build.gradle 파일과 application.yml 파일에 각각 의존성과 설정을 추가했다.

``` java
//build.gradle -Thymeleaf 의존성 주입

dependencies {
  ...
  implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  ...
}
```

```java
//application.yml - Thymeleaf 템플릿 엔진 설정

spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    cache : false
```

<br>

🚫 TroubleShooting 2. UserJoinDTO 생성 후 회원가입에서 오류 발생

- 1 errors<EOL>Field error in object 'userJoinDTO' on field 'id': rejected value [null];
- codes [\[typeMismatch.userJoinDTO.id\](http://typemismatch.userjoindto.id/),\[typeMismatch.id\](http://typemismatch.id/),typeMismatch.long,typeMismatch]; arguments
- ConversionFailedException: Failed to convert from type [null] to type [long] for value 'null'; nested exception is java.lang.IllegalArgumentException: A null value cannot be assigned to a primitive type

🧐 입력값을 전달하는 과정에서 문제가 생겼다. 로그를 토대로 검색해보니 userJoinDTO 객체의 id 필드에서 null 값을 받아 Long 타입으로 변환하는 과정에서 발생한 오류라고 한다.

내가 User 객체에서는 id  필드를 Long으로 선언해놓고 UserJoinDTO 객체에서는 id 필드를 long으로 받아서 생긴 문제였다. Long 타입은 NULL 값이 가능한데 long 타입은 불가능하니 typeMismatch가 난 것이다.

🔨 UserJoingDTO의 id 필드도 Long 타입으로 선언하니 문제 해결!

```java
 public class UserJoinDTO {

    private final Long id;

    ...

    public UserJoinDTO(Long id, String name, String userId, String password, String email) {
        this.id = id;

        ...

    }

    public Long getId() {
        return id;
    }

		...

}
```

<br>

## 📝 동작정리
※ 동작 헷갈려서 정리

### (1) Article이 저장되는 과정

1. [글쓰기] 버튼 클릭

```java
// ArticleController
@Controller
public class ArticleController {

   ...

    @GetMapping("/post/write")
    public String postForm() {
        return "post/form";
    }

	...

}
```

/post/write URL로 GET 요청이 보내지면 form.html 템플릿이 클라이언트 측에 렌더링 되어 보여진다.

1. 글 작성 후 [등록] 버튼 클릭

```javascript
<!--form.html-->
<form class="submit-write" method="post" action="/post/write">
	<div class="text-box">
		<div class="comment-writer-container">
			<p class="post-title">제목</p>
		</div>
		<div class="form-group">
			<label for="title"></label><textarea class="form-control" id="title" name="title" placeholder="제목을 입력하세요"></textarea>
		</div>
	</div>

	<div class="text-box">
		<div class="comment-writer-container">
			<p class="post-content">내용</p>
		</div>
		<div class="form-group">
			<label for="content"></label><textarea class="form-control" id="content" name="content" placeholder="내용을 입력하세요"></textarea>
		</div>
	</div>
	<input class="post-write-btn" type="submit" value="등록"/>
</form>
```

- `<form>` 태그 : 사용자가 입력한 값을 서버로 전송하기 위해 사용
    - action 속성이 /post/wirte로 설정되어 있고, method 속성이 post로 설정되어 있으므로 입력된 title과 content가 POST 방식으로 /post/write 주소로 전송된다.

등록 버튼을 누르면 HTML은 다음과 같은 요청을 서버에 보낸다.

(❗ 아래는 예시일 뿐 정확한 요청은 아님)

```bash
#HTML 요청(request) 예시
POST /post/write HTTP/1.1
Content-Type: application/x-www-form-urlencoded

title=제목입니다.&content=내용입니다.
```

1. ArticleDto 객체 생성 후 articleService.write() 메서드 호출

```java
// ArticleController
@Controller
public class ArticleController {

   ...

    @PostMapping("/post/write")
    public String writePost(@ModelAttribute final ArticleDTO articleDto) {
        articleService.write(articleDto);
        return "redirect:/";
    }

	...

}
```

- @ModelAttribute : 컨트롤러 메서드의 매개변수로 선언된 객체를 자동으로 바인딩하여 모델에 추가한다. 이때 해당 요청(request)의 body에 포함된 값을 메서드의 인자로 전달하는데, 요청 파라미터(name)와 ArticleDTO 필드의 이름이 일치해야 자동 바인딩이 동작한다. 만약 서로 일치하지 않으면 수동으로 바인딩 해주어야 한다.
  - 바인딩
  - 모델

1. articleService.write() 메서드 동작 및 articleRepository.save() 메서드 호출

```java
@Service
public class ArticleService {

		...

    public void write(final ArticleDTO articleDto) {
        Article article = Article.toArticle(articleDto);
        articleRepository.save(article);
    }

		...

}
```

ArticleDTO 인스턴스를 Article 객체로 변환한 후 articleRepository.save() 메서드의 매개변수로 전달한다.

1. articleRepository.save() 메서드 동작

```java
@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ARTICLE_TB").usingGeneratedKeyColumns("ID");

        Map<String, Object> articleParameters = new HashMap<>();
        articleParameters.put("TITLE", article.getTitle());
        articleParameters.put("CONTENT", article.getContent());
        articleParameters.put("AUTHOR", article.getAuthor());
        articleParameters.put("CREATED_AT", Timestamp.valueOf(LocalDateTime.now()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(articleParameters));
        article.setId(key.longValue());
    }

		...

}
```

article 객체에 저장된 값을 데이터베이스에 저장

