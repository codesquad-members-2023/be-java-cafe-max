# be-java-cafe
마스터즈 2023 스프링 카페

## 03.27 ~ 04.02 목표
- 하루 1시간 산책하기
- 미션 구현에 급급해하지 말고 하나씩 공부하며 적용하기
- TIL 작성하기
- 백준 4문제 풀기
- Object 9장 읽고 정리
- 스프링 MVC1 강의 듣기
- Java 8 문법 적용해보기

## 2023.03.27(월)
- [x] 1단계 - 회원 가입 기능 구현
    - UserRepository는 추후 db가 변동될 수 있기 때문에 interface로 구현
    - UserRepository는 일단 HashMap으로 구현
    - UserService에서 이미 존재하는 유저 아이디라면 중복 예외 처리
- [x] 1단계 - 회원 목록 조회 기능 구현
- [x] 1시간 산책

### 더 공부할 것
1. ResponseBody, RequestParam, RestController
2. 템플릿 엔진, 머스테치, 타임리프
3. GET, POST

## 2023.03.28(화)
- [x] 회원 프로필 정보 보기 기능 구현
    - 회원이 아닌 사람을 프로필 조회하려고 하면 에러
- [x] 백트래킹 알고리즘 학습
- [x] BoJ 15649 구현
- [x] @ResponseBody, @RestController, @RequestParam, @PathVariable, @ModelAttribute 학습
- [x] GET과 POST의 차이 학습
- [x] 알바

## 2023.03.29(수)
- [x] 타임리프로 html 중복 코드 제거
    - index와 users 디렉토리 안의 파일들은 경로가 다르기 때문에 navigation 에서 공통되는 부분을 따로 빼서 navigation.html로 만들어 모든 파일에 적용함
    - 경로가 다른 부분은 user_navigation.html 로 만들어 users의 파일들에만 적용하고, index에는 적용을 하지 않음
    - head는 공통된 부분이기 때문에 모든 파일에 적용함
- [x] mvc1 section 1까지 듣기
- [x] 알바

### 팀원 피드백
1. HashMap 보다 ConcurrentHashMap 이 멀티쓰레드 환경에서 더 좋다.
2. static 파일은 사용자가 직접 접근이 가능하기 때문에 웬만하면 파일을 올리지 않는 것이 좋다.

## 2023.03.30(목)
- [x] Dan의 리뷰에 대해 생각하기
    - 도메인 객체로서 User에게 password가 꼭 필요한가
        - 만약 필요하다면 password는 언제 필요한가
            - 항상 필요한가 특정 시점에 필요한가
        - 필요하다면 어떻게 안전하게 관리할 수 있는가
        - 특정시점에만 필요하다면 그것이 같은 역할을 하는 객체일까
    - 팩토리 패턴, layer 구분에 대해 생각하기
        - toEntity()가 맞을까 fromDto()가 맞을까
    - final, 불변성
    - 방어적 복사, List.of(), unmodifiableList

## 2023.03.31(금)
- [ ] Dan의 리뷰에 대해 생각하기
    - 팩토리 패턴, layer 구분
    - final, 불변성
    - 방어적 복사, List.of(), unmodifiableList
- [x] ResponseDto 만들어서 적용하기
- [x] 사용하지 않은 코드 삭제하기
