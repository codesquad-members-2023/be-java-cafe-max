스프링 카페 3단계 - DB에 저장하기
=

## 1. H2 데이터베이스 연동
- [x] H2 데이터베이스 의존성을 추가하고 연동한다.
- [x] Spring JDBC를 사용

## 2. 게시글 데이터 저장하기
- [x] Article 클래스를 DB 테이블에 저장할 수 있게 구현
- [x] Article 테이블이 적절한 PK를 가지도록 구현
- [x] 전체 게시글 목록 데이터를 DB에서 조회하도록 구현
- [x] 게시글의 세부 내용을 DB에서 가져오도록 구현

![image](https://user-images.githubusercontent.com/118447769/231973961-51e82507-0f8c-4168-a797-591e453b2027.png)  

### 테이블 생성

```h2
CREATE TABLE IF NOT EXISTS users
(
    user_id    VARCHAR(64)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    user_name  VARCHAR(64)  NOT NULL,
    user_email VARCHAR(64)  NOT NULL,
    PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS articles
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    writer     VARCHAR(64)  NOT NULL,
    title      VARCHAR(64)  NOT NULL,
    content    VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    PRIMARY KEY (id)
    );

```
- DTO와 동일한 column을 생성하였으며 PK는 user의 경우 가입시 입력한 userId, articles의 경우 id로 지정
- qna/form 에서 POST 했던 request DTO를 테이블에 저장
- 레파지토리 연결만 메모리에서 h2로 변경했기에 기능은 동일

## 3. 사용자 정보 DB에 저장
- [x] 회원가입을 통해 등록한 사용자 정보를 DB에 저장

![image](https://user-images.githubusercontent.com/118447769/231975427-0473f7ce-c88d-45eb-8c5b-ff1c99f6bf16.png)  

- articles와 동일하게 form에서 작성한 request DTO를 저장

## 4. 배포
