# 학습 계획

- [ ] 스프링이 무엇인가에 대해 강의를 보며 이론적으로 파고 들려 하지 말고, 일단 해보기
    - [ ] 아래의 블로그들을 보며 천천히 따라하기
        - https://melonicedlatte.com/2021/07/11/174700.html
        - https://velog.io/@ghkdgus29/%EC%8A%A4%ED%94%84%EB%A7%81-%EA%B8%B0%EB%B3%B8
    - [ ] 각 애노테이션에 대해 공부하기
        - `@Controller`
        - `@PostMapping`
        - `@GetMapping`
        - `PathVariable`
    - [ ] JCF 클래스가 무엇인지 공부
    - [ ] `redirect`에 대해 공부하기
    - [ ] GET 요청과 POST 요청에 대한 것을 실습으로 이해하기
- [ ] 주어진 마크업 파일을 이용하는 방법 공부하기
    - [ ] `Mustache`에 대해 공부하기
    - [ ] HTML 중복 제거에 대해 생각하기
- [ ] 어느 정도 익숙해지면 실습에 필요한 강의만 찾아 보기
- [ ] 이번주부터 리뷰 받는다는 생각을 항상 하면서 커밋과 커밋 메시지 하나하나 신중하게 작성하기

<br>

# First PR

- [이미 닫힌 PR](https://github.com/codesquad-members-2023/be-java-cafe-max/pull/4)
    - closed 되었기 때문에 edit 불가능 이슈 

<br>

# Second PR

## EC2 인스턴스 IP 주소 (탄력적 IP)

<img width="306" alt="스크린샷 2023-04-04 오후 11 05 44" src="https://user-images.githubusercontent.com/62871026/229818494-c2cd68e9-edaa-4a09-ac7f-e55421a6db6f.png">

<img width="260" alt="스크린샷 2023-04-04 오후 11 05 46" src="https://user-images.githubusercontent.com/62871026/229818510-dc09d8e2-3ead-4fb6-9fc2-c7c1fc4ac1c5.png">


- ~~http://43.200.234.81:8080~~
- 탄력적 IP로 세팅했기 때문에 서버가 재시동되어도 주소는 동일합니다.
- 혹시 모를 추가 요금을 위해 새벽에는 서버를 닫아둘 예정입니다.

<br>

## 탄력적 IP 삭제

<img width="1166" alt="스크린샷 2023-04-05 오후 2 55 54" src="https://user-images.githubusercontent.com/62871026/229993192-9c4146e2-ee93-49b8-9438-e6fdc5212cdb.png">

- 호눅스의 말씀으로 탄력적 IP는 사용하지 않을 때 과금이 된다는 사실을 알게 되었습니다.
- 이미 몇천원을 내야 하는 상황이 발생했지만 금액이 더 커지지 않아 다행입니다.
- 앞으로는 웬만하면 인스턴스를 종료하지 않고 아래의 주소를 사용하도록 하겠습니다.
    - http://15.164.212.51:8080/

<br>

## 학습 과정

- h2가 너무 생소해 세팅에만 너무 많은 시간을 잡아먹었던 것 같습니다.
    - 처음에 h2 server를 가지고 진행했었는데 동료들이 지적해 준 덕에 embedded로 하루만에 갈아탈 수 있었습니다.
    - properties 세팅으로 대부분의 시간을 빼앗겼습니다.
- 호눅스가 제공해주신 강의를 보고, 구글링으로 직접 부딪혀 보는 과정을 가졌습니다.
- 하루하루 배워가는 것이 확 체감되는 게 이번 스프링이 처음입니다.
    - 강의를 거의 안 보았는데도 동료들의 조언으로 나날이 성장하는 걸 느끼니 재밌게 할 수 있었던 것 같습니다.
- 이 문장을 작성하는 현재는 목요일 저녁입니다.
    - 지금부터 kyu 리뷰를 받기 전까지는 영한님의 mvc 1편 이론을 공부할 예정입니다.
    - 구현은 어떻게든 도움을 받아가면서 나름대로의 이해를 했지만, 이론적으로는 아직 정리가 안 된 것 같아 1편은 꼭 완강할 생각입니다.

<br>

## 배포 과정

### AWS EC2 인스턴스 생성

- 인스턴스 생성할 때 다른 건 문제가 없었는데 **보안 그룹** 설정에서 조금 애를 먹었습니다.
- 아래처럼 8080, HTTPS를 지정해주고 겨우 접속에 성공할 수 있었습니다.

<img width="465" alt="스크린샷 2023-04-05 오후 4 01 20" src="https://user-images.githubusercontent.com/62871026/230005242-c5f738ab-c26b-401f-985b-d45acf4fa95f.png">

### Amazon Linux 접속

- 배포를 진행하면서 리눅스 쉘 스크립트의 편리함을 알게 되었습니다.
    - 그동안은 리눅스가 너무 생소하여 쉘 스크립트를 쓰는 블로그를 봐도 그냥 지나쳤던 기억이 납니다.
    - 이번에는 ssh 실행도 그렇고 스프링 부트 서버 실행도 그렇고, 직접 커스텀하여 매번 매우 편리하게 사용할 수 있었습니다.

- 전

<img width="968" alt="스크린샷 2023-04-04 오후 11 12 13" src="https://user-images.githubusercontent.com/62871026/229820266-a3b7ffa8-469e-4d4c-9954-f735478d5a09.png">

- 후

<img width="882" alt="스크린샷 2023-04-04 오후 11 12 40" src="https://user-images.githubusercontent.com/62871026/229820405-390f6c02-d746-4bd9-aca7-3f823cd392eb.png">

<br>

### 스프링 부트 서버 실행

- 스프링 서버를 띄울 때 스크립트의 도움을 가장 많이 받고 있습니다.

#### start.sh

<img width="1279" alt="스크린샷 2023-04-04 오후 11 16 07" src="https://user-images.githubusercontent.com/62871026/229821531-cdb8e011-42e5-40ed-95d6-76c431ea2802.png">

```bash
cd cafe/step3/be-java-cafe-max
git pull
```
- 스프링 부트 레포지토리에 가서 깃의 `pull`로 최신 정보를 가져옵니다.

<br>

```bash
./gradlew build
cd
```
- 그 위치에 있는 gradlew를 build 한 후, `home`으로 돌아옵니다.

<br>

```bash
nohup java -jar cafe/step3/be-java-cafe-max/build/libs/cafe-0.0.1-SNAPSHOT.jar 1>/dev/null 2>&1&
```

- nohup
    - nohup로 서버를 실행하지 않으면 터미널이 꺼짐과 동시에 서버도 다운됩니다.
    - 하지만 nohup로 실행했을 때 백그라운드에서 서버가 작동합니다.
- `>/dev/null 2>&1&`
    - 원래라면 실행할 때마다 nohup의 로그가 남게 되는데 지금은 필요가 없어서 이 명령어로 지워줍니다.

<br>

#### find.sh

<img width="505" alt="스크린샷 2023-04-04 오후 11 16 37" src="https://user-images.githubusercontent.com/62871026/229821510-45b7deac-6022-4157-aff5-df830839abe3.png">

- 서버를 종료 시키는 가장 대표적인 명령어는 두가지가 있습니다.
    - `kill -15 id`
        - 순차적으로 작업을 종료시켜서 안전합니다.
    - `kill -9 id`
        - 강제 종료이기 떄문에 위험합니다.
- 저는 이 중 안전한 -15를 선택하였습니다.
- `find.sh`의 명령어는 이 id 값을 찾아주는 명령어입니다.
    - 이 스크립트만 있으면 귀찮게 매번 jar을 입력해줄 필요가 없습니다.

<br>

## 구현 중 고민했던 부분

### DTO에 대한 고민

- UserController (controller)

```java
    @PostMapping("/user/create")
    // 결국 컨트롤러에서 UserForm을 User에 넣어주게 되는데 이 방법은 좋지 않은 방법일까요?
    public String create(@Validated UserForm form) {
        User user = new User(form);
        userService.join(user);
        return "redirect:/users/" + user.getId();
    }
```

<br>

- User (domain)

```java
    public User(UserForm form) {
        this.userId = form.getUserId();
        this.password = form.getPassword();
        this.name = form.getName();
        this.email = form.getEmail();
    }
```

- 컨트롤러에서 domain을 직접적으로 사용하지 말고 DTO를 사용하여 전달받으라고 알고 있습니다.
    - 이런 생각으로 나머지 로직에는 하루종일 DTO 적용에 대하여 삽질을 해서 어느 정도는 짤 수 있었지만,
    - UserController의 `create()`메서드에서는 어떤 식으로 DTO를 적용해야 할지 모르겠습니다.
- UserForm은 Id를 제외한 필드를 갖고 있고, `@NotBlank`로 예외 처리도 하고 있습니다.
    - 회원 가입을 담당하는 create() 메서드를 제외하고는 UserForm을 사용하지 않습니다.

<br>

### DTO 생성자 주입 방법에 대한 고민

- UserProfileForm.java (ArticleTimeForm와 UserUpdateForm도 똑같습니다.)

<img width="1144" alt="스크린샷 2023-04-06 오후 10 30 56" src="https://user-images.githubusercontent.com/62871026/230393483-57e755d2-21fa-4fab-8edf-1f66c4d52d4a.png">

- 주석에 달려있는 로직을 사용한 동료들이 꽤 보이기에 이 부분도 kyu의 말씀을 꼭 들어보고 싶었습니다.
    - 주석 방법을 사용한다면 DTO의 필드에 저장이 되지 않고, 그저 전달 목적만 가지게 된다고 이해했습니다.
    - 하지만 제가 한 방법처럼 도메인 객체를 받아서 필드에 넣어주는 방식으로 한다면 그저 값을 전달해주는 목적과 상반될까요?

<br>

### JdbcTemplate 종류에 대한 고민

- 원래 맨처음에는 순수 JdbcTemplate 클래스를 사용하여 구현을 했습니다.
    - 하지만 동료들이 이것보다는 다른 방법이 더 선호된다고 하셔서 다른 방법을 찾아봤습니다.
- 결론은 NamedParameterJdbcTemplate와 SimpleJdbcInsert를 동시에 사용하는 것이 좋다고 생각하여 이 둘을 사용하도록 로직을 재구현했습니다.
    - repository 폴더 안에 있는 클래스들을 보시면 주석 처리되어 있는 부분은 과거에 작성한 코드입니다.
    - 변경을 위한 코드로서는 파라미터를 직접 설정하지 않는 이 방식이 더 좋다고 생각하는데 맞을까요?
        - 아니면 혹시 이 상황도 개발 환경에 따라 다르게 적용이 될까요?

<br>

## 구현 결과 GIF

### 글 쓰기, 조회 기능

![글 쓰기와 조회 기능](https://user-images.githubusercontent.com/62871026/229815124-b585653f-f09d-4bb8-b425-df82dd5b9fc3.gif)

- 이전의 로직과 작동 방식은 동일합니다.
- 하지만 글쓴이, 제목, 내용이 메모리가 아닌, h2 db에 저장됩니다.
- `INSERT` 문법을 사용했습니다.

<br>

### 회원 가입 후 회원 프로필과 리스트 조회

![회원 가입 후 프로필 리스트](https://user-images.githubusercontent.com/62871026/229815130-811d80b1-e993-44c9-92d4-0f12dffe3fc9.gif)

- 이것도 이전의 로직과 작동 방식은 동일합니다.
- 하지만 아이디, 비밀번호, 이름, 이메일 정보가 메모리가 아닌, h2 db에 저장됩니다.
- `INSERT` 문법을 사용했습니다.

<br>

### (추가) 글 쓴 시간 정보 조회

![글 쓴 시간 정보](https://user-images.githubusercontent.com/62871026/230383843-3f14ef8c-8923-415e-b54d-db7be89c1b7f.gif)

<br>

### 회원 정보 수정

![회원 정보 수정](https://user-images.githubusercontent.com/62871026/229815141-9161b1e7-2ca0-4d94-bf4a-fa2be4fbcc59.gif)

- 바로 전 미션에서 구현하지 못 했던 그 회원 수정 기능입니다.
- PutMapping으로는 계속 실패하였는데 PostMapping으로 바꾼 후 몇 가지 로직만 바꾸었더니 바로 성공했습니다.
- `UPDATE` 문법을 사용했습니다.

<br>

### 예외 처리

- 각 폼에 하나라도 null이 들어가면 404 페이지로 넘어가게끔 해주었습니다.
- Yes를 누르면 홈으로 가고, No를 누르면 제 블로그로 이동합니다.

<br>

![글 쓰기 에러](https://user-images.githubusercontent.com/62871026/230008036-ba65b7e6-c8ac-49f2-8c4d-2d15e8bf2e19.gif)

- 글 작성할 때의 예외 처리입니다.
- 폼 전체가 null이기 때문에 당연히 에러 페이지로 넘어갑니다.

<br>

![회원 가입 에러](https://user-images.githubusercontent.com/62871026/230008779-b4f297af-a3a1-420d-8838-e4e4a1548236.gif)

- 회원 가입할 때의 예외 처리입니다.
- 폼 중 하나라도 null이면 에러 페이지로 넘어갑니다.
    - 이 경우에서는 이름이 null입니다.

<br>

![회원 수정 에러](https://user-images.githubusercontent.com/62871026/230009319-32aafe69-48f0-482b-9b3c-ad88fbc7384b.gif)

- 회원 정보를 수정할 때의 예외 처리입니다.
- 폼 중 하나라도 null이면 에러 페이지로 넘어갑니다.
    - 이 경우에는 이메일이 null입니다.

## Reference

- https://velog.io/@jinyeong-afk/Springboot-awsec2%EC%97%90-jar-%ED%8C%8C%EC%9D%BC-%EC%83%9D%EC%84%B1-%ED%9B%84-%EC%8B%A4%ED%96%89
- https://velog.io/@explorer-cat/AWS-EC2-SpringBootMariaDBJPA-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC%ED%95%B4%EB%B3%B4%EA%B8%B0
- https://bcp0109.tistory.com/356
- https://develop-writing.tistory.com/121
- https://velog.io/@pu1etproof/AWS-EC2RDS-Spring-Boot-%EB%B0%B0%ED%8F%AC-DNS-%ED%8F%AC%EC%9B%8C%EB%94%A9-1
- https://velog.io/@minji/AWS-EC2%EC%97%90-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EB%9D%84%EC%9A%B0%EB%8A%94-%EB%B0%A9%EB%B2%95-2
- https://velog.io/@superscman/AWS-EC2-ubuntu-%EC%84%9C%EB%B2%84-%EB%B0%B1%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8B%A4%ED%96%89
- https://superds.tistory.com/entry/nohup-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-1devnull-21