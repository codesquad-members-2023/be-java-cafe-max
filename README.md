# be-java-cafe
마스터즈 2023 스프링 카페 

## 한 일 - 3/30 (목)
1. 김영한 - spring mvc 강의 듣고 클론코딩 중입니다.
2. be-java-cafe-max 다시 클론 및 설정
    - gradle 설정 (mustache 추가)
    - 전에 작성한 main, test 코드 추가 (김영한 강의)
3. mustache 이용해서 html 반복되는 부분 정리
   - templates/layout에 navbar, header, footer 입력
   - templates 폴더의 모든 html 파일에 mustache 문법 적용해서 layout의 폴더 안의 navbar, header, footer 파일들 적용
4. index.html에서 회원가입 페이지로 get 요청 하면, templates/form.html 과 연결해주는 기능 완성
   - 연결이 안되는 문제가 있었는데, application.properties 설정이 적용이 안되서 그런 것 같다. 재시작 해서 해결! (Ape한테 감사..)

## 할 일 - 3/31 (금)
1. ☑️️️userId 왜 null 인지 확인 -> 변수명이 제대로 안된 부분이 있었음.
2. 각 html이 적절한 방식으로 get, post 되어있는지 확인
3. ☑️️/ 로 가면 index.html 로 가게 하고 싶음 
4. ☑️index.html이 static에 남아있는게 맞는건가? -> template로 이동
5. ☑️코드 변경되면 자동으로 재시작 되게 적용. [블로그](https://torbjorn.tistory.com/726)
6. ☑️김영한 - spring mvc 강의 보기
7. ☑️mustache 문법으로 반복문 돌면서 회원 리스트 추가하기
8. Model, 서블릿이 뭘까?
9. number (회원번호) 로 회원 리스트의 index 부분을 만들었는데, 고유한 index로 수정하고 싶다.
   - 회원이 삭제 될 경우, 중간에 빈 번호가 생기는데 테이블 index는 그대로 유지 및 정렬 하고 싶다.
10. 학습 리뷰 가이드 라인(JK 영상) 보고 PR 정리해보기
11. 회원 프로필 정보보기 기능 구현하기 (미션1-3)
    - /users/{{userId}}

\+ 티스토리 목차 만들기 [블로그](https://sangminem.tistory.com/307)
