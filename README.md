# be-java-cafe
마스터즈 2023 스프링 카페 

배포: http://ec2-13-125-213-166.ap-northeast-2.compute.amazonaws.com:8080


# 미션 구현 목록
## URL

|                       URL                        | HTTP Method |    기능     |
|:------------------------------------------------:|:-----------:|:---------:|
|                      **\/**                      |     GET     | 게시글 목록 조회 |
|                 **\/questions**                  |     GET     | 게시글 작성 폼  |
|                   \/questions                    |    POST     |  게시글 작성   |
|                 \/questions/{id}                 |     GET     | 게시글 상세 조회 |
|              \/questions/{id}/edit               |     GET     | 게시글 수정 폼  |
|              \/questions/{id}/edit               |    POST     |  게시글 수정   |
|             \/questions/{id}/delete              |    POST     |  게시글 삭제   |
|            **\/answers/{articleId}**             |    POST     |   댓글 작성   |
|  \/questions/{articleId}/answers/{replyId}/edit  |     GET     |  댓글 수정 폼  |
|  \/questions/{articleId}/answers/{replyId}/edit  |    POST     |   댓글 수정   |
| \/questions/{articleId}/answers/{replyId}/delete |    POST     |   댓글 삭제   |
|                   **\/users**                    |     GET     | 회원 목록 조회  |
|                  \/users/create                  |     GET     |  회원 가입 폼  |
|                  \/users/create                  |    POST     |   회원 가입   |
|                 \/users/{userId}                 |     GET     | 회원 프로필 조회 |
|                   **\/login**                    |     GET     |   로그인 폼   |
|                     \/login                      |    POST     |    로그인    |
|                     \/logout                     |     GET     |   로그아웃    |


## ERD
### 회원 user
|     Key     |  이름  |    Field    |    Type     |   NULL   |     Extra      |
|:-----------:|:----:|:-----------:|:-----------:|:--------:|:--------------:|
| primary key | 회원번호 | customer_id |   bigint    | NOT NULL | auto_increment |
|             | 아이디  |   user_id   | varchar(16) | NOT NULL |                |
|             | 비밀번호 |  password   | varchar(32) | NOT NULL |                |
|             |  이름  |    name     | varchar(16) | NOT NULL |                |
|             | 이메일  |    email    | varchar(64) | NOT NULL |                |
|             |  삭제  |   deleted   | varchar(1)  |          |                |


### 게시글 article
|     Key     | 이름  |    Field    |     Type     |   NULL   |     Extra      |
|:-----------:|:---:|:-----------:|:------------:|:--------:|:--------------:|
| primary key | 번호  |     id      |    bigint    | NOT NULL | auto_increment |
|             | 글쓴이 |   writer    | varchar(16)  | NOT NULL |                |
|             | 제목  |    title    | varchar(32)  | NOT NULL |                |
|             | 내용  |  contents   | varchar(255) | NOT NULL |                |
|             | 생성일 | created_at  |   datetime   | NOT NULL |                |
|             | 수정일 | modified_at |   datetime   |   NULL   |                |
|             | 포인트 |   points    |    bigint    | NOT NULL |                |
|             | 삭제  |   deleted   |  varchar(1)  |          |                |

### 댓글 reply
|     Key     |   이름   |    Field    |     Type     |   NULL   |     Extra      |
|:-----------:|:------:|:-----------:|:------------:|:--------:|:--------------:|
| primary key | 댓글 번호  |  reply_id   |    bigint    | NOT NULL | auto_increment |
|             |  글쓴이   |   writer    | varchar(16)  | NOT NULL |                |
|             |   내용   |  contents   | varchar(255) | NOT NULL |                |
|             |  생성일   | created_at  |   datetime   | NOT NULL |                |
|             |  수정일   | modified_at |   datetime   |   NULL   |                |
|             | 게시글 번호 | article_id  |    bigint    | NOT NULL |                |
|             |   삭제   |   deleted   |  varchar(1)  |          |                |
