# Java Cafe

> 기본적인 CRUD 게시판을 구현하는 것이 목표다.

## 주요 기능

📎 회원 관리
   - 회원 가입
   - 회원 정보 수정
   - 회원 목록 조회
   - 회원 프로필 조회

📎 게시글 관리
   - 게시글 등록
   - 게시글 목록 조회
   - 게시글 상세 보기

### URL

|  HTTP  | URL                    | 기능           |
|:------:|------------------------|--------------|
| `GET`  | /users/sign-up         | 회원 가입 페이지    |
| `POST` | /users                 | 회원 가입        |
| `GET`  | /users                 | 회원 목록 조회     |
| `GET`  | /users/{userId}        | 회원 프로필 정보 보기 |
| `GET`  | /users/{userId}/update | 회원 정보 수정 페이지 |
| `POST` | /users/{userId}/update | 회원 정보 수정     |
| `GET`  | /articles/write        | 글 작성 페이지     |
| `POST` | /articles              | 글 작성         |
| `GET`  | /                      | 글 목록 조회      |
| `GET`  | /articles/{id}         | 글 상세 보기      |


### 배포 URL

[Fia의 CodeSquad](http://43.201.78.83:8080/)
