스프링 카페 1단계 - 회원 가입 및 회원 목록 조회 기능
=

## 1. 회원 가입 기능
- [x] 가입하기 페이지에서 회원 가입 폼 출력
- [x] 가입 후 회원 목록 조회 페이지로 이동


### 가입하기

![image](https://user-images.githubusercontent.com/118447769/230552391-cf459010-3a5d-4812-a6de-b13c4ab861e5.png)  

- 가입하기 버튼을 누르면 create-user-form url 로 이동하여 form.html 출력한다.
- 가입 정보 입력 후 submit 을 하면 PostMapping 을 통해 UserController 의 join 메서드가 실행된다.
- 가입 정보를 JoinRequest DTO 에 담아서 UserService 의 join 메서드를 통해 레파지토리에 전달된다.
- UserRepository 는 JCF 중 Map 을 사용했으며 유저의 아이디를 키값으로 가진 상태로 유저 정보를 save 한다.



### 회원 목록 조회 페이지로 이동

![image](https://user-images.githubusercontent.com/118447769/230552483-a643d161-a07e-48dd-a5e7-68db4045cb64.png)  

- 가입이 끝나면 회원 목록 페이지를 redirect 한다.



## 2. 회원 목록 조회 기능
- [x] 가입한 회원들의 목록 출력

- 회원 목록은 가입된 유저들의 정보들을 가지고 있는 리스트를 사용하였다.
- 단순 조회이므로 GetMapping 을 사용하였으며 UserService 의 getUsers 메서드를 통해 리스트를 반환한다.
- getUsers 는 UserDto 를 담고 있는 리스트이다.
  - collect 메서드를 통해 스트림의 모든 요소를 하나의 컬렉션으로 모았다.
  - UnmodifiableList 를 활용함으로써 수정할 수 없는 상태로 반환하였다.


## 3. 회원 프로필 조회 기능 구현
- [x] 개별 회원의 프로필 정보를 출력

![image](https://user-images.githubusercontent.com/118447769/230552656-1a2f2c1c-1b4d-4456-8a84-2bc0db6f9d84.png)

- 회원 리스트에서 회원의 아이디를 클릭하면 /users/{userId} 로 이동한다.
- UserRepository 의 키값이 아이디이기에 아이디를 통해 유저 정보를 찾을 수 있다.
- 찾은 UserDto 를 user/profile 로 넘겨 화면에 출력한다.



---

# Mission 1 리뷰내용

## 1. 버전관리 대상 (gitignore)

- .gitignore 파일이란 Git 버전 관리에서 제외할 파일 목록을 지정하는 파일이다.
- git 으로 프로젝트를 관리할 때, 그 프로젝트 안의 특정파일들은 git 으로 관리할 필요가 없는 경우가 있다.
    - 보안상으로 위험성이 있는 파일
    - 프로젝트와 관계없는 파일
    - 용량이 너무 커서 제외해야되는 파일
- .gitignore 파일은 항상 최상위 디렉토리에 위치해야한다.

- 이미 .gradle 확장자가 gitignore 에 추가되어 있는데 커밋에 올라간 이유가 뭘까?..
    - fork를 해온 레파지토리였기에 기존에 이미 커밋되어 있는 상황
    - 이러한 경우 .gitignore에 추가했다 하더라도 무시되지 않는다.
- 1. 이전 커밋을 수정하거나 삭제하고 다시 푸시하는 방법
- 2. git rm 명령어를 사용하여 해당 파일을 삭제하고 새로운 커밋을 만드는 방법

## 2. @Autowired 애노테이션 제거 
- @Autowired annotation 은 의존성 주입 방식 중 하나이다.
- 이는 객체 간의 의존 관계를 자동으로 설정 해 주는 기능을 수행한다.
- 이를 통해 의존 객체를 명시적으로 선언하고 주입하는 작업을 줄일 수 있다.
- @Autowired 를 사용하려면, 스프링 설정 파일에서 해당 클래스를 빈(Bean)으로 등록해야 한다.
- 스프링은 빈으로 등록된 클래스에 대해 @Autowired 애노테이션을 이용해 의존성 주입을 자동으로 수행한다.
- 현재는 제한적인 경우에만 사용하므로 제거할 것

## 3. UserDto Entity 생성

- 기존 코드
  ```java
  @PostMapping("/user/create")
  public String create(UserDTO userDTO){
    User user = new User(userDTO.getUserId(),userDTO.getPassword(),
    userDTO.getName(),userDTO.getEmail());
    memoryUserRepository.save(user);
    return"redirect:/users";
  }
  ```
  - 생성자를 매번 작성해주어야하는 문제가 있다.
  - 메서드명까지 더하여 생성자명이 너무 길고, 개수까지 많아서 가독성이 매우 떨어진다.

- 수정된 코드
  ```java
  @PostMapping("/join")
  public String join(@ModelAttribute JoinRequest joinRequest) {
    userService.join(joinRequest);
    return "redirect:/users";
  }
  ```
  - UserDto.toUserEntity() 를 통해 생성자를 매번 작성하는 것을 피할 수 있다.
  - 이를 위해 DTO의 entity를 리턴하는 방식을 썼다가, dto 패키지 내부에 request 클래스를 생성하는 방향으로 전환했다.
      - 가입 요청을 받거나 회원정보 수정 요청을 받을 때 한번에 필요한 값들만 전달 할 수 있게 수정하였다.

## 4. url 수정 + 'join' 이 적절한 메서드 명인가?

- 기존 코드
  ```java
  @GetMapping("/user/form")
    public String join() {
    return "user/form";
  }
  ```
    - /users/user/form 과 같은 형태로 url 이 매핑되는 문제가 발생한다.
      - 중복된 리소스의 표현으로 추후 유지보수하기 어려워진다.
    - 단순히 가입 창을 보여주는 메서드인데 join 이라는 네이밍은 적절하지 않다.

- 수정된 코드
  ```java
  @GetMapping("/create-user-form")
  public String showJoinForm() {
    return "user/form";
  }
  
  @GetMapping("/login-user-form")
  public String showLoginForm() {
    return "user/login";
  }
  ```
    - 가입창을 보여주는 메서드이기에 showJoinForm 으로 메서드 명을 수정하였다.
    - 추가로 제안해주신대로 가입과 로그인 창에서의 매핑의 형태를 수정하였다.

## 5. DTO (Data Transfer Object)
- DTO 는 Spring 프레임워크에서 사용되는 데이터 전송 객체이다.
- DTO는 서로 다른 계층 간 데이터 전송을 위해 사용한다.
  - ex) 클라이언트가 웹 요청을 보내고, 해당 요청을 처리하는 서비스 계층과 데이터베이스 계층 사이에서 데이터를 전송하는 경우
- 일반적으로 단순한 자바 객체로 구성되며, 해당 객체는 데이터를 저장하는 필드와 해당 필드에 접근할 수 있는 getter 및 setter 메서드를 가지고 있다.
- 데이터 전송에 필요한 최소한의 필드만 포함하고 있으므로, 데이터의 전송량을 최소화하고 성능을 향상시킨다.
- DTO는 주로 RESTful 웹 서비스에서 사용된다.
  - 이러한 서비스에서는 클라이언트와 서버 간에 JSON 또는 XML과 같은 형식의 데이터를 전송하므로, DTO는 해당 데이터를 저장하고 전송하는 데 유용하다.
- DTO는 비즈니스 로직과 분리될 수 있으므로, 해당 객체를 통해 비즈니스 로직에 대한 영향을 최소화할 수 있다.
  - 유지보수성과 코드의 재사용성을 높이는 데 도움이 된다.

## 6. 접근제한자와 동시성 문제
- 기존의 MemoryUserRepository 필드
  ```java
  private final Map<String, User> userRepository = new ConcurrentHashMap<>();
  private static Long sequence = 0L;
  ```
  - ConcurrentHashMap은 스레드 간에 안전하게 값을 공유할 수 있도록 하기 위해 사용했었다.
  - sequence에 대한 동시성 문제는 고려하지 못했었다.

- 수정된 필드
  ```java
  private static final Map<String, User> userRepository = new ConcurrentHashMap<>();
  private static final AtomicLong sequence = new AtomicLong(0L);
  ```
  - 동시성 문제를 해결하기 위해 sequence의 타입을 AtomicLong으로 변경하였다.
  - 추가로, static을 추가하였다.
    - 기존에 필드를 static으로 선언하지 않아도 잘 작동됐던 이유는 이 MemoryUserRepository 클래스가 스프링 빈으로 등록되어 스프링 컨테이너가 이를 관리했기 때문이다.
    - 스프링은 기본적으로 빈으로 등록된 객체를 싱글톤으로 관리하며, 이는 여러 클라이언트가 해당 빈을 공유하게 된다.
    - 따라서 userRepository와 sequence 필드는 MemoryUserRepository 클래스의 인스턴스가 생성될 때마다 새로 생성되는 것이 아니라 스프링 컨테이너가 유일하게 생성한 객체 내에서 공유되어 사용된다.
    - 그럼에도 불구하고 필드를 static으로 선언되어야 하는 이유는 MemoryUserRepository 클래스의 인스턴스가 여러 개 생성되더라도 모든 인스턴스에서 같은 필드를 공유하여 사용하기 위해서이다.
    - 이를 통해 메모리 사용을 최적화할 수 있고 코드의 일관성과 가독성도 향상시킬 수 있다.

### 동시성 문제
- 동시성 문제는 여러 스레드 또는 프로세스가 동시에 공유된 자원에 접근하려고 할 때 발생할 수 있는 문제이다.
- 동시성 문제는 대부분 경쟁 상태(Race Condition)로 알려져 있으며, 다수의 스레드 또는 프로세스가 서로의 작업을 방해하거나 올바르지 않은 값을 사용하여 잘못된 결과를 초래할 수 있다.
- Deadlock 이나 Livelock 같은 다른 동시성 문제도 있다.
  - Deadlock : 두 개 이상의 스레드가 서로가 가지고 있는 자원을 대기하면서 무한정 기다리는 상황
  - Livelock : 두 개 이상의 스레드가 서로를 계속해서 방해하면서 작업을 수행할 수 없는 상황
- 이를 해결하기 위해서는 동기화(Synchronization)와 같은 기술을 사용해야 한다.
- 동기화를 통해 하나의 스레드가 공유 데이터에 접근하는 동안 다른 스레드들은 대기하도록 하여, 데이터에 대한 접근이 일관되고 정확하게 이루어지도록 보장할 수 있다.

### HashMap과 ConcurrentHashMap의 차이
![image](https://user-images.githubusercontent.com/118447769/230563553-b4c48030-9192-4adc-be9d-6ef58fda6bb9.png)  

- ConcurrentHashMap
  - 내부적 동기화 때문에 스레드가 safe하다.
  - 추가 및 삭제와 같은 수정 작업만 동기화 된다. 읽기 작업은 동기화 x
  - 키와 밸류에 null을 허용하지 않는다.
  - 반환된 반복자는 본질적으로 안전하며 iterator 생성 후 맵이 수정되면 예외가 발생하지 않는다.
  - 수정 작업만 동기화 되기 때문에 추가 제거 작업은 해시맵보다 느리다. 그러나 읽기 작업은 해시맵과 동일한 성능을 제공한다.

### AtomicLong
- AtomicLong은 멀티스레드 환경에서 안전하게 long 타입의 값을 증가시키거나 감소시키는 클래스이다.
- 여러 스레드에서 동시에 접근하더라도 안전하게 값을 조작할 수 있도록 synchronized 키워드나 Lock 객체를 사용하지 않고도 원자적(atomic) 연산을 지원한다.
  - 원자적 연산이란 한 번에 실행되어야 하는 연산을 의미 (중간에 다른 스레드의 접근을 차단하는 것을 보장)
-  addAndGet(), getAndAdd(), incrementAndGet(), decrementAndGet() 등의 메서드를 제공한다.



## 그 외의 것들

- [x] README 작성
- [x] 사용하지 않는 클래스 및 메서드 제거
- [x] import 붙여쓰기
- [x] 코드 컨벤션 준수
- [x] EOL 추가
- [x] DTO 학습