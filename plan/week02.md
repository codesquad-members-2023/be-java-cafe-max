# be-java-cafe
마스터즈 2023 스프링 카페

## 04.03 ~ 04.09 목표
- 학습 내용 wiki에 작성하기
- 하루 1시간 산책하기
- 백준 4문제 풀기
- Object 10장 읽고 정리
- 스프링 MVC1 강의 듣기
- Java 8 문법 적용하기
- 피드백 받은 부분 생각 정리 후 문서화하기

## 2023.04.03(월)
- [x] 피드백 받은 부분 고민하기
  - 불변성, 방어적 복사
  - 팩토리 패턴, layer 구분
- [x] 파라미터에 final 키워드 붙이기
  - IntelliJ 에서 바로 final 붙일 수 있게 설정 변경하기
- [x] README에 프로젝트 요약 정리하기
- [x] 학습한 내용 wiki 만들어서 옮기기
- [x] step1 테스트코드 작성하기
- [ ] step2 구현하기

## 2023.04.04(화)
- [x] 팀원 피드백 정리하기
- [x] 불변성 vs. 방어적 복사 위키에 정리
- [x] final 위키에 정리
- [ ] step2 구현하기
- [ ] step1 Controller test 하기
- [x] 알바
- [ ] 백준 1697

### 팀원 피드백
1. Controller Test는 mockMVC 사용하면 가능하다.
2. unmodifiableList와 List.copyOf 중 User가 setter가 없어 사실상 불변 객체이기 때문에 아무거나 써도 될 것 같다.
3. Test code에서 User 객체 생성을 메서드로 만들어 여러 메서드에서 활용할 수 있게 하면 중복을 줄일 수 있다.

## 2023.04.05(수)
- [x] step2 구현하기
  - 글쓰기
  - 글 목록 조회하기
  - 게시글 상세보기
  - 회원정보 수정
- [x] 알바
- [x] 리드미 및 학습한 내용 작성

## 2023.04.06(목)
- [x] step3 db 연동하기
  - article, user
- [x] mockMvc 사용해서 userController test 완료하기
- [x] step2 Service, Controller, Repository test 하기
  - article, user
  - article의 LocalDateTime.now() 를 테스트하기가 어려워 ArticleService test는 남겨두었다.
- [ ] 리드미 및 학습한 내용 작성

### 페어 리뷰
UserService에서 업데이트 할 때 Repository에 update 메서드를 구현하지 말고 바로 save 메서드를 사용하는 것이 좋을 것 같다.

## 2023.04.07(금)
- [ ] 배포
- [ ] 리드미 및 학습 정리
