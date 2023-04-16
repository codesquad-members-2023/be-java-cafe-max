# be-java-cafe
마스터즈 2023 스프링 카페<br>3차 리뷰 대비 다시 작성

### 1-1. 회원 가입 기능 구현
- [ㅇ]가입하기 페이지에서 회원 가입 폼을 표시한다(기본 Html에 있음)<br>
- []Html에서 중복된 부분을 분리한다<br>
- [ㅇ]개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다

- 기존에 가입했던 회원들 목록이 조회 화면에서 정렬되서 보여져야 될 텐데(자동으로 되는지 아직 모름)<br>
아마도 최근에 가입한 회원이 맨 위에 있으면 될 듯.

- User 자료형 필드변수: 주어진 html을 보면 아이디, 이름, 이메일, +패스워드,(그리고 뭐가 더 필요할지 아직 모름)

#### <학습>
- [ㅇ] 의존성 주입에 대해 간략히 확인
- [] 의존성 주입에 대해 많이 확인
- [] bean에 대해 자세히 확인
- [] 리뷰 코멘트 받은 부분들 매우 확인
- bean: @Controller, @Service, @Repository 등이 붙으면 bean이다(@Component)
- @AutoWired: setter, 필드, 2개 이상 생성자에서 bean 등록
- @Service(Servic 클래스): request 받은대로 (response 주기 위한) 필요한 데이터 가공을 하는 부분(을 분리)
- Model: .addAttribute로 ("이름", '받은 거')에서 받은 거를 view에 전달
- domain: 
- DTO: Data Transfer Object(필요성과 효과에 대해 아직 모름)
- DI: 
- DAO: Data Access Object(@Repository)
- Ioc Container:
- ComponentScan:
- @RequestBody
- 수단과 방법을 가리지 말고 진도 따라잡기....!!!!!!  
> [ㅇ] 1단계-1<br>
> [ㅇ] 1단계-2<br>
> [ㅇ] 1단계-3<br>
> [] 2단계<br>
> [] 3단계<br>
> [] 3단계 배포<br>

### 경과
- 1-1 완료: 코드 수정 후 form.html에서 form에 데이터를 입력하고 제출할 때마다 list.html로 redirect되서 view로 나타나는 것을 확인(서버 끄면 저장은 안 됨)
- 1-2 완료: 주소창에 /users를 하면 list.html로 이동(이미 완료된 상태) 
- 1-3 완료: 주소창에 /users/userId로 profile.html로 이동(성공)
