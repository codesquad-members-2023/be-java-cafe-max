# 학습 계획

---

1. 리펙토링
2. AWS
3. 게시글 수정 기능
4. 댓글 기능
5. 조회수 추가


<br>

### 1. 리펙토링

---

[ ] 테이블 수정 -> MySQL로 변경하면서 수정
  - users
    - 로그인 아이디 칼럼 수정 user_id -> username  
    - 닉네임 칼럼 추가 nickname 중복 불가
    - email 중복 허용
  - article
    - 작성자 join 없이 로그인 세션 정보로 칼럼 추가

[x] removeAttribute() vs invalidate() 차이점
- https://treecode.tistory.com/106

[ ] 중복 체크의 경우 exists 쿼리를 사용하도록 수정  
- 클러스터링 테이블에서 pk로 유저 조회를 하는 경우 B-Tree의 리프 노드에서 주소값을 통해 디스크 I/O가 발생하는데 
exists를 사용하면 존재 여부만 판별하니까 리프 노드에서 체크만 하고 디스크 I/O는 발생하지 않는 것 같다.  

[ ] 단건 조회 queryForStream 사용  
- 조회 결과가 없을 경우 try-catch 제거
- ResultSet의 담긴 결과를 stream 처리로 읽어오기 때문에 ResultSet을 직접 명시적으로 닫아줘야 한다. -> try resources 사용  

<br>

### 2. AWS

---

<br>

### 3. 게시글 수정 기능

---

<br>

### 4. 댓글 기능

---

<br>


### 5. 조회수

---

<br>