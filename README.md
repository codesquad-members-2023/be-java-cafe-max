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

## 🌎 웹페이지 디자인
- 제공된 자료를 활용하거나 [디자인 기획서](https://www.figma.com/file/x3Ti8BcshPj5TFCUlTGLIe/BE_%EA%B5%90%EC%9C%A1%EC%9A%A9%EC%9B%B9%ED%8E%98%EC%9D%B4%EC%A7%80?node-id=0-1&t=IZ2KLLZeyhys2vmc-0)를 참고해서 직접 구현한다.
- 디자인은 자유롭게 변경 가능하다.
- 필요에 따라 웹 페이지 URL은 변경 가능하다.

## ⌨️ 프로그래밍 요구사항
- Lombok은 사용하지 않는다. 
- 별도의 데이터베이스는 사용하지 않는다. 
- 웹 템플릿은 Mustache를 추천한다. 원할 경우 Thymeleaf를 사용해도 무방하다.
- 기능에 따른 URL과 메서드 컨벤션을 정한다.
- HTML의 중복 코드를 제거한다.
- WebMVCConfigurer를 적용한다.

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

## 📌 URL 및 메서드 컨벤션
| 기능            | URI                | templates           | HTTP 메서드  |
|:--------------|:-------------------|:--------------------|:----------|
| 회원 가입         | /user/create       | /user/form.html     | `POST`    |
| 회원 목록 조회      | /users             | /user/list.html     | `GET`     |
| 특정 회원 프로필 조회  | /users/{userId}    | /user/profile.html  | `GET`     |
| 게시글 쓰기        | /article/write     | /qna/form.html      | `POST`    |
| 게시글 목록 조회     | /articles          | /index.html         | `GET`     |
| 게시글 상세 보기     | /articles/{index}  | /qna/show.html      | `GET`     |

## ⚡️ 개선 필요 사항
- [X] html 중복 코드 분리
- [ ] 각종 유효성 검증
- [ ] 테스트 케이스 작성
- [ ] 게시글 TimeStamp 추가

## 🔥 고민
> [상세 내용 참고](https://graceful-dracorex-565.notion.site/Spring-c73a165b57ed4716ad3c65f0ed173d1c)
- [X] ❗️: `Template` 경로 매핑이 안되는 이슈
  - Template Engine 미설정으로 인해 발생, 설정 후 해결(프로젝트에 미리 의존성 설정되어 있는 줄 알았는데 아니었음)
- [X] ❓: 매핑 후 HTML 내 `URI`를 일일이 바꿔주야 하는지?
  - Thymeleaf `fragment` 기능으로 HTML 공통 부분 분리하여 해결
- [X] ❓: View에서 값을 전달받았을 때 `setter`로 객체 필드 변수를 할당해야만 하는지? (김영한님 강의에서 setter로 해서...)
  - 확실하지는 않지만 꼭 그럴 필요는 없을 것 같아서 우선 생성자로 하는 것으로
- [X] ❓: 매핑 URI 앞에 `/` 가 붙을 때와 아닐 때의 차이점? (예: `"/링크"` vs `"링크")
  - 절대 경로, 상대 경로 차이. 하지만 두가지 방법으로 테스트했을 때 다 문제 없었음
  - View Resolver에서 자동으로 `/`를 붙여주기 때문에 @Getmapping return 시  
  앞에 `/` 붙어있으면 서버에 배포했을 때는 문제가 발생할 수 있다구 들음(확실치 않아서 공부 필요)
- [ ] ❓: 매핑 후 어떨 때 `redirect` 하고 어떨 때 `template`를 리턴하는지 판단 기준?
- [X] ❗️: WebMvcConfigurer 가 적용되지 않던 이슈
  - 인터페이스를 구현하는 객체의 실행 순서를 지정하는 코드 추가하여 해결

## ✏️ 공부가 필요한 내용
- [ ] 절대 경로, 상대 경로
- [ ] View Resolver 동작 원리
- [ ] redirect
- [ ] WebMvcConfigurer & @EnableWebMvc
