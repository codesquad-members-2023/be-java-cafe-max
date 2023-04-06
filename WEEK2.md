## 학습 계획

---

<br>

[x] 게시글 기능 구현 (이전 미션 2단계)
[ ] Spring JDBC 사용, Repository 구현체 교체
[ ] AWS 학습
[ ] AWS 배포


<br>

# 게시글

---

<br>

**Article**

- 제목  
  - 2글자 이상 30글자 이하  
- 작성자  
  - 지금은 회원 가입시 요구조건대로 입력  
- 본문  
  - 0글자 이상 1000글자 이하  

<br>

**URL**

---

게시글 리스트 조회
- [GET] "/articles"
  게시글 단건 조회
- [GET] "/articles/{id}"
  게시글 작성폼
- [GET] "/articles/write"
  게시글 작성
- [POST] "/articles"

<br>

**구현 목록**

[x] Service  
- 게시글 저장, 조회, 리스트 조회  
- ~~예외 처리~~  
- ~~테스트 코드~~  

[x] Repository  
- 게시글 저장, 조회, 리스트 조회  
- ~~테스트 코드~~  

[x] Controller  
- 게시글 조회, 작성 매핑  
- dto 검증  
- ~~테스트 코드~~  

[x] Thymeleaf  

<br>

# JDBC

implementation 'com.h2database:h2'
implementation는 컴파일 시간에 종속된 모듈을 노출시키지 않기 때문에 데이터 베이스를 초기화할. sql 파일을 자동으로 읽을 수 없어 실행할. sql 파일을 application.properties에 직접 추가해줘야 한다.

runtimeOnly 'com.h2database:h2'
runtimeonly는 런타임 중에 종속성을 추가한다. 따라서 application.properties에 초기화 파일 경로를 입력하지 않아도 된다.


## 학습 리스트 및 생각

---

<br>

**학습 리스트**


<br>

**생각**

- 이번주부터는 미션 구현을 빠르게 끝내고 리펙토링을 하면서 원하는 부분을 더 학습 해야겠다.  
- 기능 단위로 테스트를 작성하려고 했는데 AWS 학습을 집중해야 되기 때문에 급하게 게시글 기능을 구현했다. Jdbc를 적용하면서 테스트 코드를 짜야곘다  
- cs, 알고리즘 스터디를 같이 하려니 시간 분배가 어려워서 계획적으로 좀 해야될 것 같다.  
- forward를 쓸 일이 없을 것 같았는데 홈 화면에서 게시글 리스트를 보여주다보니 forward를 사용해봤다. 
HomeController에 ArticleService를 주입하는 것이 맞을까? 만약 홈 화면에서 보여주는 정보가 여러 종류라면 어떻게 해야 될까  

**학습 리스트**

[ ] aws 공부, 정리  
[ ] 