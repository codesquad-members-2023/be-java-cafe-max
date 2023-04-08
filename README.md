# be-java-cafe
마스터즈 2023 스프링 카페

## 사용 기술
- Spring MVC
- Spring Validation
- Thymeleaf
- Spring Jdbc

## URL Convention

| url                    | method | 기능                                |
|------------------------|--------|-----------------------------------|
| /users/login           | get    | 로그인 페이지 요청                        |
| /users/login           | post   | 로그인 요청                            |
| /users/join            | get    | 회원 가입 페이지 요청                      |
| /users                 | post   | 회원 가입 요청                          |
| /users/{userId}        | get    | 회원 프로필 페이지 요청                     |
| /users/{userId}/update | get    | 회원 프로필 설정 페이지 요청                  |
| /users/{userId}/update | put    | 회월 프로필 설정 요청                      |
| /users                 | get    | 모든 회원 맴버 페이지 요청                   |
| /posts/new,/posts/form | get    | 게시글 작성 페이지 요청                     |
| /posts                 | post   | 게시글 추가 요청                         |
| /                      | get    | 모든 게스글 표시되는 메인 페이지 요청             |                    
| /posts/{postId}        | get    | 게시글의 페이지 요청                       |

## [Commit Log Guidelines](https://github.com/naver/egjs/wiki/Commit-Log-Guidelines)
- feat: A new feature
- fix: A bug fix
- docs: Documentation only changes
- style: Changes that do not affect the meaning of the code. Such as white-space, formatting, missing semi-colons, etc... It also possible to change JSHint, JSCS rules of the code.
- refactor: A code change that neither fixes a bug nor adds a feature
- test: Adding missing tests. Changing tests.
- demo: Adding missing demos. Changing demos.
- chore: Changes to the build process or tools and libraries such as documentation generation
