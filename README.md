# be-java-cafe
마스터즈 2023 스프링 카페<br>5차 리뷰 대비 다시 작성

### 5차 리뷰 준비를 위한 학습계획
- 배포....!!!!!!  :fire::fire::ship::ship:
> [ㅇ] 3단계 (Jdbc연결) 돌아가는지 확인하기<br>
> [] 배포....!!!!!!<br>
> [] (시간 남으면) 4단계 직행....!!!! ------> (!!!???)앗 배포랑 구현은 혹시 별개의 것...?(아 그럼 이번주 리뷰를 위해서 4단계로 바로 그냥 직행을)    <br>
> [] (필요성을 절감하여) logging 사용하기

### 경과

#### 학습 주제
- Dependency: '외부 객체를 필수 속성으로 갖게 될 경우 그 객체'
- Dependency Injection: D를 가지게 만드는 방법(@Autowired를 속성/세터/생성자에 사용 -> 생성자1개일 때 불필요)
- Bean: 'Ioc 컨테이너에서 만든 자바 클래스' / '자바 객체를 재사용이 가능하도록 정의(한 것)'
- GET과 POST의 차이: Get은 여러 번의 요청을 해도 항상 일정한 결과가 나오는(멱등) 반면 Post는 요청을 할 때마다 다른 결과가 나오는(안 멱등)  
- query parameter: ex) url 주소에 '?'과 희망사항 값(parameter)이 포함되서, 필터된 결과 반환 가능! (더 확인 필요...)  
- MESSEAGE BODY: http 메세지 body는 1. 실제 전송할 데이터이다 2. byte로 표현 가능한 모든 결과가 다 갈 수 있다.
- @ComponentScan 알아보기 -> (@Component + @Bean까지...)
- Model.addAttribute: ("amumal", amumal.get()) ---mustache--> {{#amumal}} .. {{/amumal}}
- @Repository -> @Service -> @Controller 관계(매우 명쾌하면 좋겠다!)
- @RequestBody:
- Ioc 컨테이너: (Ioc는 이론 개념이고) 스프링 컨테이너는...
- @Repository -> @Service -> @Controller (흐름)
- @Configuration -> 설정 클래스 선언
- @ModelAttribute: 원시 타입 인자를 view에(Model에) 전달해주는 역할!
- wrapper 클래스!: '원시 타입 자체를 객체로 만들어주는' 것인데(아직 정확히 이해하지 못함 -> 몇 번 예제로 연습을...)
- 자료구조 선택기준!: 성능 스펙이 다 있으므로 생각을 해서 선택(!)
- WebMVCConfigurer(인터페이스): ~를 구현한 클래스로 .... (이 것으로 할 수 있는 것들을 더 찾아봐야 겠다는)
- DAO == Data Access 0bject == @Repository  
- Persistence Layer == 데이터베이스(영구저장)
- EJB = Enterprise Java Bean
- in-memory DB -> 잘 되는지 돌려보기에 아주 좋다는(!)

# 5주차
## 미션 진행 상황 <2단계 구현> (완료)

<특이사항>
- 모든 사용자는 게시글 목록을 볼 수 있어야 한다 <br>
  -> (특별히 조치를 하지 않으면 원래 그랬던 것 같은) -> (요구사항을 이해하지 못한 듯한 느낌)

### [커밋을 이쁘게 하기 위한 TODO리스트]

## (미루고 3단계 직행)

<버그>
- [] /users/ 다음에 없는 아이디를 쓰면 빈 profile 화면으로 이동 됨 -> 뭔가 이상한 -> 에러 페이지 처리(예정)
- [] 질문하기에 등록직후 나오는 "redirect:/articles" (localhost:8080/articles)에서는 잘 보이는 article목록이
맨 처음에 보이는 index.html(localhost:8080 == 기본주소 == 메인화면 == 같은 index.html인데) 에서만 안 보이는(헉..) 

<회원정보 수정>
- 비밀번호, 이름, 이메일만 수정할 수 있으며,<br>사용자 아이디는 수정할 수 없다.---> (readOnly 처리) <br>비밀번호가 일치하는 경우에만 수정 가능하다.
- [ㅇ] /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
- [] UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.   <<<<< (헉.... DTO아니고 User에 하라고 떡하니 써있는....)
- [] {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
- [] DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.
- [] UserRepository의 save() 메소드를 사용해 업데이트한다.

## <3단계>

<H2 데이터베이스 연동>
- [ㅇ] H2 데이터베이스 의존성을 추가하고 연동한다. (JPA와 같은 ORM은 사용하지 않는다.)
- [ㅇ] Spring JDBC를 사용한다. (DB 저장 및 조회에 필요한 SQL은 직접 작성한다.)
- [ㅇ] 데이터베이스를 생성할 때 필요한 SQL문을 resources/schema.sql 로 저장한다.

<게시글 데이터 저장하기>
- [ㅇ] Article 클래스를 DB 테이블에 저장할 수 있게 구현한다.
> Article 테이블이 적절한 PK를 가지도록 구현한다. -> AUTO_INCREMENT로 되는 중(id)
- [ㅇ] 게시글 목록 구현하기 
> 전체 게시글 목록 데이터를 DB에서 조회하도록 구현한다. -> 그냥 model에 쓸 list를 DB에서 가져오면 되는거(...!!)
- [ㅇ] 게시글 상세보기 구현하기
> 게시글의 세부 내용을 DB에서 가져오도록 구현한다. -> 그냥 model에 쓸 Article findById만 DB에서 가져오면 되는거(!! ! !)
- [ㅇ] 사용자 정보 DB에 저장 
- [ㅇ] 사용자 목록 구현  
- [ㅇ] 사용자 상세보기 구현
> 회원가입을 통해 등록한 사용자 정보를 DB에 저장한다. -> (똑같)
- [] 배포(..!!!!!!!) 
> (h2로)AWS와 같은 클라우드를 이용해서 간단히 배포를 진행해 본다. -> README와 PR에 배포 URL을 기술한다.

