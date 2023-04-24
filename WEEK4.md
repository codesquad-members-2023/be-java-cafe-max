# 학습 계획

---

~~1. 리펙토링~~
2. AWS
~~3. 게시글 수정 기능~~
4. 댓글 기능
5. 조회수 추가


<br>

### 1. 리펙토링

---

[x] 테이블 수정 -> ~~MySQL로 변경하면서 수정~~
  - users
    - 로그인 아이디 칼럼 수정 user_id -> username  
    - 닉네임 칼럼 추가 nickname 중복 불가
    - email 중복 허용
  - article
    - 작성자 join 없이 로그인 세션 정보로 칼럼 추가

[x] removeAttribute() vs invalidate() 차이점
- https://treecode.tistory.com/106

[x] 중복 체크의 경우 exists 쿼리를 사용하도록 수정  
- ~~클러스터링 테이블에서 pk로 유저 조회를 하는 경우 B-Tree의 리프 노드에서 주소값을 통해 디스크 I/O가 발생하는데 
exists를 사용하면 존재 여부만 판별하니까 리프 노드에서 체크만 하고 디스크 I/O는 발생하지 않는 것 같다.~~  
- ~~MySQL InnoDB에서 클러스터링 테이블은 리프 노드에 column 값들을 가지고 있어서 큰 차이는 없을 것 같다~~
- pk가 아닌 세컨더리 인덱스로 조회할 때는 클러스터 인덱스를 조회 안하니까 빠른거 같다.  

[x] 단건 조회 queryForStream 사용  
- 조회 결과가 없을 경우 try-catch 제거
- ResultSet의 담긴 결과를 stream 처리로 읽어오기 때문에 ResultSet을 직접 명시적으로 닫아줘야 한다. -> try resources 사용  

<br>

### 2. AWS

---

<br>

### 3. 게시글 수정 기능

---

- URL
  - [GET] "/articles/{id}/update-form"  
  - [PUT] "/articles/{id}"  
- 세션 id와 해당 게시글의 작성자 id를 비교하여 일치하면 수정 버튼이 보임  

<br>

### 4. 댓글 기능

---

<br>


### 5. 조회수

---

동시성 고려
- 상품 재고 같은 중요한 값이 아니면 잃는 것이 더 클수도 있음  
- 중복 조회수 -> 쿠키(?)  
- https://www.inflearn.com/course/동시성이슈-재고시스템#curriculum  
- https://zzang9ha.tistory.com/443

<br>