![](https://img.shields.io/badge/VERSION-4.0-green)
![](https://img.shields.io/badge/LAST_UPDATE-2023--04--10-blue)

# ☕️ 스프링 카페 미션
- 2023 코드스쿼드 마스터즈 BE max에서 진행한 스프링으로 카페를 구현하는 미션
- 미션 기간(max 4~5주차): `
  - 1~2단계: `23-03-27 ~ 23-03-31` [5d]
  -   3단계: `23-04-03 ~ 23-04-07` [5d]
  - 4~5단계: `23-04-10 ~ 23-04-14` [5d]

## ⚙️ 개발 환경
![IntelliJ](https://img.shields.io/badge/IntelliJ-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

## 🔖 버전 기록
| 버전  | 구현 기간               | 기능                                                   |
|:----|:--------------------|:-----------------------------------------------------|
| 4.0 | 23-04-10 ~          | 로그인 기능                                               |
| 3.0 | 23-04-03 ~ 23-04-07 | H2 DB 연동 <br/>AWS 배포                                 |
| 2.0 | 23-03-29 ~ 23-03-31 | 게시글 쓰기 기능 추가 <br/>게시글 목록 조회 기능 추가                    |
| 1.0 | 23-03-27 ~ 23-03-29 | 회원 가입 기능 추가 <br/>회원 목록 조회 기능 추가 <br/>회원 프로필 조회 기능 추가 |

## ✔️ 기능 요구사항

### 회원가입 기능
- 회원 가입 폼에 입력받은 데이터로 회원 가입하는 기능을 구현한다.
  - 가입하기 페이지에서 회원 가입 폼을 표시한다.
  - 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.

### 회원 목록 조회 기능
- 현재 가입된 회원 목록을 조회할 수 있는 기능을 구현한다.

### 회원 프로필 조회 기능
- 가입된 회원의 프로필 정보를 조회할 수 있는 기능을 구현한다.
  - 사용자 목록 조회 페이지에서 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.

### 게시글 쓰기 기능
- 모든 사용자가 게시글을 작성할 수 있는 기능을 구현한다.
  - 게시글 추가를 완료한 후 메인 페이지로 이동한다.

### 게시글 목록 조회 기능
- 모든 사용자가 게시글 목록을 볼 수 있는 기능을 구현한다.
  - 메인 페이지에서 게시글 목록을 조회한다.

### 게시글 상세 보기 기능
- 모든 사용자가 작성된 게시글의 상세 내용을 볼 수 있는 기능을 구현한다.
  - 게시글 목록의 제목을 클릭했을 때 게시글 상세 페이지에 접속할 수 있도록 한다.

### 회원 정보 수정 기능
- (선택) 입력된 사용자 정보를 수정할 수 있는 기능을 구현한다.

### H2 DB 연동 기능
- 게시글 데이터를 DB 테이블에 저장할 수 있는 기능을 구현한다.
  - 게시글 객체가 적절한 PK를 가지도록 구현한다.
- 게시글 목록 데이터를 DB에서 조회하는 기능을 구현한다.
- 게시글의 세부 내용을 DB에서 가져오는 기능을 구현한다.
- 회원가입을 통해 등록한 사용자 정보를 DB에 저장하는 기능을 구현한다.

### 배포
- AWS를 이용해서 간단히 배포를 진행한다. 
  - 배포 시 DB는 H2를 사용하며, 배포를 위해 MySQL 등의 DB로 변경하지 않는다.

### 로그인 기능
- 현재 상태가 로그인 상태이면 상단 메뉴에서 “로그아웃”, “개인정보수정”을 표시한다.
- 현재 상테가 로그인 상태가 아니라면 상단 메뉴에서 “로그인”, “회원가입”을 표시한다.

### (선택) 개인정보 수정 기능
- 로그인한 사용자는 자신의 정보를 수정할 수 있다.
- 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
- 비밀번호가 일치하는 경우에만 수정 가능하다.

## 🌎 웹페이지 디자인
- 제공된 자료를 활용하거나 [디자인 기획서](https://www.figma.com/file/x3Ti8BcshPj5TFCUlTGLIe/BE_%EA%B5%90%EC%9C%A1%EC%9A%A9%EC%9B%B9%ED%8E%98%EC%9D%B4%EC%A7%80?node-id=0-1&t=IZ2KLLZeyhys2vmc-0)를 참고해서 직접 구현한다.
- 디자인은 자유롭게 변경 가능하다.
- 필요에 따라 웹 페이지 URL은 변경 가능하다.

## ⌨️ 프로그래밍 요구사항
- Lombok은 사용하지 않는다. 
- H2 데이터베이스 의존성을 추가하고 연동한다.
  - JPA와 같은 ORM은 사용하지 않는다. 
  - Spring JDBC를 사용한다. 
  - DB 저장 및 조회에 필요한 SQL은 직접 작성한다.
- 웹 템플릿은 Mustache를 추천한다. 원할 경우 Thymeleaf를 사용해도 무방하다.
- 기능에 따른 URL과 메서드 컨벤션을 정한다.
- HTML의 중복 코드를 제거한다.
- WebMVCConfigurer를 적용한다.
- Spring MVC에서 메소드의 인자로 HttpSession을 이용해서 로그인을 구현한다.
- Spring Security와 같은 별도 라이브러리 등은 사용하지 않는다.
- API가 아닌 템플릿 기반으로 구현한다.

---

## ✨ 기능 목록
- 회원 가입 기능
  - [X] 가입하기 페이지에서 회원 가입 폼을 표시하는 기능
  - [X] 회원 가입 완료 후 확인 버튼을 누르면 회원 목록 조회 페이지로 이동하는 기능

- 회원 목록 조회 기능
  - [X] 목록 조회 페이지에서 가입한 회원들의 목록을 출력하는 기능
  - [X] 가입한 회원 ID를 누르면 프로필 페이지로 이동하는 기능

- 회원 프로필 조회 기능
  - [X] 회원 프로필 페이지에서 개별 회원 프로필 정보를 출력하는 기능

- 게시글 쓰기 및 저장 기능
  - [X] 게시글을 작성하고 저장하는 기능
  - [X] 게시글 작성 완료 후 메인 페이지로 이동하는 기능

- 게시글 목록 조회 기능
  - [X] 작성된 게시글 목록을 조회하는 기능

- 게시글 상세 보기 기능
  - [X] 게시글 목록 클릭 시 상세 페이지에 접속하는 기능

- (선택) 회원 정보 수정 기능

- H2 DB 연동 기능
  - [X] 게시글 데이터를 DB에 저장하는 기능(PK 포함 필수)
  - [X] 사용자 정보를 DB에 저장하는 기능

- AWS 배포
  - [X] H2 DB를 사용하여 AWS 배포

- 로그인 기능
  - [ ] 로그인 상태 시 상단 메뉴에서 “로그아웃”, “개인정보수정”을 표시하는 기능

## 📌 URL 및 메서드 컨벤션
| 기능            | URI               | templates          | HTTP 메서드 |
|:--------------|:------------------|:-------------------|:---------|
| 회원 가입 페이지 조회  | /users/new        | /user/form.html    | `GET`    |
| 회원 가입 폼 제출    | /users            | /user/form.html    | `POST`   |
| 회원 목록 조회      | /users            | /user/list.html    | `GET`    |
| 특정 회원 프로필 조회  | /users/{userId}   | /user/profile.html | `GET`    |
| 게시글 쓰기 페이지 조회 | /articles/new     | /qna/form.html     | `GET`    |
| 게시글 폼 제출      | /articles         | /qna/form.html     | `POST`   |
| 게시글 목록 조회     | /articles         | /index.html        | `GET`    |
| 게시글 상세 보기     | /articles/{index} | /qna/show.html     | `GET`    |
| 에러 페이지        | /error            | /error.html        | `GET`    |
| 로그인 페이지 조회    | /login            | /user/login.html   | `GET`    |
| 로그인 폼 제출      | /login            | /user/login.html   | `POST`   |

## ⚡️ 개선 필요 사항
- [X] html 중복 코드 분리
- [ ] 각종 유효성 검증
- [ ] 테스트 케이스 작성
- [ ] 게시글 TimeStamp 추가

## 🔥 고민 & Trouble Shooting
> [상세 내용 참고](https://graceful-dracorex-565.notion.site/Spring-c73a165b57ed4716ad3c65f0ed173d1c)
- [X] ❗️: `Template` 경로 매핑이 안되는 이슈
- [X] ❓: 매핑 후 HTML 내 `URI` 수정을 효율적으로 할 수 있는 방법?
- [X] ❗️: CSS가 적용되지 않던 이슈
- [ ] ❓: 어느 레이아웃에서 어떻게 DTO <-> 도메인 객체를 변환하면 좋을지? -> 서비스 레이아웃
- [X] ❓: 매핑 URI 앞에 `/` 가 붙을 때와 아닐 때의 차이점? (예: `"/링크"` vs `"링크")
- [ ] ❓: `@Get/Postmapping` 시 언제 `redirect` 하고, 언제 `template`를 리턴하는지 판단 기준?
- [X] ❗️: WebMvcConfigurer 가 적용되지 않던 이슈
- [ ] ❓: 파라미터가 많아질 때 가독성 좋게 값을 주고 받을 수 있는 방법? -> 빌더 패턴

## 📝 공부한 내용
- Template Engine
- Thymeleaf 문법
- HTTP get, post method
- Model 객체를 파라미터로 받는 이유
- @GetMapping @PostMapping 동작 원리
- 절대 경로, 상대 경로
- View Resolver 동작 원리
- Template Engine 및 매핑 URI "/" 처리 방식
- H2 DB 3가지 모드
- gradle 의존성 옵션
- 커넥션 풀
- 빌더 패턴

## ✏️ 공부가 필요한 내용
- [ ] redirect & forward
- [ ] WebMvcConfigurer & @EnableWebMvc
