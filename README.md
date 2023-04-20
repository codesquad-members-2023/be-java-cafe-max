# be-java-cafe
마스터즈 2023 스프링 카페<br>4차 리뷰 대비 다시 작성

### 이번 주 학습계획
- 수단과 방법을 가리지 말고 진도 따라잡기....!!!!!!  :fire::fire:
> [ㅇ] 2단계 -> 이번주 목표는 2단계까지<br>
> [] 2단계 선택<회원정보 수정하기> -> (미루기)<br>
> [] 3단계 직행<br>
> [] WebMVCConfigurer 적용

### 경과
- 2단계: 완료
- 2단계-선택미션: 하려다가 미루고 3단계 시작
- 3단계 시작(목요일)

#### TODO 리스트
- [] DI에 대해 더 알아보기
- [] Bean에 대해 몹시 알아보기
- [] Get과 POST의 차이 + query parameter + MESSEAGE BODY
- [] @ComponentScan 알아보기 -> (@Component + @Bean까지...)
- [] @RequestBody
- [] @Repository -> @Service -> @Controller 관계(매우 명쾌하면 좋겠다!)
- [] Ioc Container

#### <학습 주제 목록>
- bean:
- @AutoWired ->
- Service 클래스 하는 역할: ?? -> 비지니스 로직(무슨 뜻인지 아직 잘 모름)
- Model.addAttribute: ("amumal", amumal.get()) ---mustache--> {{#amumal}} .. {{/amumal}}
- @Repository -> 저장소<br>
- @RequestBody -> POST API인데 Json일때 필요(아직 잘 모름)
- Ioc 컨테이너:
- @ComponentScan:
- @Repository -> @Service -> @Controller (흐름)
- @Configuration:  '설정 클래스를 선언'<<<
- @ModelAttribute:
- wrapper 클래스!: '원시 타입 자체를 객체로 만들어주는' 것인데(아직 정확히 이해하지 못함 -> 몇 번 예제로 연습을...)
- 자료구조 선택기준!: 성능 스펙이 다 있으므로 생각을 해서 선택(!)
- WebMVCConfigurer(인터페이스): ~를 구현한 클래스로 ....
