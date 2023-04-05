# 미션 과정

## 설정

1. DB Connection 설정 – application.properties에 추가
```
# spring- h2 연결
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:~/spring-qna-db;DB_CLOSE_ON_EXIT=FALSE // Embedded mode(내장모드)
spring.datasource.username=sa
spring.datasource.password=

# h2 database web으로 확인
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

2. 의존성 설정 - build.gradle에 추가

```
implementation group: 'org.springframework.boot', name: 'spring-boot-starter-jdbc'
implementation group: 'com.h2database', name: 'h2', version: '2.0.206'
```
3. http://localhost:8080/h2-console 입력

![스크린샷 2023-04-04 오후 4.20.31.png](..%2FDesktop%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-04-04%20%EC%98%A4%ED%9B%84%204.20.31.png)
JDBC URL : jdbc:h2:~/spring-qna-db (jdbc:h2:~/test로 입력시 not found 에러 발생!)
application.properties의 spring.datasource.url와 일치해야 함!

## 문제 해결
1. schema.sql, data.sql 경로 에러

➡️ application.properties에 경로 추가!
> 의존성 추가에 Implementation으로 했다면 초기화할 schema.sql, data.sql 경로를 입력해야 한다. (runtimeOnly로 설정시 필요 없음)
```
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
```

2. CREATE TABLE user 에러 발생

user는 h2 데이터베이스 예약어이기 때문에 식별자(identifier)로 사용될 수 없다.

 ➡️ 1) user를 "user"로 변경하기 or 2) user를 users로 변경하기
 ➡️ 특정 데이터베이스에서는 "name"도 식별자로 사용! "name"으로 변경 
 
3. data.sql 파일을 실행 실패 에러

'script' must not be null or empty

➡️ data.sql에 코드 추가

4. CREATE TABLE "user" 에러 발생

'schema.sql' 파일을 실행하여 'user' 테이블이 이미 생성되었다. 'user' 테이블을 다시 생성하려고 시도하면 이미 존재하기 때문에 에러가 발생했다.

➡️ DROP TABLE IF EXISTS "user"; CREATE TABLE "user"으로 변경!

5. 프로그램이 꺼져도 데이터베이스 유지하기!

 * schema.sql 내용 변경
 ```
 DROP TABLE IF EXISTS users
 CREATE TABLE users
 
 // 다음과 같이 변경
 CREATE TABLE IF NOT EXISTS users
 ```
 * data.sql 삭제, application.properties의 경로 삭제 

6. 홈 화면에서 게시글 클릭 시 url 에러 발생

```
http://localhost:8080/articles/{id} 

http://localhost:8080/articles/    // id값에 null이 들어감
```
➡️ Article에 생성자 추가
```
public Article(String writer, String title, String contents, Long id) {
        this(writer, title, contents);
        this.id = id;
    }
```
➡️ articleRowMapper()에서 setId로 id 설정
```
private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"));
            article.setId(rs.getLong("id"));  // id 설정 추가
            return article;
        };
    }
```