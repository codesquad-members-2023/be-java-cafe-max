![](https://img.shields.io/badge/VERSION-2.0-green)
![](https://img.shields.io/badge/LAST_UPDATE-2023--03--29-blue)

# ☕️ 스프링 카페 미션
- 미션 기간(max 3주차): `23-03-27 ~ 23-03-31` [5d]
- 2023 코드스쿼드 마스터즈 BE max에서 진행한 스프링으로 카페를 구현하는 미션

## ⚙️ 개발 환경
![IntelliJ](https://img.shields.io/badge/IntelliJ-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)


## ✔️ 기능 요구사항
- 회원 가입 폼에 입력받은 데이터로 회원 가입하는 기능을 구현한다.
- 현재 가입된 회원 목록을 조회할 수 있는 기능을 구현한다.
- 가입된 회원의 프로필 정보를 조회할 수 있는 기능을 구현한다.
- 모든 사용자가 게시글을 작성할 수 있는 기능을 구현한다.
- 모든 사용자가 게시글 목록을 볼 수 있는 기능을 구현한다.
- 모든 사용자가 작성된 게시글의 상세 내용을 볼 수 있는 기능을 구현한다.
- (선택) 입력된 사용자 정보를 수정할 수 있는 기능을 구현한다.

## 🌎 웹페이지 디자인
- 제공된 자료를 활용하거나 [디자인 기획서](https://www.figma.com/file/x3Ti8BcshPj5TFCUlTGLIe/BE_%EA%B5%90%EC%9C%A1%EC%9A%A9%EC%9B%B9%ED%8E%98%EC%9D%B4%EC%A7%80?node-id=0-1&t=IZ2KLLZeyhys2vmc-0)를 참고해서 직접 구현한다.
- 디자인은 자유롭게 변경 가능하다.
- 필요에 따라 웹 페이지 URL은 변경 가능하다.

## ⌨️ 프로그래밍 요구사항
- Lombok은 사용하지 않는다. 
- 별도의 데이터베이스는 사용하지 않는다. 
- 웹 템플릿은 Mustache를 추천한다. 원할 경우 Thymeleaf를 사용해도 무방하다.
- 기능에 따른 URL과 메서드 컨벤션을 정한다.

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
  - [ ] 작성된 게시글 목록을 조회하는 기능

- 게시글 상세 보기 기능
  - [ ] 게시글 목록 클릭 시 상세 페이지에 접속하는 기능

- (선택) 회원 정보 수정 기능

## 📌 URL 및 메서드 컨벤션
| 기능            | URI                | templates           | HTTP 메서드  |
|:--------------|:-------------------|:--------------------|:----------|
| 회원 가입         | /user/create       | /user/form.html     | `POST`    |
| 회원 목록 조회      | /users             | /user/list.html     | `GET`     |
| 특정 회원 프로필 조회  | /users/{userId}    | /user/profile.html  | `GET`     |
| 게시글 쓰기        | /article/write     | /qna/form.html      | `POST`    |
| 게시글 목록 조회     | /articles          | /index.html         | `GET`     |
| 게시글 상세 보기     | /articles/{index}  | /qna/show.html      | `GET`     |

## ⚡️ 개선이 필요한 기능
- [ ] html 중복 내용 제거 및 분리
- [ ] 유효성 검증
- [ ] 테스트 케이스 작성
