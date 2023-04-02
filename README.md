# be-java-cafe


---

### 기능 목록

- 회원 가입 및 회원 목록 조회
    - 회원 가입 기능
    - 회원 목록 조회 기능
    - 회원 프로필 조회 기능

- 글 쓰기
    - 글쓰기 기능
    - 글 목록 조회 기능
    - 게시글 상세보기 기능

- 회원정보 수정
    - 회원정보 수정 화면
    - 회원정보 수정 기능

### URL

| 기능                  | URL        | Templates                   | HTTP 메서드 |
| --------------------- |------------| --------------------------- | ----------- |
| 회원 가입 기능        | /user/signUp | templates/user/form.html    | POST        |
| 회원 목록 조회 기능   | /users     | templates/user/list.html    | GET         |
| 회원 프로필 조회 기능 | /users/{id} | templates/user/profile.html | GET         |
| 글쓰기 기능           | /post/submit | templates/post/form.html    | POST        |
| 게시글 목록 조회 기능 | /          | templates/index.html        | GET         |
| 게시글 상세보기 기능  | /articles/{id} | templates/post/show         | GET         |
| 회원정보 수정 화면    | /users/{id}/form | templates/user/updateForm   | GET         |
| 회원정보 수정 기능    | /users/{id}|                             | PUT         |

### 진행 흐름

![스크린샷 2023-03-31 오후 2.13.11](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202023-03-31%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.13.11.png)

출처: [DTO](https://e-una.tistory.com/72)

클라이언트로부터 온 request를 DTO로 매핑후 원하는 동작을 하도록 했습니다.


## 실행결과

<details markdown="1">
<summary>회원가입</summary>


회원가입에 필요한 아이디,이메일,닉네임,비밀번호를 입력받는다.
![회원가입1](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B81.png)

</details>

<details markdown="1">
<summary>회원목록 조회</summary>


회원가입 완료후 가입을한 모든 회원을 보여준다.(지금 예시는1명이지만 회원가입을 계속해서하면 회원가입한 멤버가 모두 표시됩니다!)
![회원 목록 조회](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%20%E1%84%86%E1%85%A9%E1%86%A8%E1%84%85%E1%85%A9%E1%86%A8%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC.png)

</details>

<details markdown="1">
<summary>특정 회원 프로필 조회</summary>


회원목록에서 특정회원의 닉네임을 누르면 해당 회원의 프로필로 갈수있다.
![특정 회원 프로필 조회](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%90%E1%85%B3%E1%86%A8%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%91%E1%85%B5%E1%86%AF%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC.png)

</details>

<details markdown="1">
<summary>글쓰기 기능</summary>


메인페이지에서 글쓰기 버튼을 통해 글을쓸수있는 form으로 이동할수있다.
![글쓰기 기능](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%80%E1%85%B3%E1%86%AF%E1%84%8A%E1%85%B3%E1%84%80%E1%85%B5%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC.png)


</details>

<details markdown="1">
<summary>게시글 목록 조회 기능</summary>


글쓰기 기능이 완료된후 자동적으로 메인페이지로 가게되고 이때 메인페이지에 내가 작성한 글이 표시된다.
![게시글 목록 조회 기능](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%86%E1%85%A9%E1%86%A8%E1%84%85%E1%85%A9%E1%86%A8%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC.png)


</details>


<details markdown="1">
<summary>게시글 상세보기 기능</summary>


메인페이지에서 글의 제목을 누르면 해당글의 상세 내용을 볼수있다.(지금은 제목과 내용만 업데이트 됩니다!)
![게시글 상세보기 기능2](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%A1%E1%86%BC%E1%84%89%E1%85%A6%E1%84%87%E1%85%A9%E1%84%80%E1%85%B5%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC2.png)



</details>

<details markdown="1">
<summary>회원정보 수정 화면</summary>


특정 멤버의 프로필에 들어가 오른쪽 위 회원정보 수정 버튼을 통해 회원정보 수정 화면으로 이동할수 있다.
이때 기존비밀번호 입력란에 입력한 비밀번호와 서버 repository에 저장된 비밀번호가 다르다면 회원정보를 바꿀수 없다.
![회원정보 수정](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%87%E1%85%A9%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC.png)


</details>



<details markdown="1">
<summary>회원정보 수정후 화면</summary>
회원정보를 수정하면 자동적으로 멤버리스트 페이지로 이동하고 변경된 멤버의 닉네임과 이메일을 볼수있다.
![회원정보 수정후 화면](https://raw.githubusercontent.com/CDBchan/Typora-img/main/img/%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%87%E1%85%A9%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%92%E1%85%AE%20%E1%84%92%E1%85%AA%E1%84%86%E1%85%A7%E1%86%AB.png)


</details>
