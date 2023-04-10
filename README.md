# be-java-cafe

마스터즈 2023 스프링 카페

## 배포 url

[Spring-cafe](http://ec2-3-34-194-155.ap-northeast-2.compute.amazonaws.com:8080/)

## 로그인

스프링에서 `HttpSession`을 이용해 로그인을 구현

* [POST "login"] `userId`, `password`를 받고 로그인을 처리합니다.
* 로그인 로직을 수행합니다.
* 로그인 성공 시 `SESSIONID` 를 발급한다.
* 로그인 실패 시 다시 `user/login` 뷰를 반환한다.

## 자신의 개인정보만 수정

* 로그인한 사용자는 자신의 정보를 수정할 수 있다.
* 쿠키로 넘어온 세션을 통해 개인정보를 수정할 수 있는 권한이 있는지 확인한다.
* 권한이 일치하면 개인정보 수정화면을 띄워주고 그렇지 않으면, 예외 화면을 띄워준다.
