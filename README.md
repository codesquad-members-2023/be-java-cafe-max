# be-java-cafe
마스터즈 2023 스프링 카페 

## 기능 목록
- [x] 루트 경로일 때 홈 화면을 출력한다.
- [x] 아이디, 비밀번호, 이름, 이메일을 입력받아 회원가입을 한다.
  - 같은 id를 가진 회원이 있다면 중복 회원 예외를 발생시킨다. 
  - 회원가입이 완료되면 회원 목록 조회로 이동한다.
- [x] 회원 목록 조회 화면에서 이름을 클릭하면 해당 유저의 프로필 화면을 출력한다.
- [x] 홈 화면에서 질문하기 버튼을 누르면 질문하기 폼으로 이동한다.
- [x] 글쓴이, 제목, 내용을 입력받아 질문글을 생성한다.
  - 질문글을 생성하면 홈 화면(질문 목록 조회)으로 이동한다.
  - 홈 화면에서 게시글 목록을 조회한다.
  - 모든 사용자는 게시글을 작성할 수 있다.
- [x] 홈 화면에 게시글 목록을 출력한다.
- [x] 게시글 목록의 제목을 클릭했을 때 게시글 상세 화면으로 이동한다.
  - url에 article index를 넣어서 접근한다.
- [x] 회원 목록에서 수정 버튼을 누르면 회원 정보 수정 화면으로 이동한다.
  - 사용자의 id 값을 url에 넣어서 이동한다.
- [x] 비밀번호, 이름, 이메일을 수정할 수 있다.
  - 사용자 아이디는 수정할 수 없다.
  - 비밀번호가 일치하는 경우에만 수정 가능하다.
  - 수정 완료하면 사용자 목록 조회 화면(/users)로 redirect 한다.

## URL
|__HTTP Method__|__URL__| __기능__            |
|---------------|-------|-------------------|
|GET|/| 홈 화면 출력(질문 목록 조회) |
|GET|/users/join| 회원 가입 화면 출력       |
|POST|/users| 회원 가입             |
|GET|/users| 회원 목록 조회 화면 출력    |
|GET|/users/{userId}| 유저 프로필 화면 조회      |
|POST|/questions| 질문하기 게시글 작성       |
|GET|/questions/form| 글쓰기 화면 조회|
|GET|/articles/{postId}| 게시글 상세 조회|
|GET|/users/{userId}/form| 회원 정보 수정 화면 출력|
|PUT|/users/{userId}/update| 회원 정보 수정|

## 배포 url
[codesquad_cafe](http://52.79.232.139:8080/)
- step1만 배포된 상태


## 동작 화면
<details>
<summary>회원 가입 화면</summary>
<div>

![join](https://user-images.githubusercontent.com/57451700/228421158-31b1cb57-4d2a-4f82-a076-1f45b592725b.png)

</div>
</details>

<details>
<summary>회원 목록 화면</summary>
<div>

![list](https://user-images.githubusercontent.com/57451700/228422180-0ce2e663-7884-4416-af84-e9b22a9e9577.png)

</div>
</details>

<details>
<summary>회원 프로필 화면</summary>
<div>

![profile](https://user-images.githubusercontent.com/57451700/228422257-efad632c-1b2e-4466-ad0c-86f25ca3e782.png)
</div>
</details>

<details>
<summary>글 쓰기 화면</summary>
<div>

![qnahome](https://user-images.githubusercontent.com/57451700/230138787-e5bc6df5-c552-46b6-a10b-d2f4d87b97c9.png)

</div>
</details>

<details>
<summary>글 목록 조회 화면</summary>
<div>

![home](https://user-images.githubusercontent.com/57451700/230138874-eaf86b43-34a4-40c7-be5d-4568c614798b.png)

</div>
</details>

<details>
<summary>게시글 상세보기 화면</summary>
<div>

![detail](https://user-images.githubusercontent.com/57451700/230138947-09aa8971-a73e-46f7-82a7-cd04f158d86e.png)

</div>
</details>

<details>
<summary>회원 정보 수정 화면</summary>
<div>

![form](https://user-images.githubusercontent.com/57451700/230179012-c77d8570-97f8-425e-9866-04c998422589.png)

</div>
</details>

<details>
<summary>회원 정보 수정 완료 화면</summary>
<div>

![update](https://user-images.githubusercontent.com/57451700/230179038-0ce9898b-ade4-4f0b-a8cb-5eaab10fc48a.png)

</div>
</details>

