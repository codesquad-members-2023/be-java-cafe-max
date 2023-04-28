# be-java-cafe
마스터즈 2023 스프링 카페 

## 기능 목록
- [x] 루트 경로일 때 홈 화면을 출력한다.
  - 모든 사용자가 게시글의 목록(홈)을 볼 수 있다.
  - 한 페이지에 15개씩 가져오도록 한다.
  - 생성일 기준 내림차순으로 보여준다.
- [x] 아이디, 비밀번호, 이름, 이메일을 입력받아 회원가입을 한다.
  - 같은 id를 가진 회원이 있다면 중복 회원 예외를 발생시킨다. 
  - 회원가입이 완료되면 회원 목록 조회로 이동한다.
- [x] 회원 목록 조회 화면에서 이름을 클릭하면 해당 유저의 프로필 화면을 출력한다.
  - 해당 유저의 id로 유저 정보를 찾을 수 없으면 존재하지 않는 회원 예외를 발생시킨다.
- [x] 홈 화면에서 질문하기 버튼을 누르면 질문하기 폼으로 이동한다.
- [x] 글쓴이, 제목, 내용을 입력받아 질문글을 생성한다.
  - 질문글을 생성하면 홈 화면(질문 목록 조회)으로 이동한다.
  - 홈 화면에서 게시글 목록을 조회한다.
  - 로그인 한 사용자만 게시글을 작성할 수 있다. 
    - 로그인하지 않은 사용자라면 로그인 페이지로 이동한다.
- [x] 홈 화면에 게시글 목록을 출력한다.
- [x] 게시글 목록의 제목을 클릭했을 때 게시글 상세 화면으로 이동한다.
  - url에 article index를 넣어서 접근한다.
  - 로그인 한 사용자만 게시글 상세 화면을 볼 수 있다.
    - 로그인하지 않은 사용자라면 로그인 페이지로 이동한다.
- [x] 회원 목록에서 수정 버튼을 누르면 회원 정보 수정 화면으로 이동한다.
  - 사용자의 id 값을 url에 넣어서 이동한다.
- [x] 이름, 이메일을 수정할 수 있다.
  - session에서 사용자 정보를 가져온다.
    - session이 비어있으면 로그인 필요 예외를 발생시킨다.
  - 사용자 아이디는 수정할 수 없다.
    - 다른 사용자의 정보를 수정하려는 경우 인증되지 않은 사용자 예외를 발생시킨다.
  - 비밀번호가 일치하는 경우에만 수정 가능하다.
    - 비밀번호가 일치하지 않으면 비밀번호 불일치 예외를 발생시킨다.
  - 수정 완료하면 사용자 목록 조회 화면(/users)로 redirect 한다.
- [x] 아이디, 비밀번호로 로그인을 한다.
- [x] 로그아웃을 클릭하면 로그아웃을 한다.
- [x] 게시글 상세화면에서 수정을 클릭하면 게시글 수정하기 화면으로 이동한다.
  - 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능하다.
    - 같지 않으면 로그인 유저와 작성자 매치 실패 예외를 발생시킨다.
  - 댓글이 없는 경우 삭제 가능
  - 게시글 작성자와 댓글 작성자가 다를 경우 삭제 불가능
  - 게시글 작성자와 댓글 작성자가 모두 같을 경우 삭제 가능
  - 게시글을 삭제할 때 댓글도 삭제, 댓글 삭제도 상태가 삭제로 변경
- [x] 게시글의 제목과 내용을 수정할 수 있다.
  - 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능하다.
    - 같지 않으면 로그인 유저와 작성자 매치 실패 예외를 발생시킨다.
  - 수정 완료하면 게시글 상세 화면으로 redirect 한다.
- [x] 게시글 상세화면에서 삭제를 클릭하면 게시글을 삭제한다.
  - 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능하다.
    - 같지 않으면 로그인 유저와 작성자 매치 실패 예외를 발생시킨다.
  - 삭제 완료하면 홈 화면으로 redirect 한다.
- [x] 로그인한 사용자는 게시글 상세보기 화면에서 댓글들을 볼 수 있다.
- [x] 로그인한 사용자는 댓글을 추가할 수 있다.
- [x] 자신이 쓴 댓글에 한해 댓글을 삭제할 수 있다.
  - 자신이 쓴 댓글이 아니면 로그인 유저와 댓글 작성자 매치 실패 예외를 발생시킨다.

## URL
|__HTTP Method__| __URL__                 | __기능__                 |
|---------------|-------------------------|------------------------|
|GET| /                       | 홈 화면 출력(질문 목록 조회)      |
|GET| /users/join             | 회원 가입 화면 출력            |
|POST| /users                  | 회원 가입                  |
|GET| /users                  | 회원 목록 조회 화면 출력         |
|GET| /users/{userId}         | 유저 프로필 화면 조회           |
|POST| /questions              | 질문하기 게시글 작성            |
|GET| /questions/form         | 글쓰기 화면 조회              |
|GET| /articles/{postId}      | 게시글 상세 조회(+ 댓글도 같이 조회) |
|GET| /users/{userId}/form    | 회원 정보 수정 화면 출력         |
|PUT| /users/{userId}/update  | 회원 정보 수정               |
|GET| /users/login            | 로그인 화면 출력              |
|POST| /users/login            | 로그인                    |
|GET| /users/logout           | 로그아웃                   |
|GET| /articles/{postId}/form | 게시글 수정 화면 출력           |
|PUT| /articles/{postId}      | 게시글 수정                 |
|DELETE| /articles/{postId}      | 게시글 삭제                 |
|POST| /reply/{postId} | 댓글 작성                  |
|DELETE|/reply/{postId}/{replyId}| 댓글 삭제                  |


## 배포 url
[codesquad_cafe](http://13.125.255.132:8080/)


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

![home](https://user-images.githubusercontent.com/57451700/235206818-0a4070af-e313-4ba8-9be8-8e25e04d41d3.png)

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

![loginuser](https://user-images.githubusercontent.com/57451700/232544257-b669e090-40ec-466e-904b-c0bb82ae4305.png)

![userupdate](https://user-images.githubusercontent.com/57451700/232544299-86901c1c-feea-4480-b816-8ae16977367d.png)

</div>
</details>

<details>
<summary>회원 정보 수정 완료 화면</summary>
<div>

![successupdate](https://user-images.githubusercontent.com/57451700/232544356-ed27d974-c357-4573-b637-796688b0e86f.png)

</div>
</details>

<details>
<summary>로그인 화면</summary>
<div>

![login](https://user-images.githubusercontent.com/57451700/232543804-2d03d914-5275-47c4-b94c-b4ca0ad1519d.png)

</div>
</details>

<details>
<summary>로그인 완료 화면</summary>
<div>

![successlogin](https://user-images.githubusercontent.com/57451700/232544104-fab8e999-57e5-4887-b68c-4f51f0a7a802.png)

</div>
</details>

<details>
<summary>게시글 수정 화면</summary>
<div>

![original](https://user-images.githubusercontent.com/57451700/232852271-5134e128-1f35-46ab-a1da-44d63ecfa688.png)

![form](https://user-images.githubusercontent.com/57451700/232852339-b8dbbddb-98ae-435c-9baf-ec30c7ab4c4f.png)

</div>
</details>

<details>
<summary>게시글 수정 완료 화면</summary>
<div>

![success](https://user-images.githubusercontent.com/57451700/232852390-d5177ee3-6187-4bbe-8b36-e3dd166a0ea5.png)

</div>
</details>

<details>
<summary>게시글 삭제 완료 화면</summary>
<div>

![delete](https://user-images.githubusercontent.com/57451700/232852437-2ab0b5aa-b6b4-49ba-bf00-d8cbd3f697c0.png)

</div>
</details>

<details>
<summary>댓글 작성 화면</summary>
<div>

![reply](https://user-images.githubusercontent.com/57451700/233772159-220f78c3-6a62-4035-a587-ac41b08562b1.png)

</div>
</details>

<details>
<summary>댓글 작성 완료 화면</summary>
<div>

![result](https://user-images.githubusercontent.com/57451700/233772197-8fe54370-a5f4-4691-85ac-a3d0b3eadbcc.png)

</div>
</details>

<details>
<summary>댓글 삭제 완료 화면</summary>
<div>

![delete](https://user-images.githubusercontent.com/57451700/233772222-0a3eded8-292a-4172-a87e-6ca585c24846.png)

</div>
</details>
